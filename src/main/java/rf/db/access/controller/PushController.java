package rf.db.access.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import rf.db.access.model.Country;

@RestController
public class PushController {

    @Value("${push-service.url}")
    private String pushServiceUrl;

    private final RestTemplate restTemplate;

    public PushController() {
        this.restTemplate = new RestTemplate();
    }

    @PutMapping("/trigger/push")
    public ResponseEntity<Country> triggerPush(@RequestBody Country country) {
        restTemplate.postForObject(pushServiceUrl, country, Country.class);
        return ResponseEntity.ok().body(country);
    }
}
