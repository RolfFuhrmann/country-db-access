package rf.db.access.busines;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rf.db.access.model.Country;
import rf.db.access.repository.CountryRepository;

@Slf4j
@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public Flux<Country> findAll() {
        return countryRepository.findAll();
    }

    public Mono<Country> findByNameContaining(String name) {
        return countryRepository.findByNameContaining(name);
    }

    public Flux<Country> findAllByContinent(String continent) {
        return countryRepository.findDistinctByContinentIn(Arrays.asList(continent));
    }

    public Mono<Country> save(Country country) {
        log.info("{} save {}", CountryRepository.class.getName(), country.toString());
        return countryRepository.save(country);
    }

    public Mono<Country> update(Country country) {
        return countryRepository.findById(country.getId()).map(Optional::of).defaultIfEmpty(Optional.empty())
                .flatMap(optionalTutorial -> {
                    if (optionalTutorial.isPresent()) {
                        country.setId(country.getId());
                        return countryRepository.save(country);
                    }
                    return Mono.empty();
                });
    }

    public Mono<Void> deleteCountryByName(String name) {
        return countryRepository.deleteByName(name);
    }

    public Mono<Void> deleteAll() {
        return countryRepository.deleteAll();
    }
}
