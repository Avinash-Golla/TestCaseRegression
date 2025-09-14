package com.SpringAutomation.TestCaseRegression.Config;

import com.google.genai.Client;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Bean
    public Client geminiClient()
    {

        return Client.builder().apiKey(apiKey).build();
    }
}
