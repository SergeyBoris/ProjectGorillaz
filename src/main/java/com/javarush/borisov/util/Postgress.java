package com.javarush.borisov.util;

import com.javarush.borisov.config.MySessionCreator;
import com.javarush.borisov.constants.RequestStatus;
import com.javarush.borisov.db.Dao.AbstractDao;
import com.javarush.borisov.db.Dao.UserRoleDao;
import com.javarush.borisov.entity.*;
import jakarta.transaction.Transactional;
import org.hibernate.Session;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Transactional
public class Postgress {
    public static void main(String[] args) throws Exception {

        UserRoleDao userRoleDao = new UserRoleDao();
        UserRoles byId = userRoleDao.getById(UserRoles.class,1L);

        //CreateEquipment();
        //CreateUsers();
       // CreateReq();
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
            Long i = session.createQuery(
                            "select count(r) from Request r " +
                            "join r.equipmentsMontage em " +
                            "join r.equipmentsUnmontage eum " +
                            "where em.serialNumber = :serialNumber1 " +
                            "and eum.serialNumber = :serialNumber2 "
                            ,
                            Long.class)
                    .setParameter("serialNumber1", "123456789")
                    .setParameter("serialNumber2", "123456789")
                    .uniqueResult();
            System.out.println(i);
        }

    }

    private static void CreateReq() {

        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
            for (int i = 0; i < 100; i++) {
                session.beginTransaction();
                Request request1 = new Request();
                request1.setReqNumber("APOS-" + String.valueOf(random(1000, 9999)));
                request1.setCustomer("ИП Пупкин");
                request1.setAddress("Новокузнецк Кирова 55 А");
                request1.setContragent(session.get(Contragent.class, (long) random(1, 4)));
                request1.setCustomerPhone("+79049600005");
                request1.setEquipmentsMontage(Set.of(session.createQuery(
                                "from Equipment where id = :id", Equipment.class)
                        .setParameter("id", (long)random(1, 4))
                        .uniqueResult()));

                request1.setEquipmentsUnmontage(Set.of(session.createQuery(
                                "from Equipment where id = :id", Equipment.class)
                        .setParameter("id", (long)random(1, 4))
                        .uniqueResult()));
                request1.setSla(LocalDateTime.now().plusDays(2));
                request1.setStatus(RequestStatus.values()[random(0, 4)]);
                request1.setUser(session.createQuery("from User where id = :id", User.class)
                        .setParameter("id",(long) random(1, 3))
                        .uniqueResult());
                session.save(request1);
                session.getTransaction().commit();
            }

        }
    }

    private static int random(int begin, int end) {
        return ThreadLocalRandom.current().nextInt(begin, end);
    }

    private static void CreateEquipment() {
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
            session.beginTransaction();
            Equipment equipment1 = new Equipment();
            equipment1.setModel("AISINO V80");
            equipment1.setSerialNumber("123456789");

            Equipment equipment2 = new Equipment();
            equipment2.setModel("AISINO V10");
            equipment2.setSerialNumber("1234567891");

            Equipment equipment3 = new Equipment();
            equipment3.setModel("PAX Q25");
            equipment3.setSerialNumber("1234567892");

            Equipment equipment4 = new Equipment();
            equipment4.setModel("PAX S300");
            equipment4.setSerialNumber("1234567893");
            session.save(equipment1);
            session.save(equipment2);
            session.save(equipment3);
            session.save(equipment4);
            session.getTransaction().commit();

        }
    }

    private static void CreateUsers() {
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
            session.beginTransaction();
            User user1 = new User();
            user1.setName("Сергей");
            user1.setMail("serg@mail.ru");
            user1.setPassword("1234");
            user1.setRole(UserRoles.ADMIN);

            User user2 = new User();
            user2.setName("Миша");
            user2.setMail("miha@mail.ru");
            user2.setPassword("1234");
            user2.setRole(UserRoles.ENGINEER);

            User user3 = new User();
            user3.setName("Наташа");
            user3.setMail("nata@mail.ru");
            user3.setPassword("1234");
            user3.setRole(UserRoles.COORDINATOR);

            session.save(user1);
            session.save(user2);
            session.save(user3);


            session.getTransaction().commit();

        }
    }


}
