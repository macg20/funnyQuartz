package pl.funnyqrz.api.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import pl.funnyqrz.exceptions.UserAlreadyRegisterException;
import pl.funnyqrz.messages.GenericMessage;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class UserRegistrationError {

    @ExceptionHandler(value = {UserAlreadyRegisterException.class})
    public ResponseEntity<GenericMessage<String>> handleUserAlreadyRegister(UserAlreadyRegisterException ex, WebRequest request) {
        GenericMessage<String> genericMessage = new GenericMessage<>();
        genericMessage.getMessages().add(ex.getMessage());
        return new ResponseEntity<GenericMessage<String>>(genericMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<GenericMessage<String>> handleConstraintViolation(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        GenericMessage<String> genericMessage = new GenericMessage<>();
        Set<String> genericMessages = genericMessage.getMessages();
        genericMessages.addAll(constraintViolations.stream().map(constraintViolation -> String.format("%s, %s, %s", constraintViolation.getPropertyPath(),
                constraintViolation.getInvalidValue(), constraintViolation.getMessage())).collect(Collectors.toSet()));
        return new ResponseEntity<GenericMessage<String>>(genericMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<GenericMessage<String>> handleMethodArgumentValidation(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        GenericMessage<String> genericMessage = new GenericMessage<>();
        Set<String> genericMessages = genericMessage.getMessages();
        genericMessages.addAll(
                result.getFieldErrors().stream()
                        .map(fieldError -> String.format("%s", fieldError.getDefaultMessage())).collect(Collectors.toSet()));
        return new ResponseEntity<GenericMessage<String>>(genericMessage, HttpStatus.BAD_REQUEST);
    }
}
