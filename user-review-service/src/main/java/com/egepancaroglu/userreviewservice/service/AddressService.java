package com.egepancaroglu.userreviewservice.service;

import com.egepancaroglu.userreviewservice.dto.AddressDTO;
import com.egepancaroglu.userreviewservice.request.address.AddressSaveRequest;
import com.egepancaroglu.userreviewservice.request.address.AddressUpdateRequest;
import com.egepancaroglu.userreviewservice.response.RestResponse;

import java.util.List;

/**
 * @author egepancaroglu
 */

public interface AddressService {


    List<AddressDTO> getAllAddresses();

    AddressDTO getAddressById(Long id);

    AddressDTO saveAddress(AddressSaveRequest request);

    AddressDTO updateAddress(AddressUpdateRequest request);

    void deleteAddressById(Long id);


}
