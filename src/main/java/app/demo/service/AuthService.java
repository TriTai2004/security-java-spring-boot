package app.demo.service;

import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import app.demo.util.JwtUtil;
import app.demo.dto.LoginResponse;
import app.demo.dto.UserResponse;
import app.demo.modal.Account;
import app.demo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final JwtUtil jwtUtil;;
    
    public LoginResponse login(String email, String password){

        Account account = accountRepository.findByEmail(email);
        if(account != null && account.getPassword().equals(Base64.getEncoder().encodeToString(password.getBytes()))){

            UserResponse userResponse = UserResponse.builder()
                .id(account.getId())
                .email(account.getEmail())
                .role(account.getRole())
                .build();
            String token = jwtUtil.generateToken(account.getEmail(), account.getRole());
            LoginResponse loginResponse = LoginResponse.builder()
                .accessToken(token)
                .refreshToken(jwtUtil.generateRefreshToken(account.getEmail()))
                .expiresIn(jwtUtil.extractExpiration(token).getTime())
                .tokenType("Bearer")
                .user(userResponse)
                .build();

            return loginResponse;
        } else {
            return null;
        }

    }

    public String register(String email, String password){
        Account account = new Account();
        account.setEmail(email);
        account.setPassword(Base64.getEncoder().encodeToString(password.getBytes()));
        account.setRole("ROLE_USER");
        account = accountRepository.save(account);

        // Sinh token ngay sau khi tạo account
        String token = jwtUtil.generateToken(account.getEmail(), account.getRole());

        return token; // trả về token
    }

    public ResponseEntity<?> refreshToken(String refreshToken) {

        if (refreshToken.isBlank()) {
            return ResponseEntity.status(400).body("Refresh token is required");
        }

        if(jwtUtil.isTokenExpired(refreshToken, jwtUtil.getREFRESH_SECRET())) {
            return ResponseEntity.status(401).body("Refresh token expired");
        }

        String email = jwtUtil.extractEmailFromRefreshToken(refreshToken);

        Account account = accountRepository.findByEmail(email);

        String newAccessToken = jwtUtil.generateToken(email, account.getRole());

        // Implement refresh token logic here
        return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken,
                "tokenType", "Bearer",
                "expiresIn", jwtUtil.extractExpiration(newAccessToken).getTime()
            )
        );
    }
}
