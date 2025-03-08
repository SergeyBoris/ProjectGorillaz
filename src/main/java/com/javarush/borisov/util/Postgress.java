package com.javarush.borisov.util;

import java.sql.*;
import java.time.LocalDateTime;

public class Postgress {
    public static void main(String[] args) throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");
        //напишите тут ваш код
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/javarush",
                "root", "admin");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select name,weight,created,id from employee");
        int columnCount = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()) {
            System.out.printf("%S %S %S %S\n"
                    , resultSet.getObject("name", String.class)
                    , resultSet.getObject(2, Float.class)
                    , resultSet.getDate(3 )
                    , resultSet.getObject(4, Long.class));
        }
        statement.close();
        connection.close();

    }


}
