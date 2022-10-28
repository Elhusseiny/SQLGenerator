package com.hussain;

import com.hussain.service.ReadFromExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SqlGeneratorApplication implements CommandLineRunner {

    @Autowired
    ReadFromExcel readFromExcelService;

    public static void main(String[] args) {
        SpringApplication.run(SqlGeneratorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        readFromExcelService.readFromExcel();
    }
}
