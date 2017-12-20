package com.lyashenkogs.searchengineclient;

import com.lyashenkogs.searchengineclient.document.DocumentQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

@SpringBootApplication
public class SearchEngineClientApplication {

    private static Scanner scanner = new Scanner(in);

    @Autowired
    private DocumentQueryService documentQueryService;


    public static void main(String[] args) {
        SpringApplication.run(SearchEngineClientApplication.class, args);
    }


    @ConditionalOnProperty(value = "spring.profiles.active", havingValue = "prod")
    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) {
        return args -> {
            while (true) {
                out.println("Select one of the available actions (1-3): ");
                out.println("1. add document");
                out.println("2. get document by a key");
                out.println("3. get all documents keys where given token occurs");
                int action = scanner.nextInt();
                if (action == 1) {
                    out.println("specify document key and value in format key:value. Example: someKey:my awesome document");
                    if (scanner.hasNext()) {
                        scanner.nextLine();//technical depth to workaround whitespaces
                        String input = scanner.nextLine();
                        documentQueryService.addDocument(input);
                    }
                } else if (action == 2) {
                    out.println("specify document key. Example: a");
                    if (scanner.hasNext()) {
                        scanner.nextLine();//technical depth to workaround whitespaces
                        documentQueryService.getDocumentByKey(scanner.nextLine());
                    }
                } else if (action == 3) {
                    out.println("specify tokens separated by whitespaces to get documents keys. Example: my awesome");
                    if (scanner.hasNext()) {
                        scanner.nextLine();//technical depth to workaround whitespaces
                        documentQueryService.getInvertedIndex(scanner.nextLine());
                    }

                }

            }

        };
    }


}
