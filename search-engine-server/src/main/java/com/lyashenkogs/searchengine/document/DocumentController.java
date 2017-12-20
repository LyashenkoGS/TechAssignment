package com.lyashenkogs.searchengine.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/document")
    ResponseEntity addDocument(@RequestBody Document document) {
        documentService.add(document);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/document/{key}")
    Document getDocument(@PathVariable String key) {
        return documentService.get(key);
    }

    @GetMapping("/document/reverseIndex")
    Set<String> reverseIndex(@RequestParam("tokens") String stringWithTokens) {
        return documentService.getInvertedIndex(stringWithTokens);
    }

}
