package com.javarush.borisov.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DockerMySQLStarterWSL {
    public void start() {

        String containerName = "mysql-container";
        String password = "root";

        try {
            if (isContainerRunningWSL(containerName)) {
                System.out.println("Контейнер уже запущен, ничего не делаем.");
                return;
            }

            if (isContainerExistsWSL(containerName)) {
                System.out.println("⚡ Контейнер существует, но остановлен. Запускаем его...");
                runInWSL("docker start " + containerName);
                return;
            }

            System.out.println("Контейнер не найден. Создаем новый...");
            String runCommand = String.join(" ",
                    "docker run",
                    "--name", containerName,
                    "-e", "MYSQL_ROOT_PASSWORD=" + password,
                    "-p", "3306:3306",
                    "-d", "mysql:latest"
            );
            runInWSL(runCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void runInWSL(String command) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("wsl", "-e", "bash", "-c", command);
        pb.inheritIO(); // Покажет вывод в консоли Java
        Process process = pb.start();
        int code = process.waitFor();
        if (code != 0) {
            System.err.println("Ошибка выполнения WSL-команды: " + command);
        }
    }

    private boolean isContainerRunningWSL(String containerName) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("wsl", "-e", "bash", "-c",
                "docker ps --filter name=" + containerName + " --format '{{.Names}}'");
        Process process = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = reader.readLine();
        process.waitFor();
        return line != null && line.trim().equals(containerName);
    }

    private boolean isContainerExistsWSL(String containerName) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("wsl", "-e", "bash", "-c",
                "docker ps -a --filter name=" + containerName + " --format '{{.Names}}'");
        Process process = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = reader.readLine();
        process.waitFor();
        return line != null && line.trim().equals(containerName);
    }
}