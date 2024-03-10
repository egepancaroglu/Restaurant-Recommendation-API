package com.egepancaroglu.userreviewservice.service.impl;

import com.egepancaroglu.userreviewservice.dto.AddressDTO;
import com.egepancaroglu.userreviewservice.entity.Address;
import com.egepancaroglu.userreviewservice.exception.ItemNotFoundException;
import com.egepancaroglu.userreviewservice.general.ErrorMessages;
import com.egepancaroglu.userreviewservice.mapper.AddressMapper;
import com.egepancaroglu.userreviewservice.repository.AddressRepository;
import com.egepancaroglu.userreviewservice.request.address.AddressSaveRequest;
import com.egepancaroglu.userreviewservice.request.address.AddressUpdateRequest;
import com.egepancaroglu.userreviewservice.response.RestResponse;
import com.egepancaroglu.userreviewservice.service.AddressService;
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
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserService userService;

    @Override
    public List<AddressDTO> getAllAddresses() {

        List<Address> addressList = addressRepository.findAll();

        List<AddressDTO> addressDTOList = new ArrayList<>();
        for (Address address : addressList) {
            AddressDTO addressDTO = addressMapper.convertToAddressDTO(address);
            addressDTOList.add(addressDTO);
        }

        return addressDTOList;

    }

    @Override
    public AddressDTO getAddressById(Long id) {

        Address address = addressRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(ErrorMessages.ADDRESS_NOT_FOUND));

        return addressMapper.convertToAddressDTO(address);

    }

    @Override
    public AddressDTO saveAddress(AddressSaveRequest request) {

        Address address = addressMapper.convertToAddress(request);

        address.setUser(userService.getUserEntity(request.userId()));

        address = addressRepository.save(address);

        return addressMapper.convertToAddressDTO(address);

    }

    @Override
    public AddressDTO updateAddress(AddressUpdateRequest request) {

        Address address = addressRepository.findById(request.id()).orElseThrow();
        addressMapper.updateAddressRequestToUser(address, request);

        addressRepository.save(address);

        return addressMapper.convertToAddressDTO(address);
    }

    @Override
    public void deleteAddressById(Long id) {

        addressRepository.deleteById(id);

    }
}
