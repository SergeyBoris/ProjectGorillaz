package com.javarush.borisov.db.Dao;

import com.javarush.borisov.config.MySessionCreator;
import com.javarush.borisov.entity.Contragent;
import com.javarush.borisov.entity.Equipment;
import org.hibernate.Session;

import java.util.List;

public class EquipmentDao extends AbstractDao<Equipment> {
    public EquipmentDao() {
        super(Equipment.class);
    }

    public List<Equipment> getBySerialAndContragentLimit(String serial, String contragent, int limit) {
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
            ContragentDao contragentDao = new ContragentDao();

            if (contragent == null || contragent.equals("Выбор") || contragent.isEmpty()){
                return session.createQuery("select e from Equipment e " +
                                "where e.serialNumber like :serial and " +
                                "e.contragent is null ", Equipment.class)
                        .setMaxResults(limit)
                        .setParameter("serial", "%" + serial + "%")
                        .list();
            }else {


            Contragent contragent1 = contragentDao.getContragentByName(contragent);

          return session.createQuery("select e from Equipment e " +
                    "where e.serialNumber like :serial and " +
                    "e.contragent = :contragent", Equipment.class)
                    .setMaxResults(limit)
                    .setParameter("serial", "%" + serial + "%")
                    .setParameter("contragent",contragent1 ).list();
        }
            }
    }
    public Equipment getBySerialUnique (String serial) {
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
            return session.createQuery("from Equipment where serialNumber=:serial",Equipment.class)
                    .setParameter("serial", serial)
                    .uniqueResult();
        }
    }
}
