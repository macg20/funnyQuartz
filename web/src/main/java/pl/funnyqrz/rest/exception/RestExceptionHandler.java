package pl.funnyqrz.rest.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import pl.funnyqrz.exceptions.UserAlreadyRegisterException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {

    Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(value = {UserAlreadyRegisterException.class})
    public ResponseEntity<ErrorMessage> handleUserAlreadyRegister(UserAlreadyRegisterException ex, WebRequest request) {
        logger.error(ex.getMessage());
        return new ResponseEntity<ErrorMessage>(new ErrorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ErrorMessage> handleConstraintViolation(ConstraintViolationException ex) {
        logger.error(ex.getMessage());
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        String message = constraintViolations.stream().map(constraintViolation -> String.format("%s, %s, %s", constraintViolation.getPropertyPath(),
                constraintViolation.getInvalidValue(), constraintViolation.getMessage())).collect(Collectors.joining("\n"));
        return new ResponseEntity<ErrorMessage>(new ErrorMessage(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorMessage> handleMethodArgumentValidation(MethodArgumentNotValidException ex) {
        logger.error(ex.getMessage());
        BindingResult result = ex.getBindingResult();
        String message = result.getFieldErrors().stream()
                .map(fieldError -> String.format("%s", fieldError.getDefaultMessage()))
                .collect(Collectors.joining("\n"));
        return new ResponseEntity<ErrorMessage>(new ErrorMessage(message), HttpStatus.BAD_REQUEST);
    }

}
