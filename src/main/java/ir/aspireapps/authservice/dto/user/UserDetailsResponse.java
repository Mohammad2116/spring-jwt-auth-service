package ir.aspireapps.authservice.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User details response payload")
public record UserDetailsResponse(
		@Schema(example = "john@example.com")
		String email,

		@Schema(example = "John")
		String firstName,

		@Schema(example = "Doe")
		String lastName
		) {}