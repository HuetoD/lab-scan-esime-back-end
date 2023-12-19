package ipn.esimecu.labscan.web.controller;

import ipn.esimecu.labscan.dto.request.LoginRequest;
import ipn.esimecu.labscan.service.AuthService;
import ipn.esimecu.labscan.web.security.SecurityConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.Future;

@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("ping")
    public ResponseEntity<String> pong() {
        return ResponseEntity.ok("Pong :: Auth :: " + LocalDateTime.now());
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Expose-Headers", SecurityConstant.JWT_TOKEN_HEADER);
        headers.add(SecurityConstant.JWT_TOKEN_HEADER, SecurityConstant.JWT_TOKEN_PREFIX + token);
        return ResponseEntity.ok()
                             .headers(headers)
                             .body(SecurityConstant.SUCCESS_ACCESS);
    }

    @PutMapping("reset-password")
    public Future<ResponseEntity<String>> resetPassword(@RequestBody LoginRequest request) {
        return authService.resetPassword(request.getEmail(), request.getPassword())
                          .thenApply(ResponseEntity::ok);
    }

    @GetMapping("encode-password")
    public ResponseEntity<String> encode(@RequestParam String password) {
        return ResponseEntity.ok(passwordEncoder.encode(password));
    }


}
