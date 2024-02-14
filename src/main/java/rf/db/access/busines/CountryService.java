package rf.db.access.busines;

import java.util.Arrays;
import java.util.Objects;
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

        @Autowired
        private PushService pushService;

        public Flux<Country> findAll() {
                return countryRepository.findAll()
                                .doOnError(error -> log.error("Error getting all countries", error))
                                .log();
        }

        public Mono<Country> findByNameContaining(String name) {
                return countryRepository.findByNameContaining(name)
                                .doOnError(error -> log.error("Error getting country by name {}", name, error))
                                .log();
        }

        public Flux<Country> findAllByContinent(String continent) {
                return countryRepository.findDistinctByContinentIn(Arrays.asList(continent))
                                .doOnError(error -> log.error("Error getting country by continent {}", continent,
                                                error))
                                .log();
        }

        public Mono<Country> save(Country country) {
                log.info("{} save {}", CountryRepository.class.getName(), country.toString());
                return countryRepository.save(country)
                                .doOnSuccess(succress -> pushService.triggerPush(country))
                                .doOnError(error -> log.error("Error save country with id {}", country,
                                                error))
                                .log();
        }

        public Mono<Country> update(Country country) {
                Integer id = Objects.requireNonNull(country.getId());
                return countryRepository.findById(id).map(Optional::of).defaultIfEmpty(Optional.empty())
                                .flatMap(optionalCountry -> {
                                        if (optionalCountry.isPresent()) {
                                                country.setId(id);
                                                return countryRepository.save(country)
                                                                .doOnSuccess(succress -> pushService
                                                                                .triggerPush(country))
                                                                .doOnError(error -> log.error(
                                                                                "Error updating country with id {}", id,
                                                                                error))
                                                                .log();
                                        }
                                        return Mono.empty();
                                });
        }

        public Mono<Void> deleteCountryById(Country country) {
                Integer id = Objects.requireNonNull(country.getId());
                return countryRepository.deleteById(id)
                                .doOnSuccess(success -> pushService.triggerPush(country))
                                .doOnError(error -> log.error("Error deleting country with id {}", id, error))
                                .log();
        }

        public Mono<Void> deleteAll() {
                return countryRepository.deleteAll()
                                .doOnError(error -> log.error("Error deleting all countries", error))
                                .log();
        }

        public Mono<Country> findCountryById(Integer id) {
                return countryRepository.findById(Objects.requireNonNull(id))
                                .doOnError(error -> log.error("Error getting country by id {}", id, error))
                                .log();
        }

}
