package pl.nbd.imdb.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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

    public Optional<MovieDto> addMovie(MovieDto movieDto)
    {
        Optional<Movie> optional = movieRepository.findByImdbId(movieDto.getImdbId());
        if (optional.isPresent())
        {
            return Optional.empty();
        }
        Movie movie = movieRepository.save(movieMapper.mapToMovie(movieDto));
        return Optional.of(movieMapper.mapToMovieDto(movie));
    }

    public Optional<MovieDto> findByImdbId(String imdbId)
    {
        return movieRepository.findByImdbId(imdbId).map(movieMapper::mapToMovieDto);
    }

    public List<MovieDto> findByAverageRatingBetween(double minRating, double maxRating, Pageable pageable)
    {
        return movieRepository.findByAverageRatingBetween(minRating, maxRating, pageable).stream().map(movieMapper::mapToMovieDto).collect(Collectors.toList());
    }

    public List<MovieDto> findByStartYear(int startYear, Pageable pageable)
    {
        return movieRepository.findByStartYear(startYear, pageable).stream().map(movieMapper::mapToMovieDto).collect(Collectors.toList());
    }

    public List<MovieDto> getAll(Pageable pageable)
    {
        return movieRepository.findAllBy(pageable).stream().map(movieMapper::mapToMovieDto).collect(Collectors.toList());
    }

    public Optional<MovieDto> updateMovie(MovieDto movieDto)
    {
        Optional<Movie> optional = movieRepository.findByImdbId(movieDto.getImdbId());
        if (optional.isPresent())
        {
            Movie movie = movieRepository.save(movieMapper.mapToMovie(movieDto));
            return Optional.of(movieMapper.mapToMovieDto(movie));
        }
        return Optional.empty();
    }

    public boolean deleteMovieById(String imdbId)
    {
        Optional<Movie> optional = movieRepository.findByImdbId(imdbId);
        if (optional.isPresent())
        {
            Movie movie = optional.get();
            movieRepository.delete(movie);
            return true;
        }
        return false;
    }


}
