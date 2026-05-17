package ir.aspireapps.authservice.control;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.aspireapps.authservice.dto.page.PageResponse;
import ir.aspireapps.authservice.dto.user.UserDetailsResponse;
import ir.aspireapps.authservice.mapper.UserMapper;
import ir.aspireapps.authservice.service.UserService;
import jdk.dynalink.linker.LinkerServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(
        name = "Admin",
        description = "Admin-only user management operations"
)
public class AdminController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(
            summary = "Get all users",
            description = "Returns a paginated list of users for admin review"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users returned successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/users")
    public ResponseEntity<PageResponse<UserDetailsResponse>> getAllUsers(
            @Parameter(description = "Pageable list of users information")
            Pageable pageable){
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @Operation(
            summary = "Promote user to admin",
            description = "promotes a user account to administrator role"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User promoted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "403", description = "Forbidder"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/promote")
    public ResponseEntity<?> promoteUser(
            @Parameter(description = "Username(email) of the user to promote", example = "jack@example.com")
            @RequestParam String username){
        userService.promoteToAdmin(username);
        return ResponseEntity.ok("User promoted successfully");
    }
}
