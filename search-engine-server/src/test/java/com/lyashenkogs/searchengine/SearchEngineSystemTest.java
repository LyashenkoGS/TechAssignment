package com.lyashenkogs.searchengine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import redis.clients.jedis.Jedis;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SearchEngineSystemTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    @Qualifier("documents")
    private Jedis jedis;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @After
    public void tearDown() {
        //clean up Redis
        jedis.flushAll();
    }

    @Test
    public void documentAdd_Get() throws Exception {
        //Given documents
        String documentJSON = "{\"key\":\"someKey\",\"document\": \"SomeDocument\"}";
        mockMvc.perform(post("/document")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(documentJSON))
                .andExpect(status().isCreated());
        //Then retrieve the one by a key
        mockMvc.perform(get("/document?key=someKey")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(documentJSON));
    }

    @Test
    public void invertedIndex() throws Exception {
        //Given multiple documents
        Set<String> documentsSet = new HashSet<>();
        documentsSet.add("{\"key\":\"someKey1\",\"document\": \"word1 word2 word3 word4\"}");
        documentsSet.add("{\"key\":\"someKey2\",\"document\": \"word1 word3\"}");
        documentsSet.add("{\"key\":\"someKey3\",\"document\": \"word4 word4\"}");
        documentsSet.add("{\"key\":\"someKey4\",\"document\": \"word5\"}");
        for (String documentJson : documentsSet) {
            mockMvc.perform(post("/document")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(documentJson))
                    .andExpect(status().isCreated());
        }
        //Then get all keys where word1 and word2 occurs
        String reverseIndex = mockMvc.perform(get("/document/reverseIndex?tokens=word1 word2")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        assertTrue(reverseIndex.contains("someKey1"));
        assertTrue(reverseIndex.contains("someKey2"));
    }
}
