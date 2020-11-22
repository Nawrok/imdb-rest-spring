package pl.nbd.imdb.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import pl.nbd.imdb.model.Movie;
import pl.nbd.imdb.model.MovieDto;
import pl.nbd.imdb.repository.MovieRepository;

import java.util.Optional;

@Service
public class MovieService
{
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository)
    {
        this.movieRepository = movieRepository;
    }

    public Optional<MovieDto> addMovie(MovieDto movieDto)
    {
        Optional<Movie> optional = movieRepository.findByImdbId(movieDto.getImdbId());
        if (optional.isPresent())
        {
            return Optional.empty();
        }
        Movie movie = movieRepository.save(convertFromDto(movieDto));
        return Optional.of(convertToDto(movie));
    }

    public Optional<MovieDto> findMovieByImdbId(String imdbId)
    {
        return movieRepository.findByImdbId(imdbId).map(this::convertToDto);
    }

    public Slice<MovieDto> findByAverageRatingBetween(double minRating, double maxRating, Pageable pageable)
    {
        return movieRepository.findByAverageRatingBetween(minRating, maxRating, pageable).map(this::convertToDto);
    }

    public Slice<MovieDto> findByStartYear(int startYear, Pageable pageable)
    {
        return movieRepository.findByStartYear(startYear, pageable).map(this::convertToDto);
    }

    public Slice<MovieDto> getAll(Pageable pageable)
    {
        return movieRepository.findAll(pageable).map(this::convertToDto);
    }

    public Optional<MovieDto> updateMovie(MovieDto movieDto)
    {
        Optional<Movie> optional = movieRepository.findByImdbId(movieDto.getImdbId());
        if (optional.isPresent())
        {
            Movie movie = movieRepository.save(convertFromDto(movieDto));
            return Optional.of(convertToDto(movie));
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

    private MovieDto convertToDto(Movie movie)
    {
        MovieDto dto = new MovieDto();
        BeanUtils.copyProperties(movie, dto);
        return dto;
    }

    private Movie convertFromDto(MovieDto dto)
    {
        Movie movie = new Movie();
        BeanUtils.copyProperties(dto, movie);
        return movie;
    }
}
