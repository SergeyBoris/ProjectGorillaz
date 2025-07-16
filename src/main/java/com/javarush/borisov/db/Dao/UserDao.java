package com.javarush.borisov.db.Dao;

import com.javarush.borisov.config.MySessionCreator;
import com.javarush.borisov.entity.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends AbstractDao<User> {
    public UserDao() {
        super(User.class);
    }

    public User getUserByName(String name) {
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
           return session.createQuery("from User where name=:name", User.class)
                   .setParameter("name", name)
                   .uniqueResult();
        }

    }
}
