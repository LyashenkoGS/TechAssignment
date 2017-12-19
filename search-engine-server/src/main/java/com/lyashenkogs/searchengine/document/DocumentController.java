package com.lyashenkogs.searchengine.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping
    ResponseEntity addDocument(@RequestBody Document document) {
        documentService.add(document);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping
    Document getDocument(String key) {
        return documentService.get(key);
    }
}
