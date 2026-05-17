package ir.aspireapps.authservice.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Update user details payload")
public record UserUpdateDetailsRequest(
		@Schema(example = "john@example.com")
		@NotBlank(message = "Email can't be empty")
		@Email(message = "Not a valid email")
		String email,

		@Schema(example = "John")
		@NotBlank(message = "First name can't be empty")
		@Size(max = 150, message = "Fist name length max size is 150")
		String firstName,

		@Schema(example = "Doe")
		@NotBlank(message = "Last name can't be empty")
		@Size(max = 150, message = "Last name length max size is 150")
		String lastName		
	) {}