package com.SpringAutomation.TestCaseRegression.Service;

import com.SpringAutomation.TestCaseRegression.Client.LLMClient;
import com.SpringAutomation.TestCaseRegression.Model.DefectRecord;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LLMService {

    private final LLMClient llmClient;

    public LLMService(LLMClient llmClient)
    {
        this.llmClient=llmClient;
    }

    public String getAffectedTestCases(String defectDescription, List<DefectRecord> records)
    {
        String context =records.stream()
                .map(r -> r.getTestCaseId()+" -> "+r.getDefectDescription())
                .collect(Collectors.joining("\n"));
        String prompt = "Given the following defect-testcase mapping:\n"
                        +context
                        +"\n\nQuestion: Which test cases are affected  by this defect: \""
                        +defectDescription+"\"?\n\nRespond with testcase IDs only in the form of arrayList ";
        System.out.println("This is the prompt"+ prompt);
        return prompt;
    }
}
