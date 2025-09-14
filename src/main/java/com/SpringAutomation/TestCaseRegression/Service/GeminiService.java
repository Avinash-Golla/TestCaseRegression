package com.SpringAutomation.TestCaseRegression.Service;


import com.SpringAutomation.TestCaseRegression.Config.GeminiConfig;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    private final Client client;

    public GeminiService(Client client)
    {
    this.client=client;
    }

    public String queryLLM(String prompt)
    {
        Client client = Client.builder().apiKey("AIzaSyAD4Kq1_ExrBWDJEZVydeOvKMVOH6drRGQ").build();

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.5-flash",
                        prompt,
                        null);

        System.out.println(response.text());
        return response.text();
    }
}


