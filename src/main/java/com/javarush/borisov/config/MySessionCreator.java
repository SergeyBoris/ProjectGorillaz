package com.javarush.borisov.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

public class MySessionCreator {
    private static MySessionCreator instance;
    private static SessionFactory sessionFactory;
    private static Configuration configuration;

    private MySessionCreator() {
         configuration = new Configuration().configure();

        sessionFactory = configuration.buildSessionFactory();
    }


    public static SessionFactory getSessionCreator() {
        if (instance == null) {
            instance = new MySessionCreator();
        }
        return instance.sessionFactory;
    }
    public static SessionFactory firstRun() {

        configuration = new Configuration().configure();
        configuration.setProperty(Environment.URL, "jdbc:mysql://localhost:3306");
        return configuration.buildSessionFactory();
    }

}
