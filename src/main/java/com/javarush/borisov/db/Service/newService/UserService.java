package com.javarush.borisov.db.Service.newService;

import com.javarush.borisov.db.Repository.UserRepo;
import com.javarush.borisov.entity.dto.UserDto;
import com.javarush.borisov.entity.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAllUsersDto(){
                return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }
    public UserDto getUserByEmail(String email){
        return userRepository.findByMail(email).stream().map(userMapper::toDto).toList().get(0);
    }
}
