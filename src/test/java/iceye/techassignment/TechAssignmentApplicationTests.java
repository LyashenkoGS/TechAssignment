package iceye.techassignment;

import iceye.techassignment.services.GenerateImageService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TechAssignmentApplicationTests {

    @LocalServerPort
    int port;
    @Autowired
    private GenerateImageService generateImageService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void assignment() {
        given().port(port)
                .basePath("/assignment").get("")
                .then().statusCode(200)
                .body(StringContains.containsString("{\"userId\":1,\"id\":1,\"title\":\"tiredneherper oitpo irutpecxe itaceacco tnedivorp talleper erecaf tua tnus\",\"body\":\"otcetihcra teineve mer tnus metua tse murer murtson\\nmatot sauq tu tu eaitselom tiredneherper\\nmuc te atidepxe rutnuuqesnoc eadnasucer tipicsus\\ntipicsus te aiuq\"},{\"userId\":1,\"id\":2,\"title\":\"esse tse iuq\",\"body\":\"allun isin euqen iuq sumissop sitibed non mairepa iuq\\nsidneicier tu eaitselom lihin lev orrop etatpulov siitidnalb taiguf\\neuqen serolod ae eataeb rolod tiredneherper lihin tnis iuqes\\neativ eropmet murer tse\"}"));
    }


    @Test
    public void ingest() throws IOException, NoSuchAlgorithmException {
        String someInputText = "someInputText";
        String expectedHash = "D41D8CD98F00B204E9800998ECF8427E";
        String expectedURL = "http://" + InetAddress.getLocalHost().getHostName() + ":" + port + "/download_image/" + expectedHash + ".png";
        given()
                .port(port)
                .basePath("/ingest")
                .body(someInputText)
                .post("")
                .then()
                .statusCode(200)
                .body(CoreMatchers.equalTo(expectedURL));
        generateImageService.generateImageFromText(someInputText, "png");
        System.out.println(expectedURL);
        //download a file
        URL website = new URL(expectedURL);
        String tempFileName = "tempFile.png";
        try (InputStream in = website.openStream()) {
            Files.copy(in, Paths.get(tempFileName), StandardCopyOption.REPLACE_EXISTING);
        }
        //So a generated file and downloaded are equal
        byte[] tempFileBytes = Files.readAllBytes(Paths.get(tempFileName));
        byte[] D41D8CD98F00B204E9800998ECF8427E = Files.readAllBytes(Paths.get("D41D8CD98F00B204E9800998ECF8427E.png"));
        Assert.assertArrayEquals(D41D8CD98F00B204E9800998ECF8427E, tempFileBytes);
        //clean up
        Files.delete(Paths.get("D41D8CD98F00B204E9800998ECF8427E.png"));
        Files.delete(Paths.get(tempFileName));
    }


    @Test
    public void generateImage() throws NoSuchAlgorithmException, IOException {
        String expectedHash = "D41D8CD98F00B204E9800998ECF8427E";
        String fileName = generateImageService.generateImageFromText("Hello", "png");
        String generatedHash = fileName.replace(".png", "");
        assertEquals(expectedHash, generatedHash);
        Files.delete(Paths.get(fileName));
    }

}
