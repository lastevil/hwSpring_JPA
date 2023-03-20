package com.hw.spring.auth.converters;

import com.hw.spring.auth.dto.UserDto;
import com.hw.spring.auth.entitys.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public UserDto fromEntity(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setCreatedAt(user.getCreatedAt());
        return userDto;
    }
}
