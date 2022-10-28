package com.hussain.service;

import com.hussain.DTO.PermissionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class WriterService {

    @Value("${query}")
    String query;

    protected void writeToFile(List<PermissionDTO> permissionDTOList) throws IOException {
        int id = 0;
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("permissions.sql"), "utf-8"))) {
            for (PermissionDTO rolePermission : permissionDTOList) {
                for (PermissionDTO.Resource resourcePermission : rolePermission.getResources()) {
                    writer.write(query.replace("#id", String.valueOf(++id)).replace("#groupName", "'" + rolePermission.getRole().toLowerCase() + "'").replace("#permissionName", "'" + resourcePermission.getResource() + "'").replace("#permission", "'" + resourcePermission.getPermission() + "'") + " on conflict do nothing;");

                }
                writer.write("\n");
            }
        }
    }

}
