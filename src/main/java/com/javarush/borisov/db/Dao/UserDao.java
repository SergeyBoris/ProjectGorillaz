package com.javarush.borisov.db.Dao;

import com.javarush.borisov.entity.User;

public class UserDao extends AbstractDao<User> {
    public UserDao() {
        super(User.class);
    }
}
