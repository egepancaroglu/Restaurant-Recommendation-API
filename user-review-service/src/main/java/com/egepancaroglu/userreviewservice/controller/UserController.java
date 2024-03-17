package com.egepancaroglu.userreviewservice.controller;

import com.egepancaroglu.userreviewservice.dto.UserDTO;
import com.egepancaroglu.userreviewservice.dto.response.UserResponse;
import com.egepancaroglu.userreviewservice.request.user.UserSaveRequest;
import com.egepancaroglu.userreviewservice.request.user.UserUpdateRequest;
import com.egepancaroglu.userreviewservice.response.RestResponse;
import com.egepancaroglu.userreviewservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author egepancaroglu
 */

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<UserDTO>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(RestResponse.of(userService.getUserById(id)));
    }

    @GetMapping
    public ResponseEntity<RestResponse<List<UserDTO>>> getAllUsers() {
        return ResponseEntity.ok(RestResponse.of(userService.getAllUsers()));
    }

    @PostMapping
    public ResponseEntity<RestResponse<UserResponse>> createUser(@Valid @RequestBody UserSaveRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestResponse.of(userService.saveUser(request)));
    }

    @PutMapping
    public ResponseEntity<RestResponse<UserDTO>> updateUser(@Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(RestResponse.of(userService.updateUser(request)));
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<RestResponse<UserDTO>> activateUser(@PathVariable Long id) {
        return ResponseEntity.ok(RestResponse.of(userService.activateUser(id)));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }


}
