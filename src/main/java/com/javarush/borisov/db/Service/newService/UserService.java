package com.javarush.borisov.db.Service.newService;

import com.javarush.borisov.db.Dao.UserDao;
import com.javarush.borisov.db.Repository.UserRepo;
import com.javarush.borisov.entity.dto.UserDto;
import com.javarush.borisov.entity.dto.old.UserDtoOld;
import com.javarush.borisov.entity.User;
import com.javarush.borisov.entity.mapper.UserMapperImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepository;
    private final UserMapperImpl userMapperImpl;

    public List<UserDto> getAllUsersDto(){
                return userRepository.findAll().stream().map(userMapperImpl::toDto).toList();
    }
}
