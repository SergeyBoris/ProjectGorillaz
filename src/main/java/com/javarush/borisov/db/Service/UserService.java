package com.javarush.borisov.db.Service;

import com.javarush.borisov.db.Dao.UserDao;
import com.javarush.borisov.entity.dto.old.UserDtoOld;
import com.javarush.borisov.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    public List<UserDtoOld> getAllUsersDto(){

        UserDao userDao = new UserDao();
        List<User> all = userDao.getAll();
        List<UserDtoOld> userDtoOlds = new ArrayList<>();
        for (User user : all) {
            userDtoOlds.add(new UserDtoOld(user));
        }
        return userDtoOlds;
    }
}
