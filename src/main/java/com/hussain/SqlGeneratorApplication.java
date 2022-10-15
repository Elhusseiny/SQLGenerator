package com.hussain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

@SpringBootApplication
public class SqlGeneratorApplication implements CommandLineRunner {

    @Value("${query}")
    String query;
    String jsonString;
    ObjectMapper mapper = new ObjectMapper();


    public static void main(String[] args) {
        SpringApplication.run(SqlGeneratorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        File inputJson = new ClassPathResource("permissions.json").getFile();
        jsonString = new String(Files.readAllBytes(inputJson.toPath()));
        List<PermissionDTO> permissionDTOList = mapper.readValue(jsonString, new TypeReference<List<PermissionDTO>>() {
        });
        int id = 0;
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("final_insert.sql"), "utf-8"))) {
            for (PermissionDTO rolePermission : permissionDTOList) {
                for (PermissionDTO.Resource resourcePermission : rolePermission.getResources()) {
                    writer.write(query.replace("#id", String.valueOf(++id)).replace("#groupName", "'" + rolePermission.getRole().toLowerCase() + "'").replace("#permissionName", "'" + resourcePermission.getResource() + "'").replace("#permission", "'" + resourcePermission.getPermission() + "'") + " on conflict do nothing;");
                    writer.write("\n");

                }
                writer.write("\n");
            }
        }


    }

}
