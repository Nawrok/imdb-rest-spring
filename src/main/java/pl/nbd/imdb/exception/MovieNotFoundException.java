package pl.nbd.imdb.exception;

public class MovieNotFoundException extends RuntimeException
{
    public MovieNotFoundException(String imdbId)
    {
        super("Could not find movie: " + imdbId);
    }
}
