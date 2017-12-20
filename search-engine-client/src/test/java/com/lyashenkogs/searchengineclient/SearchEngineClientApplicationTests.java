package com.lyashenkogs.searchengineclient;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.lyashenkogs.searchengineclient.document.DocumentQueryService;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SearchEngineClientApplicationTests {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Autowired
    private DocumentQueryService documentQueryService;

    private WireMockServer wireMockServer;

    @Before
    public void setUp() {
        wireMockServer = new WireMockServer(8085);
        wireMockServer.stubFor(post(urlPathMatching("/document"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", APPLICATION_JSON_UTF8_VALUE)));
        wireMockServer.stubFor(get(urlPathMatching("/document/a"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", APPLICATION_JSON_UTF8_VALUE))
                .willReturn(aResponse().withHeader("Content-Type", APPLICATION_JSON_UTF8_VALUE)
                        .withBody("{\"key\":\"a\",\"document\": \"aa awesome document\"}")));
        wireMockServer.stubFor(get(urlPathMatching("/document/reverseIndex"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", APPLICATION_JSON_UTF8_VALUE)
                        .withBody("[\"a\"]")));

        wireMockServer.start();
    }

    @After
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void workflowTest() {
        documentQueryService.addDocument("a:aa awesome document");
        assertTrue(systemOutRule.getLog().contains("success"));
        systemOutRule.clearLog();
        documentQueryService.getDocumentByKey("a");
        assertTrue(systemOutRule.getLog().contains("Document{key='a', document='aa awesome document'}"));
        systemOutRule.clearLog();
        documentQueryService.getInvertedIndex("awesome document");
        assertEquals("[a]", systemOutRule.getLog().trim());
    }

}
