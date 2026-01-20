package app.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.demo.dto.LoginRequest;
import app.demo.dto.LoginResponse;
import app.demo.dto.RefreshTokenRequest;
import app.demo.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService  authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        LoginResponse loginResponse = authService.login(req.getEmail(), req.getPassword());

        if(loginResponse != null){
            return ResponseEntity.ok(loginResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    public String register(@RequestBody LoginRequest req) {
        return authService.register(req.getEmail(), req.getPassword());
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        System.out.println("Received refresh token: " + refreshTokenRequest.getRefreshToken());
        // Implement refresh token logic here
        return authService.refreshToken(refreshTokenRequest.getRefreshToken());
    }

}
