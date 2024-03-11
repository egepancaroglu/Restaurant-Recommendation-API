package com.egepancaroglu.userreviewservice.service.impl;

import com.egepancaroglu.userreviewservice.dto.UserDTO;
import com.egepancaroglu.userreviewservice.dto.response.UserResponse;
import com.egepancaroglu.userreviewservice.entity.User;
import com.egepancaroglu.userreviewservice.entity.enums.Status;
import com.egepancaroglu.userreviewservice.exception.ItemNotFoundException;
import com.egepancaroglu.userreviewservice.general.ErrorMessages;
import com.egepancaroglu.userreviewservice.mapper.UserMapper;
import com.egepancaroglu.userreviewservice.repository.UserRepository;
import com.egepancaroglu.userreviewservice.request.user.UserSaveRequest;
import com.egepancaroglu.userreviewservice.request.user.UserUpdateRequest;
import com.egepancaroglu.userreviewservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author egepancaroglu
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDTO> getAllUsers() {

        List<User> userList = userRepository.findAll();

        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userList) {
            UserDTO userDTO = userMapper.convertToUserDTO(user);
            userDTOList.add(userDTO);
        }

        return userDTOList;

    }

    @Override
    public UserDTO getUserById(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(ErrorMessages.USER_NOT_FOUND));

        return userMapper.convertToUserDTO(user);

    }

    public User getUserEntity(Long id) {

        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public UserResponse saveUser(UserSaveRequest request) {

        User user = userMapper.convertToUser(request);

        user = userRepository.save(user);

        return userMapper.convertToUserResponse(user);

    }

    @Override
    public UserDTO updateUser(UserUpdateRequest request) {

        User user = userRepository.findById(request.id()).orElseThrow(() -> new ItemNotFoundException(ErrorMessages.USER_NOT_FOUND));
        userMapper.updateUserRequestToUser(user, request);

        userRepository.save(user);

        return userMapper.convertToUserDTO(user);

    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO updateUserFields(UserUpdateRequest request) {
        return null;
    }

    @Override
    public UserDTO activateUser(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(ErrorMessages.USER_NOT_FOUND));

        user.setStatus(Status.ACTIVE);
        user = userRepository.save(user);

        return userMapper.convertToUserDTO(user);

    }

}
