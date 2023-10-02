package com.example.chocolatefactory.services;

import com.example.chocolatefactory.domain.entities.RoleEntity;
import com.example.chocolatefactory.domain.entities.UserEntity;
import com.example.chocolatefactory.domain.enums.RoleEnum;
import com.example.chocolatefactory.domain.requests.LoginRequest;
import com.example.chocolatefactory.domain.requests.RegisterRequest;
import com.example.chocolatefactory.domain.responses.JwtResponse;
import com.example.chocolatefactory.repositories.RoleRepository;
import com.example.chocolatefactory.repositories.UserRepository;
import com.example.chocolatefactory.security.jwt.JwtUtils;
import com.example.chocolatefactory.security.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder,
                       AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public boolean userAlreadyExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public void registerUser(RegisterRequest registerRequest) {
        UserEntity userEntity = new UserEntity()
                .setEmail(registerRequest.getEmail())
                .setPassword(encoder.encode(registerRequest.getPassword()))
                .setFullName(registerRequest.getFullName())
                .setCity(registerRequest.getCity())
                .setAddress(registerRequest.getAddress())
                .setPhone(registerRequest.getPhone())
                .setRoles(Set.of(roleRepository.findByRole(RoleEnum.ROLE_USER)));

        userRepository.save(userEntity);
    }

    public JwtResponse loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> authoritiesList = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .toList();

        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getFullName(), authoritiesList);
    }

    public void initRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.save(new RoleEntity().setRole(RoleEnum.ROLE_USER));
            roleRepository.save(new RoleEntity().setRole(RoleEnum.ROLE_MODERATOR));
            roleRepository.save(new RoleEntity().setRole(RoleEnum.ROLE_ADMIN));
        }
    }

    public void initInitialUsers() {
        if (userRepository.count() == 0) {
            RoleEntity userRole = roleRepository.findByRole(RoleEnum.ROLE_USER);
            RoleEntity moderatorRole = roleRepository.findByRole(RoleEnum.ROLE_MODERATOR);
            RoleEntity adminRole = roleRepository.findByRole(RoleEnum.ROLE_ADMIN);

            UserEntity user = new UserEntity().setEmail("user@gmail.com")
                    .setPassword(encoder.encode("1234")).setFullName("User Userov").setCity("Sofia")
                    .setAddress("Geo Milev").setPhone("0888 111 111").setRoles(Set.of(userRole));
            UserEntity moderator = new UserEntity().setEmail("moderator@gmail.com")
                    .setPassword(encoder.encode("1234")).setFullName("Moderator Moderatorov").setCity("Sofia")
                    .setAddress("Studentski").setPhone("0888 222 222").setRoles(Set.of(userRole, moderatorRole));
            UserEntity admin = new UserEntity().setEmail("admin@gmail.com")
                    .setPassword(encoder.encode("1234")).setFullName("Admin Adminov").setCity("Sofia")
                    .setAddress("Ivan Vazov").setPhone("0888 333 333").setRoles(Set.of(userRole, moderatorRole, adminRole));

            userRepository.save(user);
            userRepository.save(moderator);
            userRepository.save(admin);
        }
    }
}
