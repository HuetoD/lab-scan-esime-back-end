package ipn.esimecu.labscan.service;

import ipn.esimecu.labscan.exception.UserNotFoundException;
import ipn.esimecu.labscan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                                .map(user ->
                                    User.builder()
                                        .username(user.getEmail())
                                        .password(user.getPassword())
                                        .roles(user.getRoleUsers()
                                        .stream()
                                        .map(roleUser -> roleUser.getRole().getRoleName().name())
                                        .toArray(String[]::new)
                                )
                                .build()
                                )
                                .orElseThrow(UserNotFoundException::new);
    }
}