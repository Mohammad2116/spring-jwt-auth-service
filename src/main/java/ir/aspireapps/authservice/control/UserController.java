package ir.aspireapps.authservice.control;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.aspireapps.authservice.dto.auth.AuthResponse;
import ir.aspireapps.authservice.dto.auth.RefreshTokenRequest;
import ir.aspireapps.authservice.dto.user.UserDetailsResponse;
import ir.aspireapps.authservice.dto.user.UserUpdateDetailsRequest;
import ir.aspireapps.authservice.dto.user.UserUpdatePasswordRequest;
import ir.aspireapps.authservice.model.User;
import ir.aspireapps.authservice.security.CustomUserDetails;
import ir.aspireapps.authservice.service.AuthService;
import ir.aspireapps.authservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@RequiredArgsConstructor
@Tag(
        name = "Users",
        description = "Current user profile and account management"
)
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @Operation(
            summary = "Get current user profile",
            description = "Returns the authenticated user's profile information."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile returned successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/me")
    public ResponseEntity<UserDetailsResponse> getMe(@AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(userService.getDetails(user.getUser().getEmail()));
    }

    @Operation(
            summary = "Update current user details",
            description = "Updates the authenticated user's profile details"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/me")
    public ResponseEntity<UserDetailsResponse> updateMe(
            @Valid @RequestBody UserUpdateDetailsRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails){
        return ResponseEntity.ok(userService.updateDetails(userDetails.getUser().getEmail(), request));
    }

    @Operation(
            summary = "Update current user password",
            description = "Update the authenticated user's password, all refresh tokens will be revoked"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/me/password")
    public ResponseEntity<AuthResponse> updateMyPassword(
            @Valid @RequestBody UserUpdatePasswordRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest httpServletRequest) {
        return  ResponseEntity.ok(
                userService.updatePassword(userDetails.getUser().getEmail(), request,
                        httpServletRequest.getHeader("User-Agent"),
                        httpServletRequest.getRemoteAddr()));
    }

    @Operation(
            summary = "Delete current user account",
            description = "Deletes the authenticated user's account after revoking refresh tokens"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Account deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe(@AuthenticationPrincipal CustomUserDetails userDetail){
        authService.logoutAll(userDetail.getUser().getEmail());
        userService.delete(userDetail.getUser().getEmail());
        return ResponseEntity.noContent().build();
    }
}
