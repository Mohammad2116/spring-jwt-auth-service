package ir.aspireapps.authservice.service;

import ir.aspireapps.authservice.dto.user.UserDetailsResponse;
import ir.aspireapps.authservice.dto.user.UserRegisterRequest;
import ir.aspireapps.authservice.dto.user.UserUpdateDetailsRequest;
import ir.aspireapps.authservice.dto.user.UserUpdatePasswordRequest;
import ir.aspireapps.authservice.exception.ResourceNotFoundException;
import ir.aspireapps.authservice.mapper.UserMapper;
import ir.aspireapps.authservice.model.Role;
import ir.aspireapps.authservice.model.User;
import ir.aspireapps.authservice.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.email()))
            throw new RuntimeException("User already exists");

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);
        return user;
    }

    @Transactional
    public UserDetailsResponse updateDetails(UserUpdateDetailsRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("User with email [" + request.email() + "] not found"));
        user.updateDetails(request);
        return userMapper.toResponse(user);
    }

    @Transactional
    public void delete(String email){
        userRepository.delete(userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email [" + email + "] not found")));
    }

    public List<UserDetailsResponse> findAll(){
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public UserDetailsResponse getDetails(String email){
        return userMapper.toResponse(
                userRepository.findByEmail(email)
                        .orElseThrow(() -> new ResourceNotFoundException("User with email [" + email + "] not found"))
        );
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email [" + email + "] not found"));
    }

    @Transactional
    public void promoteToAdmin(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email [" + email + "] not found"));
        user.setRole(Role.ADMIN);
    }
}

