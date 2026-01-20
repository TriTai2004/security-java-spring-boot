package app.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private String refreshToken;
    private Long expiresIn;
    private UserResponse user;
    
}
