package com.javarush.borisov.db;
import com.javarush.borisov.config.AppConfig;
import com.javarush.borisov.config.ClassCreator;
import com.javarush.borisov.constants.UserRoles;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@WebListener
public class DockerMySQLStarter implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AppConfig appConfig = ClassCreator.get(AppConfig.class);
        if(appConfig.get("firstRun").equals("true")) {

            try {

                startOrRestartMySQL();
                DbUpdate.start();
                DbInit.start();


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void startOrRestartMySQL() {
        String containerName = "mysql-container";
        String password = "root";

        try {
            if (isContainerRunning(containerName)) {
                System.out.println("Контейнер уже запущен, ничего не делаем.");
                return;
            }

            if (isContainerExists(containerName)) {
                System.out.println("⚡ Контейнер существует, но остановлен. Запускаем его...");
                ProcessBuilder startProcessBuilder = new ProcessBuilder("docker", "start", containerName);
                startProcessBuilder.inheritIO();
                Process startProcess = startProcessBuilder.start();
                int startExitCode = startProcess.waitFor();
                if (startExitCode == 0) {
                    System.out.println("Контейнер успешно запущен.");
                } else {
                    System.out.println("Ошибка запуска контейнера. Код: " + startExitCode);
                }
                return;
            }

            System.out.println("Контейнер не найден. Создаем новый...");
            String[] runCommand = {
                    "docker", "run",
                    "--name", containerName,
                    "-e", "MYSQL_ROOT_PASSWORD=" + password,
                    "-p", "3306:3306",
                    "-d",
                    "mysql:latest"
            };
            ProcessBuilder runProcessBuilder = new ProcessBuilder(runCommand);
            runProcessBuilder.inheritIO();
            Process runProcess = runProcessBuilder.start();
            int runExitCode = runProcess.waitFor();
            if (runExitCode == 0) {
                System.out.println("Новый контейнер MySQL запущен!");
            } else {
                System.out.println("Ошибка запуска нового контейнера. Код: " + runExitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean isContainerRunning(String containerName) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("docker", "ps", "--filter", "name=" + containerName, "--format", "{{.Names}}");
        Process process = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = reader.readLine();

        process.waitFor();
        return line != null && line.trim().equals(containerName);
    }

    private static boolean isContainerExists(String containerName) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("docker", "ps", "-a", "--filter", "name=" + containerName, "--format", "{{.Names}}");
        Process process = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = reader.readLine();

        process.waitFor();
        return line != null && line.trim().equals(containerName);
    }

    private static void roleSyncToUserRoles(){

        for (UserRoles value : UserRoles.values()) {

        }
    }
}