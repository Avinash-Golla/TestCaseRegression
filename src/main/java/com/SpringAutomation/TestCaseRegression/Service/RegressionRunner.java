package com.SpringAutomation.TestCaseRegression.Service;


import org.springframework.stereotype.Service;
import org.testng.TestNG;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.nio.file.Path;
import java.util.List;


@Service
public class RegressionRunner {
    
    public void compileJavaFiles(String sourceDir) throws Exception
    {
        JavaCompiler compiler= ToolProvider.getSystemJavaCompiler();
        if(compiler==null)
             throw new IllegalStateException("NO java compiler  available");
        File folder=new File(sourceDir);
        compileRecursively(compiler,folder);
        
    }
    private void compileRecursively(JavaCompiler compiler, File folder) throws Exception
    {
        if(!folder.exists())
        {
            throw new IllegalArgumentException("Source does not exist");
        }
        File[] files=folder.listFiles();
        if(files==null) return;
        for(File file:files)
        {
            if(file.isDirectory())
            {
                compileRecursively(compiler,file);
            }
            else if(file.getName().endsWith(".java"))
            {
                int result =compiler.run(null, null, null, file.getPath());
                if(result !=0)
                {
                    throw new RuntimeException("Compilation FAILED for "+file.getName());
                }
                else
                    System.out.println("compiled: "+file.getPath());
            }
        }
    }

    public static void runSuite(Path suitePath) {
        TestNG testng = new TestNG();
        testng.setTestSuites(List.of(suitePath.toAbsolutePath().toString()));
        testng.setOutputDirectory("downloaded-scripts/test-outputs");
        testng.run();
    }
}



