package com.hw.orders.services;


import com.hw.orders.converters.AddressConverter;
import com.hw.orders.entitys.Address;
import com.hw.orders.repositorys.AddressRepository;
import com.hw.orders.dto.AddressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressConverter addressConverter;

    public void addAddress(AddressDto addressDto, String username) {
        List<Address> addresses = addressRepository.findAllByUsername(username);
        Address addressFromDto = addressConverter.fromDto(addressDto,username);
        for (Address address:addresses){
            if (address.equals(addressFromDto)){
                return;
            }
        }
        addressRepository.save(addressFromDto);
    }

    public List<AddressDto> getAddresses(String username) {
        List<Address> addresses = addressRepository.findAllByUsername(username);
        return addresses.stream().map(a->addressConverter.fromEntity(a)).collect(Collectors.toList());
    }

    public Address getAddress(Long id){
        return addressRepository.getById(id);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
