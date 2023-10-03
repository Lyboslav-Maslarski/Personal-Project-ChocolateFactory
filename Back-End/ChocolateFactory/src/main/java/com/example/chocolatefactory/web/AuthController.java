package com.example.chocolatefactory.web;

import com.example.chocolatefactory.config.UserAuthProvider;
import com.example.chocolatefactory.domain.responseDTOs.UserDTO;
import com.example.chocolatefactory.domain.responseDTOs.ErrorDTO;
import com.example.chocolatefactory.domain.requestDTOs.LoginDTO;
import com.example.chocolatefactory.domain.requestDTOs.RegisterDTO;
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
    private final UserAuthProvider userAuthProvider;

    public AuthController(UserService userService, UserAuthProvider userAuthProvider) {
        this.userService = userService;
        this.userAuthProvider = userAuthProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("Invalid login request data!"));
        }

        UserDTO userDTO = userService.loginUser(loginDTO);
        userDTO.setToken(userAuthProvider.createToken(userDTO));

        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDTO registerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("Invalid register request data!"));
        }

        UserDTO userDTO = userService.registerUser(registerDTO);
        userDTO.setToken(userAuthProvider.createToken(userDTO));

        return ResponseEntity.created(URI.create("api/users/" + userDTO.getId())).body(userDTO);
    }
}
