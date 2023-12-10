package com.example.chocolatefactory.services;

import com.example.chocolatefactory.domain.requestDTOs.user.LoginReqDTO;
import com.example.chocolatefactory.domain.requestDTOs.user.PasswordDTO;
import com.example.chocolatefactory.domain.requestDTOs.user.RegisterReqDTO;
import com.example.chocolatefactory.domain.requestDTOs.user.UserUpdateDTO;
import com.example.chocolatefactory.domain.responseDTOs.user.UserDTO;
import com.example.chocolatefactory.domain.responseDTOs.user.UserDetailsDTO;
import com.example.chocolatefactory.domain.responseDTOs.user.UserShorDTO;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    UserDTO registerUser(RegisterReqDTO registerReqDTO);

    UserDTO loginUser(LoginReqDTO loginReqDTO);

    List<UserShorDTO> getAllUsers();

    UserDetailsDTO getUser(Long id);

    UserDTO updateUser(Long id, UserUpdateDTO userUpdateDTO);

    void changePassword(Long id, PasswordDTO passwordDTO);

    void promoteUser(Long id);

    void demoteUser(Long id);

    void deleteUser(Long id);

    void addBonusPoints(Long buyerId, BigDecimal total);
}
