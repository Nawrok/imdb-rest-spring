package pl.nbd.imdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.nbd.imdb.exception.MovieNotFoundException;
import pl.nbd.imdb.model.MovieDto;
import pl.nbd.imdb.service.MovieService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MovieController
{
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService)
    {
        this.movieService = movieService;
    }

    @PostMapping("/movie/add")
    public ResponseEntity<MovieDto> addMovie(@RequestBody @Valid MovieDto movieDto)
    {
        Optional<MovieDto> optional = movieService.addMovie(movieDto);
        return optional.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/movie/{imdbId}")
    public MovieDto getMovieByImdbId(@PathVariable String imdbId)
    {
        return movieService.findMovieByImdbId(imdbId).orElseThrow(() -> new MovieNotFoundException(imdbId));
    }

    @GetMapping("/movies/all")
    public List<MovieDto> getAllMovies()
    {
        return movieService.getAll(Pageable.unpaged()).getContent();
    }

    @GetMapping("/movies")
    public List<MovieDto> getAllMovies(Pageable pageable)
    {
        return movieService.getAll(pageable).getContent();
    }

    @GetMapping("/movies/rating")
    public List<MovieDto> getByAverageRatingBetween(@RequestParam(defaultValue = "0") double min, @RequestParam(defaultValue = "10") double max, Pageable pageable)
    {
        return movieService.findByAverageRatingBetween(min, max, pageable).getContent();
    }

    @GetMapping("/movies/year/{year}")
    public List<MovieDto> getMovieByStartYear(@PathVariable int year, Pageable pageable)
    {
        return movieService.findByStartYear(year, pageable).getContent();
    }

    @PutMapping("/movie/update")
    public ResponseEntity<MovieDto> updateMovie(@RequestBody @Valid MovieDto movieDto)
    {
        Optional<MovieDto> optional = movieService.updateMovie(movieDto);
        return optional.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/movie/{imdbId}")
    public ResponseEntity<String> deleteMovie(@PathVariable String imdbId)
    {
        boolean isDeleted = movieService.deleteMovieById(imdbId);
        if (isDeleted)
        {
            return new ResponseEntity<>(imdbId, HttpStatus.OK);
        }
        throw new MovieNotFoundException(imdbId);
    }
}
