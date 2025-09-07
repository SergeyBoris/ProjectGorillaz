package com.javarush.borisov.util.temp;

import com.javarush.borisov.db.constants.EquipmentStatus;
import com.javarush.borisov.db.constants.RequestStatus;
import com.javarush.borisov.entity.Contragent;
import com.javarush.borisov.entity.Equipment;
import com.javarush.borisov.entity.Request;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DbRealInit {
    private static final ExcelRW excelRW = new ExcelRW();
    private static final Configuration configuration = new Configuration();
    private static Session session;

    public static void init() {

        List<Map<String, String>> requests = excelRW.readReqRow("E:/НОВОКУЗНЕЦК.xlsm", "Альфа", 7831,7883);

        configuration.configure("hibernate.cfg.xml"); // путь к файлу конфигурации

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Contragent contragent = session.get(Contragent.class, 2);
            for (Map<String, String> request : requests) {
                Request requestToSave = new Request();

                if (request.get("ReqNum") != null) {
                    requestToSave.setReqNumber(request.get("ReqNum"));
                }else requestToSave.setReqNumber("111111111111");

                RequestStatus value = RequestStatus.valueOf(request.get("ReqStatus"));
                requestToSave.setStatus(value);

                requestToSave.setCustomer(request.get("Customer"));
                requestToSave.setCustomerPhone(request.get("CustomerPhone"));
                requestToSave.setTid(request.get("Tid"));
                requestToSave.setWorkType(request.get("WorkType"));
                if (request.get("EquipmentMontage") == null) {
                    requestToSave.setEquipmentsMontage(null);
                } else {
                    System.out.println(request.get("EquipmentMontage"));
                    requestToSave.setEquipmentsMontage(eqCreate(request.get("EquipmentMontage"), session,contragent));
                }
                if (request.get("EquipmentUnMontage")==null){
                    requestToSave.setEquipmentsUnmontage(null);
                }else {
                    requestToSave.setEquipmentsUnmontage(eqCreate(request.get("EquipmentUnMontage"), session,contragent));
                }
                requestToSave.setAddress(request.get("Address"));
                Map<String, String> parameters = new LinkedHashMap<>();
                parameters.put("TST", request.get("TST"));
                parameters.put("Priority", request.get("Priority"));
                parameters.put("Zone", request.get("Zone"));
                parameters.put("Sim", request.get("Sim"));
                requestToSave.setParameters(parameters);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                if(request.get("DateOfComplete") != null && !request.get("DateOfComplete").isEmpty() && !request.get("DateOfComplete").isBlank()) {

                    LocalDate dateOfComplete = LocalDate.parse(request.get("DateOfComplete"), formatter);
                    LocalDateTime completeDate = dateOfComplete.atStartOfDay();
                    requestToSave.setCloseDate(completeDate);
                }
                requestToSave.setComment(request.get("Comment"));
                if (request.get("ClosedDate") != null && !request.get("ClosedDate").isEmpty() && !request.get("ClosedDate").isBlank()) {
                    System.out.println("|" + request.get("ClosedDate") + "|");
                    LocalDate closedDate = LocalDate.parse(request.get("ClosedDate"), formatter);
                    LocalDateTime closeDate = closedDate.atStartOfDay();
                    requestToSave.setLastUpdate(closeDate);
                }
                requestToSave.setContragent(contragent);
                System.out.println(requestToSave);
                session.persist(requestToSave);

            }
            session.getTransaction().commit();
            session.close();


            //requestAdd();
            //equipmentAdd();
        }


    }

    private static List<Equipment> eqCreate(String eq, Session session,Contragent contragent) {
        String[] split = eq.split("/");
        if (split.length == 2) {
            Equipment equipment = session.createQuery("from Equipment where serialNumber = :serialNumber", Equipment.class)
                    .setParameter("serialNumber", split[1].trim())
                    .uniqueResult();
            if (equipment == null) {
                equipment = new Equipment();
                equipment.setModel(split[0].trim().toUpperCase(Locale.ROOT));
                equipment.setSerialNumber(split[1].trim());
                equipment.setName("POS");
                equipment.setEquipmentStatus(EquipmentStatus.WAREHOUSE);
                equipment.setContragent(contragent);
                session.persist(equipment);

            }
            return List.of(equipment);
        } else {
            Equipment equipment1 = session.createQuery("from Equipment where serialNumber = :serialNumber", Equipment.class)
                    .setParameter("serialNumber", split[2].trim())
                    .uniqueResult();
            if (equipment1 == null) {
                equipment1 = new Equipment();
                equipment1.setModel(split[0].trim().toUpperCase(Locale.ROOT));
                equipment1.setName("POS");
                equipment1.setSerialNumber(split[2].trim());
                equipment1.setEquipmentStatus(EquipmentStatus.WAREHOUSE);
                equipment1.setContragent(contragent);

                session.persist(equipment1);

            }
            Equipment equipment2= session.createQuery("from Equipment where serialNumber = :serialNumber", Equipment.class)
                    .setParameter("serialNumber", split[3].trim())
                    .uniqueResult();
            if (equipment2 == null) {
                equipment2 = new Equipment();
                equipment2.setModel(split[1].trim().toUpperCase(Locale.ROOT));
                equipment2.setSerialNumber(split[3].trim());
                equipment2.setName("POS");
                equipment2.setEquipmentStatus(EquipmentStatus.WAREHOUSE);
                equipment2.setContragent(contragent);
                session.persist(equipment2);

            }
            return List.of(equipment1, equipment2);

        }
    }

    private static void requestAdd() {
        List<String[]> req = excelRW.read("E:/НОВОКУЗНЕЦК.xlsm", "Альфа", 7757, 7758, 0, 16);


        for (String[] row : req) {
            session.beginTransaction();
            Request request = new Request();
            request.setReqNumber(row[0]);
            request.setParameters(Map.of("TST", row[1], "priority", row[10], "zone", row[11]));
            request.setCustomer(row[2]);
            request.setCustomerPhone(row[3]);
            request.setTid(row[4]);
            request.setWorkType(row[5]);


            request.setEquipmentsMontage(getEquipment(row, 6));
            request.setEquipmentsUnmontage(getEquipment(row, 8));
            request.setAddress(row[10]);
            request.setCloseDate(LocalDateTime.parse(row[13]));
            request.setComment(row[15]);

            session.persist(request);
            session.getTransaction().commit();
        }


        session.close();


    }

    private static List<Equipment> getEquipment(String[] row, int indexModel) {

        if (row[indexModel] != null) {

            if (row[indexModel].contains("/")) {
                Equipment equipment = new Equipment();
                Equipment equipment2 = new Equipment();
                String[] splitModel = row[indexModel].split("/");
                equipment.setModel(splitModel[0]);
                equipment2.setModel(splitModel[1]);
                String[] splitSerial = row[indexModel + 1].split("/");
                equipment.setSerialNumber(splitSerial[0]);
                equipment2.setSerialNumber(splitSerial[1]);
                return List.of(equipment, equipment2);

            } else {
                Equipment equipment = new Equipment();
                equipment.setModel(row[indexModel]);
                equipment.setSerialNumber(row[indexModel + 1]);

                return List.of(equipment);
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
                equipment.setEquipmentStatus(EquipmentStatus.WAREHOUSE);
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
