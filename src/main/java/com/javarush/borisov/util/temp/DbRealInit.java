package com.javarush.borisov.util.temp;

import com.javarush.borisov.db.constants.EquipmentStatus;
import com.javarush.borisov.entity.Contragent;
import com.javarush.borisov.entity.Equipment;
import com.javarush.borisov.entity.Request;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DbRealInit {
    private static final ExcelRW excelRW = new ExcelRW();
    private static final Configuration configuration = new Configuration();
    private static Session session;

    public static void main(String[] args) {

        configuration.configure("hibernate.cfg.xml"); // путь к файлу конфигурации

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            session = sessionFactory.openSession();
            requestAdd();
            //equipmentAdd();
        }


    }

    private static void requestAdd() {
        List<String[]> req = excelRW.read("E:/НОВОКУЗНЕЦК.xlsm", "Альфа", 7757, 7758, 0, 16);


        for (String[] row : req) {
            session.beginTransaction();
            Request request = new Request();
            request.setReqNumber(row[0]);
            request.setParameters(Map.of("TST", row[1],"priority",row[10],"zone",row[11]));
            request.setCustomer(row[2]);
            request.setCustomerPhone(row[3]);
            request.setTid(row[4]);
            request.setWorkType(row[5]);


            request.setEquipmentsMontage(getEquipment(row,6));
            request.setEquipmentsUnmontage(getEquipment(row,8));
            request.setAddress(row[10]);
            request.setCloseDate(LocalDateTime.parse(row[13]));
            request.setComment(row[15]);

            session.persist(request);
            session.getTransaction().commit();
        }




        session.close();



    }

    private static Set<Equipment> getEquipment(String[] row, int indexModel) {

        if (row[indexModel] != null) {

            if (row[indexModel].contains("/")) {
                Equipment equipment = new Equipment();
                Equipment equipment2 = new Equipment();
                String[] splitModel = row[indexModel].split("/");
                equipment.setModel(splitModel[0]);
                equipment2.setModel(splitModel[1]);
                String[] splitSerial = row[indexModel+1].split("/");
                equipment.setSerialNumber(splitSerial[0]);
                equipment2.setSerialNumber(splitSerial[1]);
                return Set.of(equipment, equipment2);

            }else {
                Equipment equipment = new Equipment();
                equipment.setModel(row[indexModel]);
                equipment.setSerialNumber(row[indexModel+1]);

                return Set.of(equipment);
            }

        }
        return null;

    }


    private static void equipmentAdd() {

        List<String[]> list = excelRW.read("E:/НОВОКУЗНЕЦК.xlsm", "Склад Альфа", 1, 6917, 1, 4);
        Contragent contragent = session.get(Contragent.class, 2L);

        for (String[] strings : list) {
            Equipment equipment = new Equipment();
            equipment.setModel(strings[0]);
            equipment.setSerialNumber(strings[1]);
            if (strings[2].equalsIgnoreCase("склад") || strings[2].equalsIgnoreCase("Вова")) {
                equipment.setEquipmentStatus(EquipmentStatus.GOOD);
            } else if (strings[2].equalsIgnoreCase("нет")) {
                equipment.setEquipmentStatus(EquipmentStatus.ON_REQUEST);
            } else {
                equipment.setEquipmentStatus(EquipmentStatus.DEPARTED);
            }
            equipment.setContragent(contragent);
            session.beginTransaction();
            try {
                session.save(equipment);
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
            session.getTransaction().commit();

            System.out.println(strings[0] + " " + strings[1] + " " + strings[2]);
        }

    }
}
