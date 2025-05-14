package com.javarush.borisov.db;

import com.javarush.borisov.config.AppConfig;
import com.javarush.borisov.config.ClassCreator;
import com.javarush.borisov.config.MySessionCreator;
import liquibase.Scope;
import liquibase.command.CommandScope;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.hibernate.Session;

public  class DbUpdate {


    public static void start() throws Exception {
        AppConfig appConfig = ClassCreator.get(AppConfig.class);
        System.out.println("Running Liquibase...");

        String schema = appConfig.get("DBSchema");
        try (Session session = MySessionCreator.firstRun().openSession()) {
            session.beginTransaction();
            session.createNativeQuery("CREATE DATABASE IF NOT EXISTS " + schema).executeUpdate();
            session.getTransaction().commit();
        }


        Scope.child(Scope.Attr.resourceAccessor, new ClassLoaderResourceAccessor(), () -> {
            CommandScope update = new CommandScope("update");

            update.addArgumentValue("changelogFile", "changelog.xml");
            update.addArgumentValue("url", "jdbc:mysql://localhost:3306/" + schema);
            update.addArgumentValue("username", "root");
            update.addArgumentValue("password", "root");

            update.execute();
        });




        System.out.println("Running Liquibase...DONE");
    }
}