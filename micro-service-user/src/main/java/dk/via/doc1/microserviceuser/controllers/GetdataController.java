package dk.via.doc1.microserviceuser.controllers;

import dk.via.doc1.microserviceuser.model.Entry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.StringWriter;

@RestController
public class GetdataController {
    @RequestMapping("/getdata")
    public String getData() {
        StringWriter result = new StringWriter();
        result.write("<h1>Micro service demo</h1>");

        // When started via Docker Compose, the container running the
        // micro service will be assigned the "host name" microservice.
        // We can't use "localhost" because that references the container
        // itself.
        final String serviceBaseUrl = "http://microservice:8080/entries/v1";

        RestTemplate restTemplate = new RestTemplate();

        // First get number of entries
        Long count = restTemplate.getForObject(serviceBaseUrl + "/count", Long.class);
        if (count == null)
            count = (long) -1;
        result.write("<h2>Number of entries</h2>" + String.valueOf(count.longValue()) + "");

        // Then try to fetch data from a few entries
        int hits = 0;
        result.write("<h2>The data for the first 10 entries </h2>");
        for (int i = 1; i <= 1000 && hits < 10 && hits < count; i++) {
            Entry entry = restTemplate.getForObject(serviceBaseUrl + "/entry/" + String.valueOf(i), Entry.class);
            if (entry != null && entry.getId() != 0) {
                result.write("Entry no. " + String.valueOf(i) +
                        "= {\"id\":" + String.valueOf(entry.getId()) + ",\"data\":\"" + entry.getData() + "\"}<br>");
                hits += 1;
            }
        }

        return result.toString();
    }
}
