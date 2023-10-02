package com.example.chocolatefactory.web;

import com.example.chocolatefactory.domain.requests.LoginRequest;
import com.example.chocolatefactory.domain.requests.RegisterRequest;
import com.example.chocolatefactory.domain.responses.MessageResponse;
import com.example.chocolatefactory.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid login request data!"));
        }

        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }

    @PostMapping(value = {"/signup", "/signup/"})
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userService.userAlreadyExists(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        userService.registerUser(registerRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
