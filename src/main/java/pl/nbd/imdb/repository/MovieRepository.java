package pl.nbd.imdb.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.nbd.imdb.model.Movie;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String>
{
    Optional<Movie> findByImdbId(String imdbId);

    List<Movie> findAllBy(Pageable pageable);

    List<Movie> findByStartYear(Integer startYear, Pageable pageable);
}
