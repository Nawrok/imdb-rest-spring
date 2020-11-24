package pl.nbd.imdb.exception;

import com.mongodb.MongoServerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MongoServerAdvice
{
    @ResponseBody
    @ExceptionHandler(MongoServerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handler(MongoServerException exception)
    {
        return exception.getMessage();
    }
}
