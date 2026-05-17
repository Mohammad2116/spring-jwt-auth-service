package ir.aspireapps.authservice.dto.user;

public record UserDetailsResponse(
		String email,
		String firstName,
		String lastName
		) {}