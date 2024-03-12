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
        final List<AddressDTO> expectedResult = List.of(
                new AddressDTO(0L, "city", "state", "district", "street", "location", 0L));

        // Configure AddressRepository.findAll(...).
        final Address address = new Address();
        address.setId(0L);
        address.setCity("city");
        address.setState("state");
        address.setDistrict("district");
        final User user = new User();
        address.setUser(user);
        final List<Address> addresses = List.of(address);
        when(mockAddressRepository.findAll()).thenReturn(addresses);

        // Configure AddressMapper.convertToAddressDTO(...).
        final AddressDTO addressDTO = new AddressDTO(0L, "city", "state", "district", "street", "location", 0L);
        when(mockAddressMapper.convertToAddressDTO(any(Address.class))).thenReturn(addressDTO);

        // Run the test
        final List<AddressDTO> result = addressServiceImplUnderTest.getAllAddresses();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldGetAllAddresses_AddressRepositoryReturnsNoItems() {
        // Setup
        when(mockAddressRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<AddressDTO> result = addressServiceImplUnderTest.getAllAddresses();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void shouldGetAddressById() {
        // Setup
        final AddressDTO expectedResult = new AddressDTO(0L, "city", "state", "district", "street", "location", 0L);

        // Configure AddressRepository.findById(...).
        final Address address1 = new Address();
        address1.setId(0L);
        address1.setCity("city");
        address1.setState("state");
        address1.setDistrict("district");
        final User user = new User();
        address1.setUser(user);
        final Optional<Address> address = Optional.of(address1);
        when(mockAddressRepository.findById(0L)).thenReturn(address);

        // Configure AddressMapper.convertToAddressDTO(...).
        final AddressDTO addressDTO = new AddressDTO(0L, "city", "state", "district", "street", "location", 0L);
        when(mockAddressMapper.convertToAddressDTO(any(Address.class))).thenReturn(addressDTO);

        // Run the test
        final AddressDTO result = addressServiceImplUnderTest.getAddressById(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldGetAddressById_AddressRepositoryReturnsAbsent() {
        // Setup
        when(mockAddressRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> addressServiceImplUnderTest.getAddressById(0L))
                .isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    void shouldGetAddressesByUserId() {
        // Setup
        final List<AddressDTO> expectedResult = List.of(
                new AddressDTO(0L, "city", "state", "district", "street", "location", 0L));

        // Configure UserRepository.findById(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setUserName("userName");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("email");
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(0L)).thenReturn(user);

        // Configure AddressRepository.findByUser(...).
        final Address address = new Address();
        address.setId(0L);
        address.setCity("city");
        address.setState("state");
        address.setDistrict("district");
        final User user2 = new User();
        address.setUser(user2);
        final List<Address> addresses = List.of(address);
        when(mockAddressRepository.findByUser(any(User.class))).thenReturn(addresses);

        // Configure AddressMapper.convertToAddressDTO(...).
        final AddressDTO addressDTO = new AddressDTO(0L, "city", "state", "district", "street", "location", 0L);
        when(mockAddressMapper.convertToAddressDTO(any(Address.class))).thenReturn(addressDTO);

        // Run the test
        final List<AddressDTO> result = addressServiceImplUnderTest.getAddressesByUserId(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldGetAddressesByUserId_UserRepositoryReturnsAbsent() {
        // Setup
        when(mockUserRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> addressServiceImplUnderTest.getAddressesByUserId(0L))
                .isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    void shouldGetAddressesByUserId_AddressRepositoryReturnsNoItems() {
        // Setup
        // Configure UserRepository.findById(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setUserName("userName");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("email");
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(0L)).thenReturn(user);

        when(mockAddressRepository.findByUser(any(User.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<AddressDTO> result = addressServiceImplUnderTest.getAddressesByUserId(0L);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void shouldSaveAddress() {
        // Setup
        final AddressSaveRequest request = new AddressSaveRequest("city", "state", "district", "street", "location",
                0L);
        final AddressResponse expectedResult = new AddressResponse("city", "state", "district", "street");

        // Configure AddressMapper.convertToAddress(...).
        final Address address = new Address();
        address.setId(0L);
        address.setCity("city");
        address.setState("state");
        address.setDistrict("district");
        final User user = new User();
        address.setUser(user);
        when(mockAddressMapper.convertToAddress(
                new AddressSaveRequest("city", "state", "district", "street", "location", 0L))).thenReturn(address);

        // Configure UserService.getUserEntity(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setUserName("userName");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("email");
        when(mockUserService.getUserEntity(0L)).thenReturn(user1);

        // Configure AddressRepository.save(...).
        final Address address1 = new Address();
        address1.setId(0L);
        address1.setCity("city");
        address1.setState("state");
        address1.setDistrict("district");
        final User user2 = new User();
        address1.setUser(user2);
        when(mockAddressRepository.save(any(Address.class))).thenReturn(address1);

        // Configure AddressMapper.convertToAddressResponse(...).
        final AddressResponse addressResponse = new AddressResponse("city", "state", "district", "street");
        when(mockAddressMapper.convertToAddressResponse(any(Address.class))).thenReturn(addressResponse);

        // Run the test
        final AddressResponse result = addressServiceImplUnderTest.saveAddress(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldUpdateAddress() {
        // Setup
        final AddressUpdateRequest request = new AddressUpdateRequest(0L, "city", "state", "district", "street",
                "location");
        final AddressResponse expectedResult = new AddressResponse("city", "state", "district", "street");

        // Configure AddressRepository.findById(...).
        final Address address1 = new Address();
        address1.setId(0L);
        address1.setCity("city");
        address1.setState("state");
        address1.setDistrict("district");
        final User user = new User();
        address1.setUser(user);
        final Optional<Address> address = Optional.of(address1);
        when(mockAddressRepository.findById(0L)).thenReturn(address);

        // Configure AddressMapper.convertToAddressResponse(...).
        final AddressResponse addressResponse = new AddressResponse("city", "state", "district", "street");
        when(mockAddressMapper.convertToAddressResponse(any(Address.class))).thenReturn(addressResponse);

        // Run the test
        final AddressResponse result = addressServiceImplUnderTest.updateAddress(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockAddressMapper).updateAddressRequestToUser(any(Address.class),
                eq(new AddressUpdateRequest(0L, "city", "state", "district", "street", "location")));
        verify(mockAddressRepository).save(any(Address.class));
    }

    @Test
    void shouldUpdateAddress_AddressRepositoryFindByIdReturnsAbsent() {
        // Setup
        final AddressUpdateRequest request = new AddressUpdateRequest(0L, "city", "state", "district", "street",
                "location");
        when(mockAddressRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> addressServiceImplUnderTest.updateAddress(request))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void shouldDeleteAddressById() {
        // Setup
        // Run the test
        addressServiceImplUnderTest.deleteAddressById(0L);

        // Verify the results
        verify(mockAddressRepository).deleteById(0L);
    }
}
