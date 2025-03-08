package com.javarush.borisov.db;

import liquibase.Scope;
import liquibase.command.CommandScope;
import liquibase.resource.ClassLoaderResourceAccessor;

public  class DbUpdate {


    public static void main(String[] args) throws Exception {
        System.out.println("Running Liquibase...");

        Scope.child(Scope.Attr.resourceAccessor, new ClassLoaderResourceAccessor(), () -> {
            CommandScope update = new CommandScope("update");

            update.addArgumentValue("changelogFile", "changelog.xml");
            update.addArgumentValue("url", "jdbc:postgresql://localhost:5432/db_req");
            update.addArgumentValue("username", "sergant");
            update.addArgumentValue("password", "123456");

            update.execute();
        });

        System.out.println("Running Liquibase...DONE");
    }
}