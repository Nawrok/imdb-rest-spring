package pl.nbd.imdb.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.nbd.imdb.exception.MovieDuplicateException;
import pl.nbd.imdb.exception.MovieNotFoundException;
import pl.nbd.imdb.mapper.MovieMapper;
import pl.nbd.imdb.model.Movie;
import pl.nbd.imdb.model.MovieDto;
import pl.nbd.imdb.repository.MovieRepository;

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

    public MovieDto addMovie(MovieDto movieDto)
    {
        Optional<Movie> optional = movieRepository.findByImdbId(movieDto.getImdbId());
        if (optional.isPresent())
        {
            throw new MovieDuplicateException(movieDto.getImdbId());
        }
        Movie movie = movieRepository.save(movieMapper.mapToMovie(movieDto));
        return movieMapper.mapToMovieDto(movie);
    }

    public Optional<MovieDto> findByImdbId(String imdbId)
    {
        return movieRepository.findByImdbId(imdbId).map(movieMapper::mapToMovieDto);
    }

    public List<MovieDto> findByStartYear(int startYear, Pageable pageable)
    {
        return movieRepository.findByStartYear(startYear, pageable).stream().map(movieMapper::mapToMovieDto).collect(Collectors.toList());
    }

    public List<MovieDto> getAll(Pageable pageable)
    {
        return movieRepository.findAllBy(pageable).stream().map(movieMapper::mapToMovieDto).collect(Collectors.toList());
    }

    public MovieDto updateMovie(String imdbId, MovieDto movieDto)
    {
        Optional<Movie> optional = movieRepository.findByImdbId(imdbId);
        if (optional.isPresent())
        {
            movieDto.setImdbId(imdbId);
            try
            {
                Movie movie = movieRepository.save(movieMapper.mapToMovie(movieDto));
                return movieMapper.mapToMovieDto(movie);
            }
            catch (DuplicateKeyException ex)
            {
                throw new MovieDuplicateException(imdbId);
            }
        }
        throw new MovieNotFoundException(imdbId);
    }

    public void deleteMovie(String imdbId)
    {
        Optional<Movie> optional = movieRepository.findByImdbId(imdbId);
        if (optional.isPresent())
        {
            Movie movie = optional.get();
            movieRepository.delete(movie);
            return;
        }
        throw new MovieNotFoundException(imdbId);
    }
}
