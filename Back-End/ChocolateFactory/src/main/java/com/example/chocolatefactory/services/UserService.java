package com.example.chocolatefactory.services;

import com.example.chocolatefactory.domain.requestDTOs.user.PasswordDTO;
import com.example.chocolatefactory.domain.responseDTOs.user.UserDTO;
import com.example.chocolatefactory.domain.entities.RoleEntity;
import com.example.chocolatefactory.domain.entities.UserEntity;
import com.example.chocolatefactory.domain.enums.RoleEnum;
import com.example.chocolatefactory.domain.requestDTOs.user.LoginReqDTO;
import com.example.chocolatefactory.domain.requestDTOs.user.UserReqDTO;
import com.example.chocolatefactory.domain.responseDTOs.user.UserDetailsDTO;
import com.example.chocolatefactory.domain.responseDTOs.user.UserShorDTO;
import com.example.chocolatefactory.exceptions.AppException;
import com.example.chocolatefactory.mappers.UserMapper;
import com.example.chocolatefactory.repositories.RoleRepository;
import com.example.chocolatefactory.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OrderService orderService;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       OrderService orderService, PasswordEncoder encoder,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.orderService = orderService;
        this.encoder = encoder;
        this.userMapper = userMapper;
    }


    public UserDTO registerUser(UserReqDTO userReqDTO) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(userReqDTO.email());
        if (optionalUser.isPresent()) {
            throw new AppException("Email already exists!", HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = userMapper.registerDTOToUserEntity(userReqDTO);
        userEntity.setPassword(encoder.encode(CharBuffer.wrap(userReqDTO.password())));

        UserEntity saved = userRepository.save(userEntity);

        return userMapper.toUserDTO(saved);
    }

    public UserDTO loginUser(LoginReqDTO loginReqDTO) {
        UserEntity userEntity = userRepository.findByEmail(loginReqDTO.email())
                .orElseThrow(() -> new AppException("Unknown user!", HttpStatus.NOT_FOUND));

        if (encoder.matches(CharBuffer.wrap(loginReqDTO.password()), userEntity.getPassword())) {
            return userMapper.toUserDTO(userEntity);
        }

        throw new AppException("Invalid password!", HttpStatus.BAD_REQUEST);
    }

    public List<UserShorDTO> getAllUsers() {
        List<UserEntity> allUsers = userRepository.findAll();

        return allUsers.stream().map(userMapper::toUserShortDTO).collect(Collectors.toList());
    }

    public UserDetailsDTO getUser(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) {
            throw new AppException("User does not exist!", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = userEntity.get();

        UserDetailsDTO userDetailsDTO = userMapper.toUserDetailsDTO(user);
        userDetailsDTO.setOrders(orderService.getAllOrdersByUserId(user.getId()));

        return userDetailsDTO;
    }

    public void updateUser(Long id, UserReqDTO userReqDTO) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        userEntity
                .setEmail(userReqDTO.email())
                .setFullName(userReqDTO.fullName())
                .setCity(userReqDTO.city())
                .setAddress(userReqDTO.address())
                .setPhone(userReqDTO.phone());

        userRepository.save(userEntity);
    }

    public void changePassword(Long id, PasswordDTO passwordDTO) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        if (encoder.matches(CharBuffer.wrap(passwordDTO.oldPassword()), userEntity.getPassword())) {
           userEntity.setPassword(encoder.encode(CharBuffer.wrap(passwordDTO.newPassword())));
           userRepository.save(userEntity);
        }

        throw new AppException("Invalid password!", HttpStatus.BAD_REQUEST);
    }

    public void promote(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        Set<RoleEntity> roles = userEntity.getRoles();
        roles.add(roleRepository.findByRole(RoleEnum.ROLE_MODERATOR));

        userEntity.setRoles(roles);

        userRepository.save(userEntity);
    }

    public void demoteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        Set<RoleEntity> roles = userEntity.getRoles();
        roles.remove(roleRepository.findByRole(RoleEnum.ROLE_MODERATOR));

        userEntity.setRoles(roles);

        userRepository.save(userEntity);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
