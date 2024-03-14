package com.egepancaroglu.userreviewservice.service.impl;

import com.egepancaroglu.userreviewservice.dto.UserDTO;
import com.egepancaroglu.userreviewservice.dto.response.UserResponse;
import com.egepancaroglu.userreviewservice.entity.User;
import com.egepancaroglu.userreviewservice.entity.enums.Status;
import com.egepancaroglu.userreviewservice.exception.ItemNotFoundException;
import com.egepancaroglu.userreviewservice.mapper.UserMapper;
import com.egepancaroglu.userreviewservice.repository.UserRepository;
import com.egepancaroglu.userreviewservice.request.user.UserSaveRequest;
import com.egepancaroglu.userreviewservice.request.user.UserUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private UserMapper mockUserMapper;

    private UserServiceImpl userServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        userServiceImplUnderTest = new UserServiceImpl(mockUserRepository, mockUserMapper);
    }

    @Test
    void shouldGetAllUsers() {
        List<UserDTO> expectedResult = List.of(
                new UserDTO(0L, "userName", "firstName", "lastName", "email", LocalDate.of(2020, 1, 1)));

        User user = new User();
        user.setId(0L);
        user.setUserName("userName");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setStatus(Status.ACTIVE);
        List<User> users = List.of(user);
        when(mockUserRepository.findAll()).thenReturn(users);

        UserDTO userDTO = new UserDTO(0L, "userName", "firstName", "lastName", "email", LocalDate.of(2020, 1, 1));
        when(mockUserMapper.convertToUserDTO(any(User.class))).thenReturn(userDTO);

        List<UserDTO> result = userServiceImplUnderTest.getAllUsers();

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldGetAllUsers_UserRepositoryReturnsNoItems() {
        when(mockUserRepository.findAll()).thenReturn(Collections.emptyList());

        List<UserDTO> result = userServiceImplUnderTest.getAllUsers();

        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void shouldGetUserById() {
        UserDTO expectedResult = new UserDTO(0L, "userName", "firstName", "lastName", "email",
                LocalDate.of(2020, 1, 1));

        User user1 = new User();
        user1.setId(0L);
        user1.setUserName("userName");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setStatus(Status.ACTIVE);
        Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(0L)).thenReturn(user);

        UserDTO userDTO = new UserDTO(0L, "userName", "firstName", "lastName", "email", LocalDate.of(2020, 1, 1));
        when(mockUserMapper.convertToUserDTO(any(User.class))).thenReturn(userDTO);

        UserDTO result = userServiceImplUnderTest.getUserById(0L);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldGetUserById_UserRepositoryReturnsAbsent() {
        when(mockUserRepository.findById(0L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userServiceImplUnderTest.getUserById(0L)).isInstanceOf(ItemNotFoundException.class);
    }


    @Test
    void shouldGetUserEntity_UserRepositoryReturnsAbsent() {
        when(mockUserRepository.findById(0L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userServiceImplUnderTest.getUserEntity(0L)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void shouldSaveUser() {
        UserSaveRequest request = new UserSaveRequest("userName", "firstName", "lastName", "email",
                LocalDate.of(2020, 1, 1));
        UserResponse expectedResult = new UserResponse("userName", "firstName", "lastName", "email",
                LocalDate.of(2020, 1, 1));

        User user = new User();
        user.setId(0L);
        user.setUserName("userName");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setStatus(Status.ACTIVE);
        when(mockUserMapper.convertToUser(new UserSaveRequest("userName", "firstName", "lastName", "email",
                LocalDate.of(2020, 1, 1)))).thenReturn(user);

        User user1 = new User();
        user1.setId(0L);
        user1.setUserName("userName");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setStatus(Status.ACTIVE);
        when(mockUserRepository.save(any(User.class))).thenReturn(user1);

        UserResponse userResponse = new UserResponse("userName", "firstName", "lastName", "email",
                LocalDate.of(2020, 1, 1));
        when(mockUserMapper.convertToUserResponse(any(User.class))).thenReturn(userResponse);

        UserResponse result = userServiceImplUnderTest.saveUser(request);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldUpdateUser() {
        UserUpdateRequest request = new UserUpdateRequest(0L, "userName", "firstName", "lastName", "email",
                LocalDate.of(2020, 1, 1));
        UserDTO expectedResult = new UserDTO(0L, "userName", "firstName", "lastName", "email",
                LocalDate.of(2020, 1, 1));

        User user1 = new User();
        user1.setId(0L);
        user1.setUserName("userName");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setStatus(Status.ACTIVE);
        Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(0L)).thenReturn(user);

        UserDTO userDTO = new UserDTO(0L, "userName", "firstName", "lastName", "email", LocalDate.of(2020, 1, 1));
        when(mockUserMapper.convertToUserDTO(any(User.class))).thenReturn(userDTO);

        UserDTO result = userServiceImplUnderTest.updateUser(request);

        assertThat(result).isEqualTo(expectedResult);
        verify(mockUserMapper).updateUserRequestToUser(any(User.class),
                eq(new UserUpdateRequest(0L, "userName", "firstName", "lastName", "email", LocalDate.of(2020, 1, 1))));
        verify(mockUserRepository).save(any(User.class));
    }

    @Test
    void shouldUpdateUser_UserRepositoryFindByIdReturnsAbsent() {
        UserUpdateRequest request = new UserUpdateRequest(0L, "userName", "firstName", "lastName", "email",
                LocalDate.of(2020, 1, 1));
        when(mockUserRepository.findById(0L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userServiceImplUnderTest.updateUser(request))
                .isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    void shouldDeleteUser() {
        userServiceImplUnderTest.deleteUser(0L);

        verify(mockUserRepository).deleteById(0L);
    }

    @Test
    void shouldUpdateUserFields() {
        assertThat(userServiceImplUnderTest.updateUserFields(
                new UserUpdateRequest(0L, "userName", "firstName", "lastName", "email",
                        LocalDate.of(2020, 1, 1)))).isNull();
    }

    @Test
    void shouldActivateUser() {
        UserDTO expectedResult = new UserDTO(0L, "userName", "firstName", "lastName", "email",
                LocalDate.of(2020, 1, 1));

        User user1 = new User();
        user1.setId(0L);
        user1.setUserName("userName");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setStatus(Status.ACTIVE);
        Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(0L)).thenReturn(user);

        User user2 = new User();
        user2.setId(0L);
        user2.setUserName("userName");
        user2.setFirstName("firstName");
        user2.setLastName("lastName");
        user2.setStatus(Status.ACTIVE);
        when(mockUserRepository.save(any(User.class))).thenReturn(user2);

        UserDTO userDTO = new UserDTO(0L, "userName", "firstName", "lastName", "email", LocalDate.of(2020, 1, 1));
        when(mockUserMapper.convertToUserDTO(any(User.class))).thenReturn(userDTO);

        UserDTO result = userServiceImplUnderTest.activateUser(0L);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldActivateUser_UserRepositoryFindByIdReturnsAbsent() {
        when(mockUserRepository.findById(0L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userServiceImplUnderTest.activateUser(0L)).isInstanceOf(ItemNotFoundException.class);
    }
}
