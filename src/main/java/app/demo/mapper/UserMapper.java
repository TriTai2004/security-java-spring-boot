package app.demo.mapper;

import org.mapstruct.Mapper;

import app.demo.dto.UserResponse;
import app.demo.modal.Account;

@Mapper
public interface UserMapper {
    
    UserResponse toResponse(Account account);

}
