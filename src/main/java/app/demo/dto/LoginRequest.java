package app.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    String email;
    String password;
}
