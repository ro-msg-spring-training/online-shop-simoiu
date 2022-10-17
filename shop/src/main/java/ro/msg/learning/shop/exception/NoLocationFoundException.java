package ro.msg.learning.shop.exception;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@StandardException
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoLocationFoundException extends RuntimeException {
    public NoLocationFoundException() {
        super("No pick-up locations were found from which the products can be taken");
    }
}
