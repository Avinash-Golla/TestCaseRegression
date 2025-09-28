package com.SpringAutomation.TestCaseRegression.Service;


import org.springframework.stereotype.Service;
import org.testng.TestNG;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;


@Service
public class RegressionRunner {
    

    public static void runSuite(String suitePath) {
        System.out.println("Running TestNG suite at: " + suitePath);
        File suiteFile = new File(suitePath);
        System.out.println("Suite file exists: " + suiteFile.exists());
        TestNG testng = new TestNG();
        testng.setTestSuites(Collections.singletonList(suitePath));
        testng.run();
    }
}



