package pl.nbd.imdb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.nbd.imdb.model.Movie;
import pl.nbd.imdb.model.MovieDto;

@Mapper(componentModel = "spring")
public interface MovieMapper
{
    @Mapping(target = "id", ignore = true)
    Movie mapToMovie(MovieDto movieDto);

    MovieDto mapToMovieDto(Movie movie);
}
