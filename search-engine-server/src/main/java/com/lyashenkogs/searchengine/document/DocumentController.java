package com.lyashenkogs.searchengine.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.Set;

@RestController("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    @Qualifier("invertedIndex")
    private Jedis invertedIndex;

    @Autowired
    @Qualifier("documents")
    private Jedis forDocuments;

    @PostMapping
    ResponseEntity addDocument(@RequestBody Document document) {
        documentService.add(document);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping
    Document getDocument(String key) {
        return documentService.get(key);
    }

    @GetMapping("document/reverseIndex")
    Set<String> reverseIndex(@RequestParam("tokens") String stringWithTokens) {
        return documentService.getInvertedIndex(stringWithTokens);
    }

}
