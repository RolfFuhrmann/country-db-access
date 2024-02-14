package rf.db.access.repository;

import java.util.List;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rf.db.access.model.Country;

@Repository
public interface CountryRepository extends R2dbcRepository<Country, Integer> {

  Mono<Country> findByNameContaining(String name);

  Flux<Country> findDistinctByContinentIn(List<String> continents);
}
