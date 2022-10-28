package com.hussain.service;

import com.hussain.DTO.PermissionDTO;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReadFromExcel extends WriterService {

    public void readFromExcel() throws IOException, InvalidFormatException {

        File inputXlXS = new ClassPathResource("permissions_from_confluent.xlsx").getFile();
        Workbook workbook = new XSSFWorkbook(inputXlXS); // todo: make this try-with-resource
        Sheet sheet = workbook.getSheetAt(0);
        int headerRow = 0;
        int headerCell = 0;
        List<PermissionDTO> permissions = new ArrayList<>();
        String permissionString ;
        for (int row = 1; row < sheet.getPhysicalNumberOfRows(); row++) {
            for (int cell = 1; cell < sheet.getRow(row).getLastCellNum(); cell++) {
                PermissionDTO permissionDTO = new PermissionDTO()
                        .setRole(sheet.getRow(headerRow).getCell(cell).getStringCellValue().toLowerCase().trim());
                PermissionDTO.Resource resource = new PermissionDTO.Resource().setResource((sheet.getRow(row).getCell(headerCell)).getStringCellValue());
                permissionString = (sheet.getRow(row).getCell(cell)).getStringCellValue().toUpperCase().trim();
                resource.setPermission(permissionString.equals("NO") ? "DENY" : permissionString);
                permissionDTO.setResources(List.of(resource));
                if (resource.getResource().isEmpty() || resource.getPermission().isEmpty())
                    continue;  // not added yet on confluent ui elements.
                permissions.add(permissionDTO);
            }
        }

        writeToFile(permissions);
    }
}

