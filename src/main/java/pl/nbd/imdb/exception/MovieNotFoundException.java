package pl.nbd.imdb.exception;

public class MovieNotFoundException extends RuntimeException
{
    public MovieNotFoundException(String id)
    {
        super("Could not find movie: " + id);
    }
}
