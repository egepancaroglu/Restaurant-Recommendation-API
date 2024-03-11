package com.egepancaroglu.userreviewservice.service;

import com.egepancaroglu.userreviewservice.dto.AddressDTO;
import com.egepancaroglu.userreviewservice.dto.response.AddressResponse;
import com.egepancaroglu.userreviewservice.request.address.AddressSaveRequest;
import com.egepancaroglu.userreviewservice.request.address.AddressUpdateRequest;

import java.util.List;

/**
 * @author egepancaroglu
 */

public interface AddressService {

    List<AddressDTO> getAllAddresses();

    AddressDTO getAddressById(Long id);

    AddressResponse saveAddress(AddressSaveRequest request);

    AddressResponse updateAddress(AddressUpdateRequest request);

    void deleteAddressById(Long id);

    List<AddressDTO> getAddressesByUserId(Long userId);
}
