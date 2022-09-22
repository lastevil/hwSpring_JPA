package com.gbhw.hwSpring_JPA.converters;

import com.gbhw.hwSpring_JPA.dto.AddressDto;
import com.gbhw.hwSpring_JPA.entitys.Address;
import com.gbhw.hwSpring_JPA.entitys.User;
import org.springframework.stereotype.Component;

@Component
public class AddressConverter {
    public Address fromDto(AddressDto addressDto, User user) {
        Address address = new Address();
        address.setCountry(addressDto.getCountry());
        address.setCity(addressDto.getCity());
        address.setStreet(addressDto.getStreet());
        address.setHouse(addressDto.getHouse());
        address.setPostIndex(addressDto.getPostIndex());
        address.setUser(user);
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
