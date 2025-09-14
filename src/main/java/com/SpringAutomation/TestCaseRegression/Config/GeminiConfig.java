package com.SpringAutomation.TestCaseRegression.Config;

import com.google.genai.Client;
import org.checkerframework.checker.units.qual.C;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {

    @Bean
    public Client geminiClient()
    {
        return new Client();
    }
}
