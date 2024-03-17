package com.egepancaroglu.userreviewservice.service;

import com.egepancaroglu.userreviewservice.dto.UserDTO;
import com.egepancaroglu.userreviewservice.entity.User;
import com.egepancaroglu.userreviewservice.request.user.UserSaveRequest;
import com.egepancaroglu.userreviewservice.request.user.UserUpdateRequest;

import java.util.List;

/**
 * @author egepancaroglu
 */

public interface UserService {


    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    User getUserEntity(Long id);

    UserDTO saveUser(UserSaveRequest request);

    UserDTO updateUser(UserUpdateRequest request);

    void deleteUser(Long id);

    UserDTO updateUserFields(UserUpdateRequest request);

    UserDTO activateUser(Long id);
}
