package pl.nbd.imdb.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.nbd.imdb.exception.MovieDuplicateException;
import pl.nbd.imdb.exception.MovieNotFoundException;
import pl.nbd.imdb.mapper.MovieMapper;
import pl.nbd.imdb.model.Movie;
import pl.nbd.imdb.model.MovieDto;
import pl.nbd.imdb.repository.MovieRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService
{
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper)
    {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    public MovieDto addMovie(MovieDto movieDto)
    {
        movieRepository.findByImdbId(movieDto.getImdbId()).ifPresent(movie ->
        {
            throw new MovieDuplicateException(movieDto.getImdbId());
        });
        Movie movie = movieRepository.save(movieMapper.mapToMovie(movieDto));
        return movieMapper.mapToMovieDto(movie);
    }

    public MovieDto getByImdbId(String imdbId)
    {
        return movieRepository.findByImdbId(imdbId).map(movieMapper::mapToMovieDto).orElseThrow(() -> new MovieNotFoundException(imdbId));
    }

    public List<MovieDto> findByStartYear(int startYear, Pageable pageable)
    {
        return movieRepository.findByStartYear(startYear, pageable).stream().map(movieMapper::mapToMovieDto).collect(Collectors.toList());
    }

    public List<MovieDto> getAll(Pageable pageable)
    {
        return movieRepository.findAllBy(pageable).stream().map(movieMapper::mapToMovieDto).collect(Collectors.toList());
    }

    public MovieDto updateMovie(@Valid MovieDto movieDto)
    {
        Movie movie = movieRepository.findByImdbId(movieDto.getImdbId()).orElseThrow(() -> new MovieNotFoundException(movieDto.getImdbId()));
        movieDto.setId(movie.getId());
        Movie updatedMovie = movieRepository.save(movieMapper.mapToMovie(movieDto));
        return movieMapper.mapToMovieDto(updatedMovie);
    }

    public void deleteMovie(String imdbId)
    {
        Movie movie = movieRepository.findByImdbId(imdbId).orElseThrow(() -> new MovieNotFoundException(imdbId));
        movieRepository.delete(movie);
    }
}
