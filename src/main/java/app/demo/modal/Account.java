package app.demo.modal;

import java.util.UUID;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data 
public class Account {

    @Id
    @GeneratedValue
    private UUID id;

    private String email;
    private String password;
    private String role;

}
