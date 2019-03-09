package com.scuticommerce.config.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ConfigClient {

    @Value("${scuticommerce.configservice.url:http://localhost:8090/api/config/dynamic}")
    private static String serviceUrl;

    @Autowired
    ScutiRestTemplate restTemplate;

    public static void main(String[] args) {


        ConfigClient client = new ConfigClient();
        client.getConfig();
    }

    public Map getConfig() {

        Map<?,?> config = new HashMap<>();


        System.out.println(serviceUrl);
        if (serviceUrl == null) {
            serviceUrl = "http://localhost:8090/api/config/dynamic";
        }
        ScutiRestTemplate restTemplate = new ScutiRestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(
                serviceUrl,String.class);

        ObjectMapper mapper = new ObjectMapper();

        try {

            config = mapper.readValue(response.getBody(), LinkedHashMap.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

       return config;
    }
}
