package com.javarush.borisov.util;


import liquibase.Scope;
import liquibase.command.CommandScope;
import liquibase.resource.ClassLoaderResourceAccessor;
import java.sql.Connection;
import java.sql.Statement;

public  class DbUpdate {


    public static void start(AppStartConfig appConfig)  {

        System.out.println("Running Liquibase...");

        String schema = appConfig.get("DBSchema");

        try (Connection conn = MySessionCreator.firstRun()) {
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("DROP DATABASE IF EXISTS " + schema);
                stmt.executeUpdate("CREATE DATABASE " + schema);
            }
            System.out.println("База данных успешно пересоздана: " + schema);
        }catch (Exception e) {
            e.printStackTrace();
        }

//        try (Session session = MySessionCreator.firstRun().openSession()) {
//            session.beginTransaction();
//            session.createNativeQuery("DROP DATABASE IF EXISTS " + schema).executeUpdate();
//            session.createNativeQuery("CREATE DATABASE " + schema).executeUpdate();
//            session.getTransaction().commit();
//
//        }


        try {
            Scope.child(Scope.Attr.resourceAccessor, new ClassLoaderResourceAccessor(), () -> {
                CommandScope update = new CommandScope("update");

                update.addArgumentValue("changelogFile", "changelog.xml");
                update.addArgumentValue("url", "jdbc:mysql://localhost:3306/" + schema);
                update.addArgumentValue("username", "root");
                update.addArgumentValue("password", "root");

                update.execute();
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        System.out.println("Running Liquibase...DONE");
    }
}