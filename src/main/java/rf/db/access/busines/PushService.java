package rf.db.access.busines;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import rf.db.access.model.Country;

@Service
public class PushService {

    @Value("${push-service.url}")
    private String pushServiceUrl;

    private final RestTemplate restTemplate;

    public PushService() {
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<Country> triggerPush(Country country) {
        Objects.requireNonNull(pushServiceUrl);
        restTemplate.postForObject(pushServiceUrl, country, Country.class);
        return ResponseEntity.ok().body(country);
    }
}
