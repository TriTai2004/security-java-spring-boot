package app.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import app.demo.modal.Account;

public interface AccountRepository extends JpaRepository<Account, UUID>{
    Account findByEmail(String email);
}
