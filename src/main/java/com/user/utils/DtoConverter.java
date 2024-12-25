package com.user.util;

import com.user.dto.UserDto;
import com.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class DtoConverter {

    public UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setNom(user.getNom());
        userDto.setPrenom(user.getPrenom());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setRole(user.getRole());
        userDto.setEmailVerified(user.isEmailVerified());
        return userDto;
    }

    public User toUserEntity(UserDto userDto) {
        User user = new User();
        user.setNom(userDto.getNom());
        user.setPrenom(userDto.getPrenom());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setRole(userDto.getRole());
        return user;
    }
}
