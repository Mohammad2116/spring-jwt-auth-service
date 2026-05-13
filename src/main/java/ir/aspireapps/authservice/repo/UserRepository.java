package ir.aspireapps.authservice.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ir.aspireapps.authservice.model.User;

public interface UserRepository extends JpaRepository<User, UUID>{

	Optional<User> findByEmail(String email);

}
