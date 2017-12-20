package com.lyashenkogs.searchengine.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashSet;
import java.util.Set;

@Service
public class DocumentService {

    @Autowired
    @Qualifier("documents")
    private Jedis documents;

    @Autowired
    @Qualifier("invertedIndex")
    private Jedis invertedIndex;

    public void add(Document document) {
        documents.set(document.getKey(), document.getDocument());
        //for each new token create an index
        String[] tokens = document.getDocument().split(" ");
        for (String token : tokens) {
            invertedIndex.sadd(token, document.getKey());
        }
    }

    public Document get(String key) {
        return new Document(key, documents.get(key));
    }

    public Set<String> getInvertedIndex(String stringWithTokens) {
        String[] tokens = stringWithTokens.split(" ");
        Set<String> keys = new HashSet<>();
        for (String token : tokens) {
            keys.addAll(invertedIndex.smembers(token));
        }
        return keys;
    }

}
