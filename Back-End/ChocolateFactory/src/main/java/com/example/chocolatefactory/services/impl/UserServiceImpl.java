package com.example.chocolatefactory.services.impl;

import com.example.chocolatefactory.domain.enums.UserStatus;
import com.example.chocolatefactory.domain.requestDTOs.user.PasswordDTO;
import com.example.chocolatefactory.domain.requestDTOs.user.RegisterReqDTO;
import com.example.chocolatefactory.domain.requestDTOs.user.UserUpdateDTO;
import com.example.chocolatefactory.domain.responseDTOs.user.UserDTO;
import com.example.chocolatefactory.domain.entities.RoleEntity;
import com.example.chocolatefactory.domain.entities.UserEntity;
import com.example.chocolatefactory.domain.enums.RoleEnum;
import com.example.chocolatefactory.domain.requestDTOs.user.LoginReqDTO;
import com.example.chocolatefactory.domain.responseDTOs.user.UserDetailsDTO;
import com.example.chocolatefactory.domain.responseDTOs.user.UserShorDTO;
import com.example.chocolatefactory.exceptions.AppException;
import com.example.chocolatefactory.mappers.UserMapper;
import com.example.chocolatefactory.repositories.RoleRepository;
import com.example.chocolatefactory.repositories.UserRepository;
import com.example.chocolatefactory.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.CharBuffer;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OrderServiceImpl orderServiceImpl;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           OrderServiceImpl orderServiceImpl, PasswordEncoder encoder,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.orderServiceImpl = orderServiceImpl;
        this.encoder = encoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO registerUser(RegisterReqDTO registerReqDTO) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(registerReqDTO.email());
        if (optionalUser.isPresent()) {
            throw new AppException("Email already exists!", HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = userMapper.registerDTOToUserEntity(registerReqDTO);
        userEntity
                .setPassword(encoder.encode(CharBuffer.wrap(registerReqDTO.password())))
                .setRoles(Set.of(roleRepository.findByRole(RoleEnum.ROLE_USER)))
                .setUserStatus(UserStatus.ACTIVE)
                .setBonusPoints(0);

        UserEntity saved = userRepository.save(userEntity);

        return userMapper.toUserDTO(saved);
    }

    @Override
    public UserDTO loginUser(LoginReqDTO loginReqDTO) {
        UserEntity userEntity = userRepository.findByEmail(loginReqDTO.email())
                .orElseThrow(() -> new AppException("Unknown user!", HttpStatus.NOT_FOUND));

        if (userEntity.getUserStatus().equals(UserStatus.DELETED)) {
            throw new AppException("User deleted!", HttpStatus.BAD_REQUEST);
        }

        if (!encoder.matches(CharBuffer.wrap(loginReqDTO.password()), userEntity.getPassword())) {
            throw new AppException("Invalid password!", HttpStatus.BAD_REQUEST);

        }

        return userMapper.toUserDTO(userEntity);
    }

    @Override
    public List<UserShorDTO> getAllUsers() {
        List<UserEntity> allUsers = userRepository.findAllByEmailNot("admin@gmail.com");

        return allUsers.stream()
                .map(userMapper::toUserShortDTO)
                .sorted(Comparator.comparing(UserShorDTO::getModerator).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public UserDetailsDTO getUser(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) {
            throw new AppException("User does not exist!", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = userEntity.get();

        UserDetailsDTO userDetailsDTO = userMapper.toUserDetailsDTO(user);
        userDetailsDTO.setOrders(orderServiceImpl.getAllOrdersByUserId(user.getId()));

        return userDetailsDTO;
    }

    @Override
    public UserDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        userEntity
                .setEmail(userUpdateDTO.email())
                .setFullName(userUpdateDTO.fullName())
                .setCity(userUpdateDTO.city())
                .setAddress(userUpdateDTO.address())
                .setPhone(userUpdateDTO.phone());

        UserEntity saved = userRepository.save(userEntity);

        return userMapper.toUserDTO(saved);
    }

    @Override
    public void changePassword(Long id, PasswordDTO passwordDTO) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        if (!encoder.matches(passwordDTO.oldPassword(), userEntity.getPassword())) {
            throw new AppException("Invalid password!", HttpStatus.BAD_REQUEST);
        }

        userEntity.setPassword(encoder.encode(passwordDTO.newPassword()));
        userRepository.save(userEntity);
    }

    @Override
    public void promoteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        Set<RoleEntity> roles = userEntity.getRoles();

        if (roles.stream().noneMatch(role -> role.getRole().name().equals(RoleEnum.ROLE_MODERATOR.name()))) {
            roles.add(roleRepository.findByRole(RoleEnum.ROLE_MODERATOR));
        }

        userEntity.setRoles(roles);

        userRepository.save(userEntity);
    }

    @Override
    public void demoteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        Set<RoleEntity> roles = userEntity.getRoles();
        roles.removeIf(r -> r.getRole().equals(RoleEnum.ROLE_MODERATOR));

        userEntity.setRoles(roles);

        userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        userEntity.setUserStatus(UserStatus.DELETED);

        userRepository.save(userEntity);
    }

    @Override
    public void addBonusPoints(Long buyerId, BigDecimal total) {
        UserEntity userEntity = userRepository.findById(buyerId)
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        Integer oldBP = userEntity.getBonusPoints();
        userEntity.setBonusPoints(oldBP + total.intValue());

        userRepository.save(userEntity);
    }
}
