package pl.nbd.imdb.mapper;

import org.mapstruct.Mapper;
import pl.nbd.imdb.model.Movie;
import pl.nbd.imdb.model.MovieDto;

@Mapper(componentModel = "spring")
public interface MovieMapper
{
    Movie mapToMovie(MovieDto movieDto);

    MovieDto mapToMovieDto(Movie movie);
}
