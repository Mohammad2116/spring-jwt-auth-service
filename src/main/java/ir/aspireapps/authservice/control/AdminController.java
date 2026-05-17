package ir.aspireapps.authservice.control;

import ir.aspireapps.authservice.dto.user.UserDetailsResponse;
import ir.aspireapps.authservice.service.UserService;
import jdk.dynalink.linker.LinkerServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDetailsResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/promote")
    public ResponseEntity<?> promoteUser(@RequestParam String username){
        userService.promoteToAdmin(username);
        return ResponseEntity.ok("User promoted successfully");
    }
}
