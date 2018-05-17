package iceye.techassignment.contorollers;

import iceye.techassignment.entity.Post;
import iceye.techassignment.services.GenerateImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    private GenerateImageService generateImageService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(path = "/download_image/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getFile(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(fileName));
    }

    @GetMapping("/assignment")
    public List<Post> greeting() {
        ResponseEntity<Post[]> forEntity = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts", Post[].class);
        List<Post> posts = Arrays.asList(forEntity.getBody());
        for (Post post : posts) {
            post.setTitle(new StringBuilder(post.getTitle()).reverse().toString());
            post.setBody(new StringBuilder(post.getBody()).reverse().toString());
        }
        return posts;
    }

    @PostMapping("/ingest")
    public String ingest(@RequestBody String text, HttpServletRequest request) throws IOException, NoSuchAlgorithmException {
        System.out.println(request.getServerPort());

        return "http://" + InetAddress.getLocalHost().getHostName() + ":" + request.getServerPort() + "/download_image/"
                + generateImageService.generateImageFromText(text, "png");
    }

}
