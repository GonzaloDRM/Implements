package com.example.demo.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.models.Objeto;
import com.example.demo.utils.MimeUtils;

@RestController
public class ApachePoiController {

    @PostMapping(value = "/apache")
    public ResponseEntity<?> apacheSave(@RequestPart MultipartFile excel) throws MimeTypeException, IOException {
        String extension = MimeUtils.getExtensionFromMime(excel.getContentType());
        InputStream is = excel.getInputStream();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Workbook workbook;
        Sheet sheet;
        List<Objeto> dtos = new ArrayList<>();
        Map<String, String> response = new HashMap<>();

        if (extension.endsWith(".xls")) {
            workbook = new HSSFWorkbook(is);
        } else if (extension.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(is);
        } else {
            response.put("message", "Invalid file");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        sheet = workbook.getSheetAt(1);

        Iterator<Row> rowIterator = sheet.iterator();
        String[] headers = { "id*", "name*", "age", "activo" };

        if (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell cell = row.getCell(0);
            for (int i = 1; headers.length > i; i++) {
                cell = row.getCell(i);
                if (!headers[i].equalsIgnoreCase(cell.getStringCellValue())) {
                    break;
                }
            }
        }

        Row row;
        
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            Objeto dto = new Objeto();
            
            dto.setName(row.getCell(1).getStringCellValue());
            dto.setAge(row.getCell(2).getNumericCellValue());
            dto.setActivo(row.getCell(3).getBooleanCellValue());
            
            dtos.add(dto);
        }
        
        workbook.write(os);
        workbook.close();

        response.put("message", "Fail to Upload");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
