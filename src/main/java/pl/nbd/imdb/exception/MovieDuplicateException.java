package pl.nbd.imdb.exception;

public class MovieDuplicateException extends RuntimeException
{
    public MovieDuplicateException(String id)
    {
        super("Duplicate of movie: " + id);
    }
}
