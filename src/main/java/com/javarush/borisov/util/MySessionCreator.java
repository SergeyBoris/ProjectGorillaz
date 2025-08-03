package com.javarush.borisov.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySessionCreator {

    private static MySessionCreator instance;
    private static SessionFactory sessionFactory;
    private static Configuration configuration;

    private MySessionCreator() {
        configuration = new Configuration().configure(); // Читает hibernate.cfg.xml
        sessionFactory = configuration.buildSessionFactory();
    }

    public static SessionFactory getSessionCreator() {
        if (instance == null) {
            instance = new MySessionCreator();
        }
        return sessionFactory;
    }

    public static Configuration getConfiguration() {
        if (instance == null) {
            instance = new MySessionCreator();
        }
        return configuration;
    }

    /**
     * Используется для подключения к серверу MySQL без указания имени базы.
     * Это нужно для DROP/CREATE DATABASE перед стартом Hibernate.
     */
    public static Connection firstRun() {
        Configuration config = new Configuration().configure();

        String fullUrl = config.getProperty("hibernate.connection.url");
        String username = config.getProperty("hibernate.connection.username");
        String password = config.getProperty("hibernate.connection.password");

        String baseUrl;
        // Убираем имя БД из строки, оставляя только до последнего слэша
        if (fullUrl != null && fullUrl.startsWith("jdbc:mysql://")) {
            int slashIndex = fullUrl.indexOf("/", "jdbc:mysql://".length());
            if (slashIndex > 0) {
                baseUrl = fullUrl.substring(0, slashIndex);
            } else {
                throw new IllegalArgumentException("Некорректный URL: не найдено имя БД в " + fullUrl);
            }
        } else {
            throw new IllegalArgumentException("Некорректный URL: " + fullUrl);
        }

        try {
            return DriverManager.getConnection(baseUrl, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось подключиться к MySQL без указания БД", e);
        }
    }
}