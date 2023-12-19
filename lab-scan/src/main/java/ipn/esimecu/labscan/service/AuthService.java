package ipn.esimecu.labscan.service;

import ipn.esimecu.labscan.dto.request.LoginRequest;
import ipn.esimecu.labscan.entity.UserEntity;
import ipn.esimecu.labscan.exception.UnsentMailException;
import ipn.esimecu.labscan.repository.UserRepository;
import ipn.esimecu.labscan.repository.util.JwtTokenProvider;
import ipn.esimecu.labscan.repository.util.Util;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    private final MailService mailService;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public String login(LoginRequest loginRequest) throws AuthenticationException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        return jwtTokenProvider.generateJwtToken(userDetails);
    }

    public CompletableFuture<String> resetPassword(String email, String newPassword) throws UnsentMailException {
        final UserEntity user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        return mailService.sendAsyncNewPassword(
                user.getEmail(),
                Optional.ofNullable(newPassword).orElse(Util.randomString(6).toUpperCase())
        ).thenApply(password -> {
            user.setPassword(passwordEncoder.encode(password));
            return "La nueva contraseña ha sido enviada a " + email;
        });
    }

}
