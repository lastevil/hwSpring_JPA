package com.gbhw.hwSpring_JPA.services;

import com.gbhw.hwSpring_JPA.converters.AddressConverter;
import com.gbhw.hwSpring_JPA.dto.AddressDto;
import com.gbhw.hwSpring_JPA.dto.exceptions.ResourceNotFoundException;
import com.gbhw.hwSpring_JPA.entitys.Address;
import com.gbhw.hwSpring_JPA.entitys.User;
import com.gbhw.hwSpring_JPA.repositorys.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressConverter addressConverter;
    private final UserService userService;

    public void addAddress(AddressDto addressDto, String userName) {
        User user = (User) userService.findByUsername(userName).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        List<Address> addresses = addressRepository.findAddressesByUserId(user.getId());
        Address addressFromDto = addressConverter.fromDto(addressDto,user);
        for (Address address:addresses){
            if (address.equals(addressFromDto)){
                return;
            }
        }
        addressRepository.save(addressFromDto);
    }

    public List<AddressDto> getAddresses(String userName) {
        User user = (User) userService.findByUsername(userName).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        List<Address> addresses = addressRepository.findAddressesByUserId(user.getId());
        return addresses.stream().map(a->addressConverter.fromEntity(a)).collect(Collectors.toList());
    }

    public Address getAddress(Long id){
        return addressRepository.getById(id);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
