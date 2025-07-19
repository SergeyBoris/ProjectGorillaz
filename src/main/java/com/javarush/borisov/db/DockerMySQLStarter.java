package com.javarush.borisov.db;
import com.javarush.borisov.config.AppConfig;
import com.javarush.borisov.config.ClassCreator;
import com.javarush.borisov.config.MySessionCreator;
import com.javarush.borisov.db.constants.UserRoles;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebListener;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class DockerMySQLStarter implements ServletContextListener {
    private final DataSourceProperties dataSourceProperties;
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC драйвер зарегистрирован.");
        } catch (ClassNotFoundException e) {
            System.err.println("Не удалось найти драйвер MySQL!");
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void startAndWait() {
        AppConfig appConfig = ClassCreator.get(AppConfig.class);
        startOrRestartMySQL();
        waitForMySQLReady();


        if(appConfig.get("firstRun").equals("true")) {
            // DockerMySQLStarterWSL startOrRestartWSL = new DockerMySQLStarterWSL();
            // startOrRestartWSL.start();
            DbUpdate.start();
            DbInit.start();
        }


        MySessionCreator.getSessionCreator();
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
                    sleep(5);
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
                sleep(8);
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

    private static void sleep(int second) {
        try {
            System.out.println("Ждём " + second + " сек...");
            Thread.sleep(second * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void waitForMySQLReady() {
        String jdbcUrl = dataSourceProperties.getUrl();
        String user = dataSourceProperties.getUsername();
        String password = dataSourceProperties.getPassword();

        int retries = 10;
        int waitTime = 3000; // 3 секунды

        for (int i = 0; i < retries; i++) {
            try (Connection conn = DriverManager.getConnection(jdbcUrl, user, password)) {

                System.out.println("MySQL доступен, подключение успешно!");
                return;
            } catch (SQLException e) {
                System.out.println(jdbcUrl);
                System.out.println(user);
                System.out.println(password);
                System.out.printf("MySQL ещё не доступен (%s: %s), ждем...%n",
                        e.getClass().getSimpleName(), e.getMessage());
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        throw new RuntimeException("Не удалось подключиться к MySQL после " + (retries * waitTime / 1000) + " секунд");
    }


}