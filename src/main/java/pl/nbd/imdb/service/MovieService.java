package pl.nbd.imdb.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.nbd.imdb.exception.MovieDuplicateException;
import pl.nbd.imdb.exception.MovieNotFoundException;
import pl.nbd.imdb.mapper.MovieMapper;
import pl.nbd.imdb.model.MovieDto;
import pl.nbd.imdb.repository.MovieRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
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

    public MovieDto addMovie(@Valid MovieDto movieDto)
    {
        movieRepository.findByImdbId(movieDto.getImdbId()).ifPresent(m ->
        {
            throw new MovieDuplicateException(m.getImdbId());
        });
        return Optional.of(movieDto)
                .map(movieMapper::mapToMovie)
                .map(movieRepository::save)
                .map(movieMapper::mapToMovieDto)
                .orElseThrow();
    }

    public MovieDto getByImdbId(String imdbId)
    {
        return movieRepository.findByImdbId(imdbId)
                .map(movieMapper::mapToMovieDto)
                .orElseThrow(() -> new MovieNotFoundException(imdbId));
    }

    public List<MovieDto> findByStartYear(int startYear, Pageable pageable)
    {
        return movieRepository.findByStartYear(startYear, pageable)
                .stream()
                .map(movieMapper::mapToMovieDto)
                .collect(Collectors.toList());
    }

    public List<MovieDto> getAll(Pageable pageable)
    {
        return movieRepository.findAllBy(pageable)
                .stream()
                .map(movieMapper::mapToMovieDto)
                .collect(Collectors.toList());
    }

    public MovieDto updateMovie(@Valid MovieDto movieDto)
    {
        return movieRepository.findByImdbId(movieDto.getImdbId())
                .map(m ->
                {
                    movieDto.setId(m.getId());
                    return movieDto;
                })
                .map(movieMapper::mapToMovie)
                .map(movieRepository::save)
                .map(movieMapper::mapToMovieDto)
                .orElseThrow(() -> new MovieNotFoundException(movieDto.getImdbId()));
    }

    public void deleteMovie(String imdbId)
    {
        movieRepository.findByImdbId(imdbId)
                .ifPresentOrElse(movieRepository::delete, () ->
                {
                    throw new MovieNotFoundException(imdbId);
                });
    }
}
