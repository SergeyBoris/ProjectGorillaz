package com.javarush.borisov.db.Dao;

import com.javarush.borisov.config.MySessionCreator;
import com.javarush.borisov.entity.UserRoles;
import org.hibernate.Session;

import java.util.List;

public class UserRoleDao {
    private final Session session;

    public UserRoleDao() {
        session = MySessionCreator.getSessionCreator().openSession();
    }

    public UserRoles getById(Long id){
        return session.createQuery("from UserRoles where id = :id", UserRoles.class).setParameter("id",id).getSingleResult();
    }

    public List<UserRoles> getAll(){
        return session.createQuery("from UserRoles", UserRoles.class).getResultList();
    }


}
