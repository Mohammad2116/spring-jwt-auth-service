package ir.aspireapps.authservice.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateDetailsRequest(
		@NotBlank(message = "Email can't be empty")
		@Email(message = "Not a valid email")
		String email,
		
		@NotBlank(message = "First name can't be empty")
		@Size(max = 150, message = "Fist name length max size is 150")
		String firstName,
		
		@NotBlank(message = "Last name can't be empty")
		@Size(max = 150, message = "Last name length max size is 150")
		String lastName		
	) {}