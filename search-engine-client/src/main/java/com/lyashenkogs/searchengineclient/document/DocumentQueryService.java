package com.lyashenkogs.searchengineclient.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

import static java.lang.System.out;

@Service
public class DocumentQueryService {

    @Value("${server.url}")
    private String serverUrl;

    @Autowired
    private RestTemplate restTemplate;


    public void addDocument(String input) {
        String[] keyAndValue = input.split(":");
        try {
            restTemplate.postForEntity(serverUrl + "/document", new Document(keyAndValue[0], keyAndValue[1]), Set.class);
            out.println("success");
        } catch (Exception e) {
            out.println("Cant perform request!");
        }
    }

    public void getDocumentByKey(String input) {
        try {
            ResponseEntity<Document> document = restTemplate.getForEntity(serverUrl + "/document/" + input, Document.class);
            out.println(document.getBody());
        } catch (Exception e) {
            out.println("Cant perform request!");
        }
    }

    public void getInvertedIndex(String input) {
        try {
            ResponseEntity<Set> keys = restTemplate.getForEntity(serverUrl + "/document/reverseIndex?tokens=" + input, Set.class);
            out.println(keys.getBody());
        } catch (Exception e) {
            out.println("Cant perform request!");
        }
    }

}



