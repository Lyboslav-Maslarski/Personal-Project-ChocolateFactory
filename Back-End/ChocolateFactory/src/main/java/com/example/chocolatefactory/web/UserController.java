package com.example.chocolatefactory.web;

import com.example.chocolatefactory.config.UserAuthProvider;
import com.example.chocolatefactory.domain.requestDTOs.user.PasswordDTO;
import com.example.chocolatefactory.domain.requestDTOs.user.UserReqDTO;
import com.example.chocolatefactory.domain.responseDTOs.user.UserDTO;
import com.example.chocolatefactory.domain.responseDTOs.error.ErrorDTO;
import com.example.chocolatefactory.domain.requestDTOs.user.LoginReqDTO;
import com.example.chocolatefactory.domain.responseDTOs.user.UserDetailsDTO;
import com.example.chocolatefactory.domain.responseDTOs.user.UserShorDTO;
import com.example.chocolatefactory.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    public UserController(UserService userService, UserAuthProvider userAuthProvider) {
        this.userService = userService;
        this.userAuthProvider = userAuthProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginReqDTO loginReqDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("Invalid login request data!"));
        }

        UserDTO userDTO = userService.loginUser(loginReqDTO);
        userDTO.setToken(userAuthProvider.createToken(userDTO));

        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserReqDTO userReqDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("Invalid register request data!"));
        }

        UserDTO userDTO = userService.registerUser(userReqDTO);

        return ResponseEntity.created(URI.create("api/users/" + userDTO.getId())).body(userDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserShorDTO>> getAllUsers() {
        List<UserShorDTO> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> getUserById(@PathVariable Long id) {
        UserDetailsDTO userDetailsDTO = userService.getUser(id);

        return ResponseEntity.ok(userDetailsDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserReqDTO userReqDTO,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("Invalid user data!"));
        }
        userService.updateUser(id, userReqDTO);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @Valid @RequestBody PasswordDTO passwordDTO,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorDTO("Invalid user data!"));
        }
        userService.changePassword(id,passwordDTO);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/promote")
    public ResponseEntity<?> promoteUser(@PathVariable Long id) {
        userService.promote(id);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/demote")
    public ResponseEntity<UserDTO> demoteUser(@PathVariable Long id) {
        userService.demoteUser(id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
