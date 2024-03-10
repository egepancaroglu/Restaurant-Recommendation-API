package com.egepancaroglu.userreviewservice.mapper;

import com.egepancaroglu.userreviewservice.dto.AddressDTO;
import com.egepancaroglu.userreviewservice.dto.response.AddressResponse;
import com.egepancaroglu.userreviewservice.entity.Address;
import com.egepancaroglu.userreviewservice.request.address.AddressSaveRequest;
import com.egepancaroglu.userreviewservice.request.address.AddressUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * @author egepancaroglu
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AddressMapper {

    Address convertToAddress(AddressSaveRequest request);

    AddressResponse convertToAddressResponse(Address address);
    @Mapping(target = "address.status", constant = "INACTIVE")
    @Mapping(target = "userId", source = "address.user.id")
    AddressDTO convertToAddressDTO(Address address);

    void updateAddressRequestToUser(@MappingTarget Address address, AddressUpdateRequest request);

}
