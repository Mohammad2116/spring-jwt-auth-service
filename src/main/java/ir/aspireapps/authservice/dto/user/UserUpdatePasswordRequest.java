package ir.aspireapps.authservice.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdatePasswordRequest(
		@NotBlank(message = "Email can't be empty")
		@Email(message = "Not a valid email")
		String email,
		
		@NotBlank(message="Password can't be empty")
		@Size(min = 8, max = 150, message = "Password must be between 8 to 150 characters")
		@Pattern(
		    regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
		    message = "Password must contain at least one letter and one number"
		)
		String password
		
		) {}