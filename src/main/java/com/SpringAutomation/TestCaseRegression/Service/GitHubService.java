package com.SpringAutomation.TestCaseRegression.Service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Map;

@Service
public class GitHubService {

   @Value("${github.token}")
    private String githubToken;

    @Value("${github.repo.owner}")
    private String repoOwner;

    @Value("${github.repo.name}")
    private String repoName;

    private static final String GITHUB_API_URL ="https://api.github.com/repos";

    public String getFileContent(String filePath)
    {
        String url= String.format("%s/%s/%s/contents/%s", GITHUB_API_URL, repoOwner, repoName,filePath);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers =new HttpHeaders();
        headers.setBearerAuth(githubToken);
        HttpEntity<Void> entity= new HttpEntity<>(headers);

        ResponseEntity<Map> response= restTemplate.exchange(url, HttpMethod.GET,entity,Map.class);

        if(response.getStatusCode() == HttpStatus.OK)
        {
            String base64Content =(String) response.getBody().get("content");
            base64Content=base64Content.replaceAll("\\s", "");
            return new String(Base64.getDecoder().decode(base64Content),StandardCharsets.UTF_8);

        }


        throw new RuntimeException("Failed to fetch file: "+response.getStatusCode());

    }
    public Path saveFile(String relativepath , String content) throws IOException
    {
        Path downloadDir= Paths.get(relativepath);
        if(downloadDir.getParent()!=null)
        {
            Files.createDirectories(downloadDir.getParent());
        }


        return Files.writeString(downloadDir,content, StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
    }




}
