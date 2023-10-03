package com.example.chocolatefactory.services;

import com.example.chocolatefactory.domain.responseDTOs.UserDTO;
import com.example.chocolatefactory.domain.entities.RoleEntity;
import com.example.chocolatefactory.domain.entities.UserEntity;
import com.example.chocolatefactory.domain.enums.RoleEnum;
import com.example.chocolatefactory.domain.requestDTOs.LoginDTO;
import com.example.chocolatefactory.domain.requestDTOs.RegisterDTO;
import com.example.chocolatefactory.exceptions.AppException;
import com.example.chocolatefactory.mappers.UserMapper;
import com.example.chocolatefactory.repositories.RoleRepository;
import com.example.chocolatefactory.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       PasswordEncoder encoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.userMapper = userMapper;
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

    public UserDTO registerUser(RegisterDTO registerDTO) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(registerDTO.email());
        if (optionalUser.isPresent()) {
            throw new AppException("Email already exists!", HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = userMapper.registerDTOToUserEntity(registerDTO);
        userEntity.setPassword(encoder.encode(CharBuffer.wrap(registerDTO.password())));

        UserEntity saved = userRepository.save(userEntity);

        return userMapper.toUserDTO(saved);
    }

    public UserDTO loginUser(LoginDTO loginDTO) {
        UserEntity userEntity = userRepository.findByEmail(loginDTO.email())
                .orElseThrow(() -> new AppException("Unknown user!", HttpStatus.NOT_FOUND));

        if (encoder.matches(CharBuffer.wrap(loginDTO.password()), userEntity.getPassword())) {
            return userMapper.toUserDTO(userEntity);
        }

        throw new AppException("Invalid password!", HttpStatus.BAD_REQUEST);
    }
}
