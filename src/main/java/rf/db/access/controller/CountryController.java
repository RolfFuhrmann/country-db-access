package rf.db.access.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rf.db.access.busines.CountryService;
import rf.db.access.model.Country;

@Slf4j
@RestController
@CrossOrigin(origins = "${cross-origin.origins}")
@RequestMapping("/country")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private PushController pushController;

    @GetMapping(path = "/countries", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<Country> getAllCountries() {
        return countryService.findAll();
    }

    @GetMapping(path = "/name/{name}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Country> getCountriesByName(@PathVariable("name") String name) {
        return countryService.findByNameContaining(name);
    }

    @GetMapping(path = "/continent/{continent}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<Country> getCountriesByContinent(@PathVariable("continent") String continent) {
        return countryService.findAllByContinent(continent);
    }

    @PostMapping(path = "/add", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Country> createCountry(@RequestBody Country country) {
        log.info("{}.createCountry {}", CountryController.class.getName(), country.toString());
        return countryService.save(country).doOnNext(countrySaved -> pushController.triggerPush(countrySaved));
    }

    @PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Country> updateCountry(@RequestBody Country country) {
        return countryService.update(country);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteCountry(@PathVariable("id") Integer id) {
        log.info("{}.deleteCountry {}", CountryController.class.getName(), id);
        return countryService.deleteCountryById(id);
    }

    @GetMapping(path = "/countries/delay", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<Country> test() {
        return countryService.findAll()
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(Flux::just);
    }
}