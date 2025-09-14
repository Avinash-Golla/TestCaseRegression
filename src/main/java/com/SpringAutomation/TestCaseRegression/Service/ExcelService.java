package com.SpringAutomation.TestCaseRegression.Service;


import com.SpringAutomation.TestCaseRegression.Model.DefectRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {

    public List<DefectRecord> readExcelFromResources(String fileName) throws FileNotFoundException {
        List<DefectRecord> records= new ArrayList<>();
        try(InputStream is=new ClassPathResource(fileName).getInputStream();
            Workbook workbook = new XSSFWorkbook(is))
        {
            Sheet sheet= workbook.getSheetAt(0);
            for(int i=1;i<=sheet.getLastRowNum();i++)
            {
                Row row=sheet.getRow(i);
                if(row==null) continue;

                String testCaseId =row.getCell(0).getStringCellValue();
                String defectDesc =row.getCell(1).getStringCellValue();
                //String defectId =row.getCell(2) != null ? row.getCell(2).getStringCellValue(): null;
                //records.add(new DefectRecord(testCaseId,defectDesc,defectId));
                records.add(new DefectRecord(testCaseId,defectDesc));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;

    }
}
