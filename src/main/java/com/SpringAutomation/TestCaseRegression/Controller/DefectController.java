package com.SpringAutomation.TestCaseRegression.Controller;

import com.SpringAutomation.TestCaseRegression.Client.LLMClient;
import com.SpringAutomation.TestCaseRegression.Model.DefectRecord;
import com.SpringAutomation.TestCaseRegression.Service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/defects")
public class DefectController {


    private final ExcelService excelService;
    private final LLMService llmService;
    private final GeminiService geminiService;
    private final GitHubService gitHubService;
    private final TestNGSuiteGenerator suiteGenerator;
    private final RegressionRunner regressionRunner;

    public DefectController(ExcelService excelService, LLMService llmService, GeminiService geminiService, GitHubService gitHubService, TestNGSuiteGenerator suiteGenerator, RegressionRunner regressionRunner) {
        this.excelService = excelService;
        this.llmService = llmService;
        this.geminiService=geminiService;
        this.gitHubService=gitHubService;
        this.suiteGenerator=suiteGenerator;
        this.regressionRunner=regressionRunner;
    }

    @GetMapping("/analyze")
    public void analyzeDefect(@RequestParam String description, @RequestParam String filePath) throws FileNotFoundException, JsonProcessingException {
        List<DefectRecord> records =excelService.readExcelFromResources(filePath);
        //for(DefectRecord r: records)
          //  System.out.println(r.getTestCaseId()+" "+r.getDefectDescription());
        //return llmService
       // String  prompt= llmService.getAffectedTestCases(description, records);
        String prompt="";
        Set<String> testcasesIdsUsing=new HashSet<>();

        //Preparing Prompts :
        int count=0;
        for(DefectRecord d: records)
        {
            if(count!=4)
            {
                count++;
                prompt= d.getTestCaseId()+"-->"+d.getDefectDescription()+"\\n";
            }
            else {
                count=0;
              String promptToSend = "Given the following defect-testcase mapping:\n"
                        +prompt
                        +"\n\nQuestion: Which test cases are affected  by this defect: \""
                        +description+"\"?\n\nRespond with testcase IDs only in the form of arrayList ";
                System.out.println("Calling LLM CLIENT...");
                String llmResponse = geminiService.queryLLM(prompt);

                System.out.println("This is the LLM response");
                System.out.println(llmResponse);
//                List<String> testcasesIdsUsing=new ArrayList<>();
                for(int i=0;i< llmResponse.length();i++)
                {
                    if(llmResponse.charAt(i)=='T'&& llmResponse.charAt(i+1)=='C'&& llmResponse.charAt(i+2)=='0')
                        testcasesIdsUsing.add(llmResponse.substring(i,i+5));
                }
                for(String tc1: testcasesIdsUsing)
                    System.out.println(tc1);
            }
        }


        //System.out.println("Calling LLM CLIENT...");

        //String llmResponse = geminiService.queryLLM(prompt);
//        System.out.println("This is the LLM response");
//        System.out.println(llmResponse);
//        List<String> testcasesIdsUsing=new ArrayList<>();
//        for(int i=0;i< llmResponse.length();i++)
//        {
//            if(llmResponse.charAt(i)=='T')
//                testcasesIdsUsing.add(llmResponse.substring(i,i+5));
//        }
//        for(String tc1: testcasesIdsUsing)
//            System.out.println(tc1);
//        String cleaned=llmResponse.replaceAll("\\[|\\]|\"","");
//        //ObjectMapper objectMapper=new ObjectMapper();
//        List<String> affectedTcs= Arrays.stream(cleaned.split(","))
//                .map(String::trim)
//                .toList();

//        if(affectedTcs.isEmpty()) {
//          System.out.println("No affected Testcases");
//
//        }
        try{

            List<String> fullyQualifiedClasses=new ArrayList<>();
            for(String tc: testcasesIdsUsing)
            {
                String filePathGitHub="TestCases/src/test/java/com/myTests/tests/"+tc+".java";
                String content= gitHubService.getFileContent(filePathGitHub);
                //Path savedPath=gitHubService.saveFile("src/test/java/com/myTests/tests/"+tc+".java",content);
                Path savedPath=gitHubService.saveFile("src/main/java/com/myTests/tests/"+tc+".java",content);

                //gitHubService.saveFile("com/myTests/tests/"+tc+".java",content);
                fullyQualifiedClasses.add("com.myTests.tests."+tc);
            }
           // regressionRunner.compileJavaFiles("downloaded-scripts");
            Path suitePath=suiteGenerator.generateSuite(fullyQualifiedClasses);
            RegressionRunner.runSuite(suitePath.toString());
            System.out.println("Regression completed suite genreated at :" +suitePath);
        } catch (Exception e) {
            e.printStackTrace();
           System.out.println("Error: "+e.getMessage());

        }


    }



}
