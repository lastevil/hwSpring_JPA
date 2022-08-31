package com.hw.orders.converters;

import com.hw.orders.entitys.Address;
import com.hw.spring.global.dto.AddressDto;
import org.springframework.stereotype.Component;

@Component
public class AddressConverter {
    public Address fromDto(AddressDto addressDto, String username) {
        Address address = new Address();
        address.setCountry(addressDto.getCountry());
        address.setCity(addressDto.getCity());
        address.setStreet(addressDto.getStreet());
        address.setHouse(addressDto.getHouse());
        address.setPostIndex(addressDto.getPostIndex());
        address.setUsername(username);
        if (addressDto.getFlat() != null) {
            address.setFlat(addressDto.getFlat());
        }
        return address;
    }

    public AddressDto fromEntity(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setCountry(address.getCountry());
        addressDto.setCity(address.getCity());
        addressDto.setStreet(address.getStreet());
        addressDto.setHouse(address.getHouse());
        addressDto.setPostIndex(address.getPostIndex());
        if (address.getFlat() != null) {
            addressDto.setFlat(address.getFlat());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(address.getPostIndex())
                .append(", ").append(address.getCountry())
                        .append(", ").append(address.getCity())
                        .append(", ул.").append(address.getStreet())
                        .append(", дом.").append(address.getHouse());
        if(address.getFlat()!=null){
            stringBuilder.append(", кв.").append(address.getFlat());
        }
        addressDto.setFullAddress(stringBuilder.toString());
        return addressDto;
    }
}
