package com.example.chocolatefactory.services;

import com.example.chocolatefactory.domain.entities.RoleEntity;
import com.example.chocolatefactory.domain.entities.UserEntity;
import com.example.chocolatefactory.domain.enums.RoleEnum;
import com.example.chocolatefactory.domain.enums.UserStatus;
import com.example.chocolatefactory.domain.requestDTOs.user.LoginReqDTO;
import com.example.chocolatefactory.domain.requestDTOs.user.PasswordDTO;
import com.example.chocolatefactory.domain.requestDTOs.user.RegisterReqDTO;
import com.example.chocolatefactory.domain.requestDTOs.user.UserUpdateDTO;
import com.example.chocolatefactory.domain.responseDTOs.order.OrderDTO;
import com.example.chocolatefactory.domain.responseDTOs.user.UserDTO;
import com.example.chocolatefactory.domain.responseDTOs.user.UserDetailsDTO;
import com.example.chocolatefactory.domain.responseDTOs.user.UserShorDTO;
import com.example.chocolatefactory.exceptions.AppException;
import com.example.chocolatefactory.mappers.UserMapper;
import com.example.chocolatefactory.repositories.RoleRepository;
import com.example.chocolatefactory.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.CharBuffer;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {
    public static final String EMAIL = "email@abv.bg";
    public static final String PASSWORD = "123456";
    public static final String FULL_NAME = "John Doe";
    public static final String CITY = "Sofia";
    public static final String ADDRESS = "Mladost 5";
    public static final String PHONE = "0 888 8888 888";
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private OrderService orderService;
    @Mock
    private PasswordEncoder encoder;
    @Spy
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, roleRepository, orderService, encoder, userMapper);
    }

    @Test
    void testRegisterUser_WhenEmailDoesNotExist() {
//      Arrange
        RegisterReqDTO registerReqDTO = new RegisterReqDTO(EMAIL, PASSWORD.toCharArray(), FULL_NAME, CITY, ADDRESS, PHONE);
        UserDTO expectedUserDTO = new UserDTO().setEmail(EMAIL).setFullName(FULL_NAME).setCity(CITY).setAddress(ADDRESS).setPhone(PHONE);
        RoleEntity roleEntity = new RoleEntity().setRole(RoleEnum.ROLE_USER);
        UserEntity userEntity = new UserEntity().setEmail(EMAIL).setFullName(FULL_NAME).setCity(CITY).setAddress(ADDRESS).setPhone(PHONE);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(roleRepository.findByRole(any())).thenReturn(roleEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.registerDTOToUserEntity(registerReqDTO)).thenReturn(userEntity);
        when(userMapper.toUserDTO(userEntity)).thenReturn(expectedUserDTO);
        when(encoder.encode(any())).thenReturn("encodedPassword");

//      Act
        UserDTO actualUserDTO = userService.registerUser(registerReqDTO);

//      Assert
        assertNotNull(actualUserDTO);
        assertEquals(expectedUserDTO, actualUserDTO);
        assertEquals(expectedUserDTO.getEmail(), actualUserDTO.getEmail());
        assertEquals(expectedUserDTO.getFullName(), actualUserDTO.getFullName());
    }

    @Test
    void testRegisterUser_WhenEmailExists_ShouldThrowException() {
        RegisterReqDTO registerReqDTO = new RegisterReqDTO(EMAIL, PASSWORD.toCharArray(), FULL_NAME, CITY, ADDRESS, PHONE);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new UserEntity()));

        AppException appException = assertThrows(AppException.class, () -> userService.registerUser(registerReqDTO));
        assertEquals("Email already exists!", appException.getMessage());
    }

    @Test
    void testLoginUser_WhenUserExists() {
        LoginReqDTO loginReqDTO = new LoginReqDTO(EMAIL, PASSWORD.toCharArray());
        UserDTO expectedUserDTO = new UserDTO().setEmail(EMAIL).setFullName(FULL_NAME)
                .setCity(CITY).setAddress(ADDRESS).setPhone(PHONE);
        UserEntity userEntity = new UserEntity().setEmail(EMAIL).setFullName(FULL_NAME)
                .setCity(CITY).setAddress(ADDRESS).setPhone(PHONE)
                .setPassword(PASSWORD)
                .setUserStatus(UserStatus.ACTIVE);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(userEntity));
        when(userMapper.toUserDTO(userEntity)).thenReturn(expectedUserDTO);
        when(encoder.matches(CharBuffer.wrap(PASSWORD), PASSWORD)).thenReturn(true);

        UserDTO actualUserDTO = userService.loginUser(loginReqDTO);

        assertNotNull(actualUserDTO);
        assertEquals(expectedUserDTO, actualUserDTO);
        assertEquals(expectedUserDTO.getEmail(), actualUserDTO.getEmail());
        assertEquals(expectedUserDTO.getFullName(), actualUserDTO.getFullName());
    }

    @Test
    void testLoginUser_WhenUserDoesNotExist_ShouldThrowException() {
        LoginReqDTO loginReqDTO = new LoginReqDTO(EMAIL, PASSWORD.toCharArray());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        AppException appException = assertThrows(AppException.class, () -> userService.loginUser(loginReqDTO));
        assertEquals("Unknown user!", appException.getMessage());
    }

    @Test
    void testLoginUser_WhenUserIsDeleted_ShouldThrowException() {
        LoginReqDTO loginReqDTO = new LoginReqDTO(EMAIL, PASSWORD.toCharArray());
        UserEntity deletedUser = new UserEntity();
        deletedUser.setUserStatus(UserStatus.DELETED);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(deletedUser));

        AppException appException = assertThrows(AppException.class, () -> userService.loginUser(loginReqDTO));
        assertEquals("User deleted!", appException.getMessage());
    }

    @Test
    void testLoginUser_WhenPasswordIsIncorrect_ShouldThrowException() {
        LoginReqDTO loginReqDTO = new LoginReqDTO(EMAIL, PASSWORD.toCharArray());
        UserEntity userEntity = new UserEntity();
        userEntity.setUserStatus(UserStatus.ACTIVE);
        userEntity.setPassword(Arrays.toString(PASSWORD.toCharArray()) + "123");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));

        AppException appException = assertThrows(AppException.class, () -> userService.loginUser(loginReqDTO));
        assertEquals("Invalid password!", appException.getMessage());
    }

    @Test
    void testGetAllUsers_ShouldReturnUsersExceptAdmin() {
        UserEntity user1 = new UserEntity().setEmail("user1@abv.bg");
        UserEntity moderator = new UserEntity().setEmail("moderator@abv.bg");
        UserEntity user2 = new UserEntity().setEmail("user2@abv.bg");
        UserShorDTO user1Short = new UserShorDTO().setEmail("user1@abv.bg").setModerator(false);
        UserShorDTO moderatorShort = new UserShorDTO().setEmail("moderator@abv.bg").setModerator(true);
        UserShorDTO user2Short = new UserShorDTO().setEmail("user2@abv.bg").setModerator(false);

        List<UserEntity> expectedUsers = List.of(user1, moderator, user2);

        when(userRepository.findAllByEmailNot("admin@gmail.com")).thenReturn(expectedUsers);
        when(userMapper.toUserShortDTO(user1)).thenReturn(user1Short);
        when(userMapper.toUserShortDTO(moderator)).thenReturn(moderatorShort);
        when(userMapper.toUserShortDTO(user2)).thenReturn(user2Short);

        List<UserShorDTO> actualUsers = userService.getAllUsers();

        assertNotNull(actualUsers);
        assertEquals(3, actualUsers.size());
        assertEquals("moderator@abv.bg", actualUsers.get(0).getEmail());
    }

    @Test
    void testGetUser_WhenUserExists_ShouldReturnUserDetails() {
        Long userId = 1L;

        UserEntity existingUser = new UserEntity()
                .setEmail(EMAIL)
                .setFullName(FULL_NAME)
                .setCity(CITY)
                .setAddress(ADDRESS)
                .setPhone(PHONE);
        existingUser.setId(userId);

        UserDetailsDTO detailsDTO = new UserDetailsDTO()
                .setEmail(EMAIL)
                .setFullName(FULL_NAME)
                .setCity(CITY)
                .setAddress(ADDRESS)
                .setPhone(PHONE);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        when(userMapper.toUserDetailsDTO(existingUser)).thenReturn(detailsDTO);
        when(orderService.getAllOrdersByUserId(userId)).thenReturn(List.of(new OrderDTO()));

        UserDetailsDTO userDetailsDTO = userService.getUser(userId);

        assertNotNull(userDetailsDTO);
        assertEquals(existingUser.getEmail(), userDetailsDTO.getEmail());
        assertEquals(existingUser.getFullName(), userDetailsDTO.getFullName());
        assertEquals(existingUser.getCity(), userDetailsDTO.getCity());
        assertEquals(existingUser.getAddress(), userDetailsDTO.getAddress());
        assertEquals(existingUser.getPhone(), userDetailsDTO.getPhone());
        assertEquals(1, userDetailsDTO.getOrders().size());

        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, times(1)).toUserDetailsDTO(existingUser);
        verify(orderService, times(1)).getAllOrdersByUserId(userId);
    }

    @Test
    void testGetUser_WhenUserDoesNotExist_ShouldThrowException() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> userService.getUser(userId));

        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, never()).toUserDetailsDTO(any());
        verify(orderService, never()).getAllOrdersByUserId(any());
    }

    @Test
    void testUpdateUser_WhenUserExists_ShouldUpdateDetails() {
        Long userId = 1L;
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(EMAIL, FULL_NAME, CITY, ADDRESS, PHONE);

        UserEntity existingUser = new UserEntity()
                .setEmail("different@example.com")
                .setFullName(FULL_NAME)
                .setCity(CITY)
                .setAddress(ADDRESS)
                .setPhone(PHONE);
        existingUser.setId(userId);

        UserDTO userDTO = new UserDTO()
                .setEmail(EMAIL)
                .setFullName(FULL_NAME)
                .setCity(CITY)
                .setAddress(ADDRESS)
                .setPhone(PHONE);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userMapper.toUserDTO(existingUser)).thenReturn(userDTO);
        when(userRepository.save(any())).thenReturn(existingUser);

        UserDTO updatedUserDTO = userService.updateUser(userId, userUpdateDTO);

        assertNotNull(updatedUserDTO);
        assertEquals(EMAIL, updatedUserDTO.getEmail());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
        verify(userMapper, times(1)).toUserDTO(existingUser);
    }

    @Test
    void testUpdateUser_WhenUserDoesNotExist_ShouldThrowException() {
        Long userId = 1L;
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(EMAIL, FULL_NAME, CITY, ADDRESS, PHONE);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> userService.updateUser(userId, userUpdateDTO));

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).toUserDTO(any());
    }

    @Test
    void testChangePassword_WhenOldPasswordMatches_ShouldChangePassword() {
        Long userId = 1L;
        PasswordDTO passwordDTO = new PasswordDTO("oldPassword", "newPassword");

        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId);
        existingUser.setPassword("encodedOldPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(encoder.matches(passwordDTO.oldPassword(), existingUser.getPassword())).thenReturn(true);
        when(encoder.encode(passwordDTO.newPassword())).thenReturn("encodedNewPassword");

        userService.changePassword(userId, passwordDTO);

        verify(userRepository, times(1)).findById(userId);
        verify(encoder, times(1)).encode(passwordDTO.newPassword());
        verify(userRepository, times(1)).save(existingUser);

        assertEquals("encodedNewPassword", existingUser.getPassword());
    }

    @Test
    void testChangePassword_WhenOldPasswordDoesNotMatch_ShouldThrowException() {
        Long userId = 1L;
        PasswordDTO passwordDTO = new PasswordDTO("wrongOldPassword", "newPassword");

        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId);
        existingUser.setPassword("encodedOldPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(encoder.matches(passwordDTO.oldPassword(), existingUser.getPassword())).thenReturn(false);

        assertThrows(AppException.class, () -> userService.changePassword(userId, passwordDTO));

        verify(userRepository, times(1)).findById(userId);
        verify(encoder, times(1)).matches(passwordDTO.oldPassword(), existingUser.getPassword());
        verify(encoder, never()).encode(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testPromoteUser_WhenUserExists_ShouldAddModeratorRole() {
        Long userId = 1L;

        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId);
        HashSet<RoleEntity> roles = new HashSet<>();
        roles.add(new RoleEntity().setRole(RoleEnum.ROLE_USER));
        existingUser.setRoles(roles);

        RoleEntity moderatorRole = new RoleEntity();
        moderatorRole.setRole(RoleEnum.ROLE_MODERATOR);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(roleRepository.findByRole(RoleEnum.ROLE_MODERATOR)).thenReturn(moderatorRole);
        when(userRepository.save(any())).thenReturn(existingUser);

        userService.promoteUser(userId);

        assertTrue(existingUser.getRoles().contains(moderatorRole));

        verify(userRepository, times(1)).findById(userId);
        verify(roleRepository, times(1)).findByRole(RoleEnum.ROLE_MODERATOR);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testPromoteUser_WhenUserExistsAndIsModerator_ShouldNotChangeRoles() {
        Long userId = 1L;

        RoleEntity userRole = new RoleEntity();
        userRole.setRole(RoleEnum.ROLE_USER);
        RoleEntity moderatorRole = new RoleEntity();
        moderatorRole.setRole(RoleEnum.ROLE_MODERATOR);

        Set<RoleEntity> userRoles = new HashSet<>();
        userRoles.add(userRole);
        userRoles.add(moderatorRole);

        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId);
        existingUser.setRoles(userRoles);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        userService.promoteUser(userId);

        assertTrue(existingUser.getRoles().contains(userRole));
        assertTrue(existingUser.getRoles().contains(moderatorRole));

        verify(userRepository, times(1)).findById(userId);
        verify(roleRepository, never()).findByRole(RoleEnum.ROLE_MODERATOR);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testDemoteUser_WhenUserExistsAndIsModerator_ShouldRemoveModeratorRole() {
        Long userId = 1L;

        RoleEntity moderatorRole = new RoleEntity();
        moderatorRole.setRole(RoleEnum.ROLE_MODERATOR);

        Set<RoleEntity> roles = new HashSet<>();
        roles.add(new RoleEntity().setRole(RoleEnum.ROLE_USER));
        roles.add(moderatorRole);

        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId);
        existingUser.setRoles(roles);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(roleRepository.findByRole(RoleEnum.ROLE_MODERATOR)).thenReturn(moderatorRole);
        when(userRepository.save(any())).thenReturn(existingUser);

        userService.demoteUser(userId);

        assertFalse(existingUser.getRoles().contains(moderatorRole));

        verify(userRepository, times(1)).findById(userId);
        verify(roleRepository, never()).findByRole(RoleEnum.ROLE_MODERATOR);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testDemoteUser_WhenUserExistsAndIsNotModerator_ShouldNotChangeRoles() {
        Long userId = 1L;

        RoleEntity userRole = new RoleEntity();
        userRole.setRole(RoleEnum.ROLE_USER);
        RoleEntity moderatorRole = new RoleEntity();
        moderatorRole.setRole(RoleEnum.ROLE_MODERATOR);

        Set<RoleEntity> userRoles = new HashSet<>();
        userRoles.add(userRole);

        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId);
        existingUser.setRoles(userRoles);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        userService.demoteUser(userId);

        assertTrue(existingUser.getRoles().contains(userRole));
        assertFalse(existingUser.getRoles().contains(moderatorRole));

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testDeleteUser_WhenUserExists_ShouldMarkAsDeleted() {
        Long userId = 1L;

        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any())).thenReturn(existingUser);

        userService.deleteUser(userId);

        assertEquals(UserStatus.DELETED, existingUser.getUserStatus());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testDeleteUser_WhenUserDoesNotExist_ShouldThrowException() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> userService.deleteUser(userId));

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any());
    }
}