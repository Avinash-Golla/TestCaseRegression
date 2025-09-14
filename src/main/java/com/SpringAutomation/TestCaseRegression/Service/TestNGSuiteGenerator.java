package com.SpringAutomation.TestCaseRegression.Service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class TestNGSuiteGenerator {

    public Path generateSuite(List<String> testCaseFiles) throws IOException
    {
        Path downloadDir= Paths.get("downloaded-scripts");
        if(!Files.exists(downloadDir))
        {
            Files.createDirectories(downloadDir);
        }

        Path suitePath =downloadDir.resolve("testng.xml");

        StringBuilder xml=new StringBuilder();
        xml.append("<!DOCTYPE suite SYSTEM \"https://testng.org/testng-1.0dtd\">\n");
        xml.append("<suite name=\"RegressionSuite\">\n");
        xml.append("   <test name=\"AffectedTests\">\n");
        xml.append("       <classes>\n");
        for(String file:testCaseFiles)
        {
            String className=file.replace("src/test/java/","")
                    .replace(".java","")
                    .replace("/",".");
            xml.append("          <class name=\"").append(className).append("\"/>\n");
        }

        xml.append("       </classes>\n");
        xml.append("   </test>\n");
        xml.append("</suite>\n");


        Files.writeString(suitePath,xml.toString(), StandardCharsets.UTF_8);
        return suitePath;


    }
}
