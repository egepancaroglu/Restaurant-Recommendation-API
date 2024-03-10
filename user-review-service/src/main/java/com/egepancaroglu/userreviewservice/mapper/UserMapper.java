package com.egepancaroglu.userreviewservice.mapper;

import com.egepancaroglu.userreviewservice.dto.UserDTO;
import com.egepancaroglu.userreviewservice.dto.response.UserResponse;
import com.egepancaroglu.userreviewservice.entity.User;
import com.egepancaroglu.userreviewservice.request.user.UserSaveRequest;
import com.egepancaroglu.userreviewservice.request.user.UserUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * @author egepancaroglu
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {

    User convertToUser(UserSaveRequest request);

    UserResponse convertToUserResponse(User user);

    UserDTO convertToUserDTO(User user);

    void updateUserRequestToUser(@MappingTarget User user, UserUpdateRequest request);

}
