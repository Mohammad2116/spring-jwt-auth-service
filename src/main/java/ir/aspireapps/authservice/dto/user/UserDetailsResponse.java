package ir.aspireapps.authservice.dto.user;

public record UserDetailsResponse(
		String email,
		String firsName,
		String lastName
		) {}