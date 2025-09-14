package com.SpringAutomation.TestCaseRegression.Controller;

import com.SpringAutomation.TestCaseRegression.Client.LLMClient;
import com.SpringAutomation.TestCaseRegression.Model.DefectRecord;
import com.SpringAutomation.TestCaseRegression.Service.ExcelService;
import com.SpringAutomation.TestCaseRegression.Service.LLMService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/defects")
public class DefectController {


    private final ExcelService excelService;
    private final LLMService llmService;

    public DefectController(ExcelService excelService, LLMService llmService) {
        this.excelService = excelService;
        this.llmService = llmService;
    }

    @GetMapping("/analyze")
    public void analyzeDefect(@RequestParam String description, @RequestParam String filePath) throws FileNotFoundException {
        List<DefectRecord> records =excelService.readExcelFromResources(filePath);
        for(DefectRecord r: records)
            System.out.println(r.getTestCaseId()+" "+r.getDefectDescription());
        //return llmService
        String  prompt= llmService.getAffectedTestCases(description, records);
        System.out.println("Calling LLM CLIENT...");

        String llmResponse = LLMClient.queryLLM(prompt);
        System.out.println("This is the LLM response");
        System.out.println(llmResponse);
        return;

    }



}
