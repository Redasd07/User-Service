package com.user.service;

import com.user.dto.UserDto;
import com.user.entity.User;
import com.user.repository.UserRepository;
import com.user.util.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final DtoConverter dtoConverter;

    @Autowired
    public UserService(UserRepository userRepository, DtoConverter dtoConverter) {
        this.userRepository = userRepository;
        this.dtoConverter = dtoConverter;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(dtoConverter::toUserDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));
        return dtoConverter.toUserDto(user);
    }

    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email déjà utilisé.");
        }

        User user = dtoConverter.toUserEntity(userDto);
        user.setEmailVerified(false);
        User savedUser = userRepository.save(user);
        return dtoConverter.toUserDto(savedUser);
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));

        existingUser.setNom(userDto.getNom());
        existingUser.setPrenom(userDto.getPrenom());
        existingUser.setPhone(userDto.getPhone());
        existingUser.setRole(userDto.getRole());

        User updatedUser = userRepository.save(existingUser);
        return dtoConverter.toUserDto(updatedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur non trouvé.");
        }
        userRepository.deleteById(id);
    }
}
