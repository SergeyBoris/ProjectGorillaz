package com.javarush.borisov.db.Dao;

import com.javarush.borisov.config.MySessionCreator;
import com.javarush.borisov.entity.Contragent;
import org.hibernate.Session;

public class ContragentDao extends AbstractDao<Contragent> {
    public ContragentDao() {
        super(Contragent.class);
    }

    public Contragent getContragentByName(String name) {
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
            return session.createQuery("from Contragent where name = :name",Contragent.class)
                    .setParameter("name", name)
                    .uniqueResult();
        }
    }
}
