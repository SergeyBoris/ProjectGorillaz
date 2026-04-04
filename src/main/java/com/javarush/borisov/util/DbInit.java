package com.javarush.borisov.util;


import com.javarush.borisov.db.constants.EquipmentStatus;
import com.javarush.borisov.db.constants.RequestStatus;
import com.javarush.borisov.entity.*;
import com.javarush.borisov.util.temp.DbRealInit;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Transactional
public class DbInit {
    static int COUNT_CREATED_TEST_REQUEST = 100;

    public static void start(AppStartConfig appStartConfig) {

       // CreateEquipment();
        DbRealInit.init(appStartConfig);
        //CreateReq();


    }

    private static void CreateReq() {

        try (Session session = MySessionCreator.getSessionCreator().openSession()) {

            for (int i = 0; i < COUNT_CREATED_TEST_REQUEST; i++) {
                session.beginTransaction();
                Request request1 = new Request();
                request1.setReqNumber("APOS-" + String.valueOf(random(1000, 9999)));
                request1.setCustomer("ИП Пупкин");
                request1.setAddress("653039, Кемеровская область - Кузбасс, г Прокопьевск, пр-кт Гагарина, д 3");
                request1.setContragent(session.get(Contragent.class, (long) random(1, 4)));
                request1.setCustomerPhone("+79049600005");

                request1.setEquipmentsMontage(new ArrayList<>(session.createQuery("from Equipment where id = :id1 or id = :id2", Equipment.class)
                        .setParameter("id1", (long) random(1, 5))
                        .setParameter("id2", (long) random(0, 10))
                        .list()));




                int random1 = random(1, 7);
                if (random1 <= 4) {
                    List<Equipment> id = List.of(session.createQuery(
                                    "from Equipment where id = :id", Equipment.class)
                            .setParameter("id", (long) random1)
                            .uniqueResult());

                    request1.setEquipmentsUnmontage(id);
                }
                request1.setSla(LocalDateTime.now().plusDays(2));
                request1.setStatus(RequestStatus.values()[random(2, 4)]);
                request1.setUser(session.createQuery("from User where id = :id", User.class)
                        .setParameter("id", (long) random(1, 3))
                        .uniqueResult());
                if (random(0, 100) > 90) {
                    request1.setStatus(RequestStatus.values()[random(0, 2)]);
                }
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
            equipment1.setEquipmentStatus(EquipmentStatus.WAREHOUSE);
            equipment1.setContragent(session.get(Contragent.class, 1L));

            Equipment equipment2 = new Equipment();
            equipment2.setModel("AISINO V10");
            equipment2.setSerialNumber("1234567891");
            equipment2.setEquipmentStatus(EquipmentStatus.WAREHOUSE);
            equipment2.setContragent(session.get(Contragent.class, 1L));

            Equipment equipment3 = new Equipment();
            equipment3.setModel("PAX Q25");
            equipment3.setSerialNumber("1234567892");
            equipment3.setEquipmentStatus(EquipmentStatus.WAREHOUSE);
            equipment3.setContragent(session.get(Contragent.class, 2L));

            Equipment equipment4 = new Equipment();
            equipment4.setModel("PAX S300");
            equipment4.setSerialNumber("1234567893");
            equipment4.setEquipmentStatus(EquipmentStatus.WAREHOUSE);
            equipment4.setContragent(session.get(Contragent.class, 2L));

            session.save(equipment1);
            session.save(equipment2);
            session.save(equipment3);
            session.save(equipment4);
            session.getTransaction().commit();

        }
    }


}

