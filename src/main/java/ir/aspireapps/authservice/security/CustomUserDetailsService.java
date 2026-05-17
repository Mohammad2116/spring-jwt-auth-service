package ir.aspireapps.authservice.security;

import ir.aspireapps.authservice.exception.ResourceNotFoundException;
import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ir.aspireapps.authservice.model.User;
import ir.aspireapps.authservice.repo.UserRepository;
import lombok.RequiredArgsConstructor;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(@NotBlank String username) {
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("User with Email [" + username + "] not found"));
		return new CustomUserDetails(user);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User with Email [" + email + "] not found"));
	}
}
