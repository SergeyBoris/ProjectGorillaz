package com.javarush.borisov.util;


import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AppStartConfig {

    private Map<String,String> appConfigs;
    ClassLoader appConfigClassLoader = com.javarush.borisov.util.AppStartConfig.class.getClassLoader();
    public AppStartConfig() {
        build();
    }

    private void build() {
        appConfigs = new HashMap<>();

        try (InputStream input = appConfigClassLoader.getResourceAsStream("appconfig.yaml")) {
            if (input == null) {
                throw new RuntimeException("appconfig.yaml not found");
            }
            Yaml yaml = new Yaml();
            appConfigs = yaml.load(input);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String get(String key) {
        return appConfigs.get(key);
    }

}
