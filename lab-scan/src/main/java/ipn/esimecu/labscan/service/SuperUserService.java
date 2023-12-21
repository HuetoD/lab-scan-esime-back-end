package ipn.esimecu.labscan.service;

import ipn.esimecu.labscan.dto.AdminDTO;
import ipn.esimecu.labscan.dto.response.AdminResponse;
import ipn.esimecu.labscan.entity.RoleEntity;
import ipn.esimecu.labscan.entity.RoleUserEntity;
import ipn.esimecu.labscan.entity.UserEntity;
import ipn.esimecu.labscan.entity.constant.Role;
import ipn.esimecu.labscan.exception.EmailExistsException;
import ipn.esimecu.labscan.exception.RoleNotFoundException;
import ipn.esimecu.labscan.exception.UserNotFoundException;
import ipn.esimecu.labscan.mapper.UserMapper;
import ipn.esimecu.labscan.repository.RoleRepository;
import ipn.esimecu.labscan.repository.RoleUserRepository;
import ipn.esimecu.labscan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SuperUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleUserRepository roleUserRepository;
    private final UserMapper userMapper;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<AdminResponse> loadAdmins() {
        return userMapper.mapAllDTOs(userRepository.findAll());
    }

    @Transactional
    public AdminResponse addAdmin(AdminDTO adminDTO) throws EmailExistsException, RoleNotFoundException {
        existsEmail(adminDTO.getEmail());
        UserEntity user = new UserEntity();
        user.setEmail(adminDTO.getEmail());
        user.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        user = userRepository.save(user);
        RoleEntity role = roleRepository.findByRoleName(Role.ADMIN)
                                        .orElseThrow(() -> new RoleNotFoundException("No exite el rol de tipo: ".concat(Role.ADMIN.toString())));
        RoleUserEntity roleUserEntity = new RoleUserEntity();
        roleUserEntity.setUser(user);
        roleUserEntity.setRole(role);
        return userMapper.map(roleUserRepository.save(roleUserEntity).getUser());
    }

    @Transactional
    public AdminResponse updateAdmin(AdminDTO request) throws EmailExistsException {
        UserEntity user = userRepository.findById(request.getAdminId())
                                        .orElseThrow(UserNotFoundException::new);
        if(!request.getEmail().equals(user.getEmail())) 
            existsEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        userRepository.save(user);
        log.info(mailService.sendSyncNewPassword(user.getEmail(), request.getPassword()));
        return userMapper.map(user);
    }

    @Transactional
    public void removeAdmin(int adminId)  {
        userRepository.deleteById(adminId);
    }

    public void existsEmail(String email) throws EmailExistsException {
        if(userRepository.findByEmail(email).isPresent()) 
            throw new EmailExistsException("Correo existente: " + email);
    }

    @Override
    public String toString() {
        return "SuperUserService :: si";
    }

}
