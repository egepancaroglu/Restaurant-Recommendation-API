package com.egepancaroglu.userreviewservice.service.impl;

import com.egepancaroglu.userreviewservice.dto.AddressDTO;
import com.egepancaroglu.userreviewservice.dto.response.AddressResponse;
import com.egepancaroglu.userreviewservice.entity.Address;
import com.egepancaroglu.userreviewservice.entity.User;
import com.egepancaroglu.userreviewservice.exception.ItemNotFoundException;
import com.egepancaroglu.userreviewservice.mapper.AddressMapper;
import com.egepancaroglu.userreviewservice.repository.AddressRepository;
import com.egepancaroglu.userreviewservice.repository.UserRepository;
import com.egepancaroglu.userreviewservice.request.address.AddressSaveRequest;
import com.egepancaroglu.userreviewservice.request.address.AddressUpdateRequest;
import com.egepancaroglu.userreviewservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
class AddressServiceImplTest {

    @Mock
    private AddressRepository mockAddressRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private AddressMapper mockAddressMapper;
    @Mock
    private UserService mockUserService;

    private AddressServiceImpl addressServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        addressServiceImplUnderTest = new AddressServiceImpl(mockAddressRepository, mockUserRepository,
                mockAddressMapper, mockUserService);
    }

    @Test
    void shouldGetAllAddresses() {
        // Setup
        List<AddressDTO> expectedResult = List.of(
                new AddressDTO(0L, "city", "state", "district", "street", "location", 0L));

        Address address = new Address();
        address.setId(0L);
        address.setCity("city");
        address.setState("state");
        address.setDistrict("district");
        User user = new User();
        address.setUser(user);
        List<Address> addresses = List.of(address);
        when(mockAddressRepository.findAll()).thenReturn(addresses);

        AddressDTO addressDTO = new AddressDTO(0L, "city", "state", "district", "street", "location", 0L);
        when(mockAddressMapper.convertToAddressDTO(any(Address.class))).thenReturn(addressDTO);

        List<AddressDTO> result = addressServiceImplUnderTest.getAllAddresses();

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldGetAllAddresses_AddressRepositoryReturnsNoItems() {
        when(mockAddressRepository.findAll()).thenReturn(Collections.emptyList());

        List<AddressDTO> result = addressServiceImplUnderTest.getAllAddresses();

        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void shouldGetAddressById() {
        AddressDTO expectedResult = new AddressDTO(0L, "city", "state", "district", "street", "location", 0L);

        Address address1 = new Address();
        address1.setId(0L);
        address1.setCity("city");
        address1.setState("state");
        address1.setDistrict("district");
        User user = new User();
        address1.setUser(user);
        Optional<Address> address = Optional.of(address1);
        when(mockAddressRepository.findById(0L)).thenReturn(address);

        AddressDTO addressDTO = new AddressDTO(0L, "city", "state", "district", "street", "location", 0L);
        when(mockAddressMapper.convertToAddressDTO(any(Address.class))).thenReturn(addressDTO);

        AddressDTO result = addressServiceImplUnderTest.getAddressById(0L);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldGetAddressById_AddressRepositoryReturnsAbsent() {
        when(mockAddressRepository.findById(0L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> addressServiceImplUnderTest.getAddressById(0L))
                .isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    void shouldGetAddressesByUserId() {
        List<AddressDTO> expectedResult = List.of(
                new AddressDTO(0L, "city", "state", "district", "street", "location", 0L));

        User user1 = new User();
        user1.setId(0L);
        user1.setUserName("userName");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("email");
        Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(0L)).thenReturn(user);

        Address address = new Address();
        address.setId(0L);
        address.setCity("city");
        address.setState("state");
        address.setDistrict("district");
        User user2 = new User();
        address.setUser(user2);
        List<Address> addresses = List.of(address);
        when(mockAddressRepository.findByUser(any(User.class))).thenReturn(addresses);

        AddressDTO addressDTO = new AddressDTO(0L, "city", "state", "district", "street", "location", 0L);
        when(mockAddressMapper.convertToAddressDTO(any(Address.class))).thenReturn(addressDTO);

        List<AddressDTO> result = addressServiceImplUnderTest.getAddressesByUserId(0L);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldGetAddressesByUserId_UserRepositoryReturnsAbsent() {
        when(mockUserRepository.findById(0L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> addressServiceImplUnderTest.getAddressesByUserId(0L))
                .isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    void shouldGetAddressesByUserId_AddressRepositoryReturnsNoItems() {

        User user1 = new User();
        user1.setId(0L);
        user1.setUserName("userName");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("email");
        Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(0L)).thenReturn(user);

        when(mockAddressRepository.findByUser(any(User.class))).thenReturn(Collections.emptyList());

        List<AddressDTO> result = addressServiceImplUnderTest.getAddressesByUserId(0L);

        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void shouldSaveAddress() {
        AddressSaveRequest request = new AddressSaveRequest("city", "state", "district", "street", "location",
                0L);
        AddressResponse expectedResult = new AddressResponse("city", "state", "district", "street");

        Address address = new Address();
        address.setId(0L);
        address.setCity("city");
        address.setState("state");
        address.setDistrict("district");
        User user = new User();
        address.setUser(user);
        when(mockAddressMapper.convertToAddress(
                new AddressSaveRequest("city", "state", "district", "street", "location", 0L))).thenReturn(address);

        User user1 = new User();
        user1.setId(0L);
        user1.setUserName("userName");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("email");
        when(mockUserService.getUserEntity(0L)).thenReturn(user1);

        Address address1 = new Address();
        address1.setId(0L);
        address1.setCity("city");
        address1.setState("state");
        address1.setDistrict("district");
        User user2 = new User();
        address1.setUser(user2);
        when(mockAddressRepository.save(any(Address.class))).thenReturn(address1);

        AddressResponse addressResponse = new AddressResponse("city", "state", "district", "street");
        when(mockAddressMapper.convertToAddressResponse(any(Address.class))).thenReturn(addressResponse);

        AddressResponse result = addressServiceImplUnderTest.saveAddress(request);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldUpdateAddress() {
        AddressUpdateRequest request = new AddressUpdateRequest(0L, "city", "state", "district", "street",
                "location");
        AddressResponse expectedResult = new AddressResponse("city", "state", "district", "street");

        Address address1 = new Address();
        address1.setId(0L);
        address1.setCity("city");
        address1.setState("state");
        address1.setDistrict("district");
        User user = new User();
        address1.setUser(user);
        Optional<Address> address = Optional.of(address1);
        when(mockAddressRepository.findById(0L)).thenReturn(address);

        AddressResponse addressResponse = new AddressResponse("city", "state", "district", "street");
        when(mockAddressMapper.convertToAddressResponse(any(Address.class))).thenReturn(addressResponse);

        AddressResponse result = addressServiceImplUnderTest.updateAddress(request);

        assertThat(result).isEqualTo(expectedResult);
        verify(mockAddressMapper).updateAddressRequestToUser(any(Address.class),
                eq(new AddressUpdateRequest(0L, "city", "state", "district", "street", "location")));
        verify(mockAddressRepository).save(any(Address.class));
    }

    @Test
    void shouldUpdateAddress_AddressRepositoryFindByIdReturnsAbsent() {
        AddressUpdateRequest request = new AddressUpdateRequest(0L, "city", "state", "district", "street",
                "location");
        when(mockAddressRepository.findById(0L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> addressServiceImplUnderTest.updateAddress(request))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void shouldDeleteAddressById() {
        addressServiceImplUnderTest.deleteAddressById(0L);

        verify(mockAddressRepository).deleteById(0L);
    }
}
