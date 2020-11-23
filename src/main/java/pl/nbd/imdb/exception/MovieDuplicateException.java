package pl.nbd.imdb.exception;

public class MovieDuplicateException extends RuntimeException
{
    public MovieDuplicateException(String imdbId)
    {
        super("Duplicate of movie: " + imdbId);
    }
}
