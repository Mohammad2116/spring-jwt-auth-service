package ir.aspireapps.authservice.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "User registration payload")
public record UserRegisterRequest(
		@Schema(example = "jack@example.com")
		@NotBlank(message = "Email can't be empty")
		@Email(message = "Not a valid email")
		String email,

		@Schema(example = "Password1234")
		@NotBlank(message="Password can't be empty")
		@Size(min = 8, max = 150, message = "Password must be between 8 to 150 characters")
		@Pattern(
		    regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
		    message = "Password must contain at least one letter and one number"
		)
		String password,

		@Schema(example = "John")
		@NotBlank(message = "First name can't be empty")
		@Size(max = 150, message = "Fist name length max size is 150")
		String firstName,

		@Schema(example = "Doe")
		@NotBlank(message = "Last name can't be empty")
		@Size(max = 150, message = "Last name length max size is 150")
		String lastName		
		) {}