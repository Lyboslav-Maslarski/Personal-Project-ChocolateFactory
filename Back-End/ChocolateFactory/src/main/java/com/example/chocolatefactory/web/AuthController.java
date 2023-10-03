package com.example.chocolatefactory.web;

import com.example.chocolatefactory.domain.dtos.UserDTO;
import com.example.chocolatefactory.domain.records.ErrorDTO;
import com.example.chocolatefactory.domain.records.LoginDTO;
import com.example.chocolatefactory.domain.records.RegisterDTO;
import com.example.chocolatefactory.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("Invalid login request data!"));
        }

        UserDTO userDTO = userService.loginUser(loginDTO);

        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDTO registerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("Invalid register request data!"));
        }

        UserDTO userDTO = userService.registerUser(registerDTO);

        return ResponseEntity.created(URI.create("api/users/" + userDTO.getId())).body(userDTO);
    }
}
