package com.lyashenkogs.searchengine.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class DocumentService {

    @Autowired
    private Jedis jedis;

    public void add(Document document) {
        jedis.set(document.getKey(), document.getDocument());
    }

    public Document get(String key) {
        return new Document(key, jedis.get(key));
    }
}
