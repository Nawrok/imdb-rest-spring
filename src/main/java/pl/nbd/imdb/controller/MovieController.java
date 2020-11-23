package pl.nbd.imdb.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.nbd.imdb.exception.MovieNotFoundException;
import pl.nbd.imdb.model.MovieDto;
import pl.nbd.imdb.service.MovieService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController
{
    private final MovieService movieService;

    public MovieController(MovieService movieService)
    {
        this.movieService = movieService;
    }

    @PostMapping("/movie")
    public MovieDto addMovie(@RequestBody @Valid MovieDto movieDto)
    {
        return movieService.addMovie(movieDto);
    }

    @GetMapping("/movie/{imdbId}")
    public MovieDto getMovieByImdbId(@PathVariable String imdbId)
    {
        return movieService.findByImdbId(imdbId).orElseThrow(() -> new MovieNotFoundException(imdbId));
    }

    @GetMapping("/movies")
    public List<MovieDto> getAllMovies(Pageable pageable)
    {
        return movieService.getAll(pageable);
    }

    @GetMapping("/movies/year/{year}")
    public List<MovieDto> getMoviesByStartYear(@PathVariable int year, Pageable pageable)
    {
        return movieService.findByStartYear(year, pageable);
    }

    @PutMapping("/movie/{imdbId}")
    public MovieDto updateMovie(@PathVariable String imdbId, @RequestBody @Valid MovieDto movieDto)
    {
        return movieService.updateMovie(imdbId, movieDto);
    }

    @DeleteMapping("/movie/{imdbId}")
    public ResponseEntity<String> deleteMovie(@PathVariable String imdbId)
    {
        movieService.deleteMovie(imdbId);
        return new ResponseEntity<>("Deleted movie: " + imdbId, HttpStatus.OK);
    }
}
