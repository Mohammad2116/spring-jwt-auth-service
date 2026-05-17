package ir.aspireapps.authservice.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Update password payload")
public record UserUpdatePasswordRequest(
		@Schema(example = "John")
		@NotBlank(message = "Email can't be empty")
		@Email(message = "Not a valid email")
		String email,

		@Schema(example = "OldPassword123!")
		@NotBlank(message="Old password can't be empty")
		String oldPassword,

		@Schema(example = "NewPassword123!")
		@NotBlank(message="New password can't be empty")
		@Size(min = 8, max = 150, message = "New password must be between 8 to 150 characters")
		@Pattern(
		    regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
		    message = "New password must contain at least one letter and one number"
		)
		String newPassword
		
		) {}