package ir.aspireapps.authservice.control;

import ir.aspireapps.authservice.dto.user.UserDetailsResponse;
import ir.aspireapps.authservice.dto.user.UserUpdateDetailsRequest;
import ir.aspireapps.authservice.model.User;
import ir.aspireapps.authservice.security.CustomUserDetails;
import ir.aspireapps.authservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserDetailsResponse> details(@AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(userService.getDetails(user.getUser().getEmail()));
    }

    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDetailsResponse> update(
            @Valid @RequestBody UserUpdateDetailsRequest request){
        return ResponseEntity.ok(userService.updateDetails(request));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal CustomUserDetails userDetails){
        userService.delete(userDetails.getUser().getEmail());
        return ResponseEntity.noContent().build();
    }
}
