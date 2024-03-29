package pl.nbd.imdb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MovieDuplicateAdvice
{
    @ResponseBody
    @ExceptionHandler(MovieDuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handler(MovieDuplicateException exception)
    {
        return exception.getMessage();
    }
}
