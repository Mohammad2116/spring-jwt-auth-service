package ir.aspireapps.authservice.control;

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
public class AdminController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/users")
    public ResponseEntity<PageResponse<UserDetailsResponse>> users(Pageable pageable){
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @PostMapping("/promote")
    public ResponseEntity<?> promoteUser(@RequestParam String username){
        userService.promoteToAdmin(username);
        return ResponseEntity.ok("User promoted successfully");
    }
}
