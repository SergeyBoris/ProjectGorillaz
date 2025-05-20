package com.javarush.borisov.db.Service;

import com.javarush.borisov.db.Dao.UserDao;
import com.javarush.borisov.db.Dto.UserDto;
import com.javarush.borisov.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    public List<UserDto> getAllUsersDto(){

        UserDao userDao = new UserDao();
        List<User> all = userDao.getAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : all) {
            userDtos.add(new UserDto(user));
        }
        return userDtos;
    }
}
