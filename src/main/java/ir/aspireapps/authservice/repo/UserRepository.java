package ir.aspireapps.authservice.repo;

import java.util.Optional;
import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import ir.aspireapps.authservice.model.User;

public interface UserRepository extends JpaRepository<User, UUID>{

	Optional<User> findByEmail(String email);

    boolean existsByEmail(@NotBlank(message = "Email can't be empty") @Email(message = "Not a valid email") String email);
}
