package com.hussain.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hussain.DTO.PermissionDTO;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class ReadFromJson extends WriterService {

    String jsonString;
    ObjectMapper mapper = new ObjectMapper();

    public void readFromJson() throws IOException {
        File inputJson = new ClassPathResource("permissions_from_fe.json").getFile();
        jsonString = new String(Files.readAllBytes(inputJson.toPath()));
        List<PermissionDTO> permissionDTOList = mapper.readValue(jsonString, new TypeReference<List<PermissionDTO>>() {
        });
        writeToFile(permissionDTOList);

    }
}
