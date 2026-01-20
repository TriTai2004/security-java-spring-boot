package app.demo.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.demo.modal.Account;
import app.demo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailConfig implements UserDetailsService{

    private final AccountRepository accountRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username);

        if(account == null){
            throw new UsernameNotFoundException("user not found");
        }

        return User.builder()
            .username(account.getEmail())
            .password(account.getPassword())
            .roles(account.getRole())
            .build();
    }

    
}
