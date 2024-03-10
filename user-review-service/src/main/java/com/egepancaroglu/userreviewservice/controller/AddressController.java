package com.egepancaroglu.userreviewservice.controller;

import com.egepancaroglu.userreviewservice.dto.AddressDTO;
import com.egepancaroglu.userreviewservice.request.address.AddressSaveRequest;
import com.egepancaroglu.userreviewservice.request.address.AddressUpdateRequest;
import com.egepancaroglu.userreviewservice.response.RestResponse;
import com.egepancaroglu.userreviewservice.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author egepancaroglu
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/addresses")
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<RestResponse<List<AddressDTO>>> getAllAddresses() {
        return ResponseEntity.ok(RestResponse.of(addressService.getAllAddresses()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<AddressDTO>> getAddressById(@PathVariable Long id) {
        return ResponseEntity.ok(RestResponse.of(addressService.getAddressById(id)));
    }

    @PostMapping
    public ResponseEntity<RestResponse<AddressDTO>> createAddress(@Valid @RequestBody AddressSaveRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestResponse.of(addressService.saveAddress(request)));
    }

    @PutMapping
    public ResponseEntity<AddressDTO> updateAddress(@Valid @RequestBody AddressUpdateRequest request) {
        return ResponseEntity.ok(addressService.updateAddress(request));
    }


    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAddresById(@PathVariable Long id) {
        addressService.deleteAddressById(id);
    }

}
