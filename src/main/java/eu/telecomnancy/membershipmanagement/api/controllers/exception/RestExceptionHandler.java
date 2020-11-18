package eu.telecomnancy.membershipmanagement.api.controllers.exception;

import eu.telecomnancy.membershipmanagement.api.services.exceptions.MembershipManagementException;
import eu.telecomnancy.membershipmanagement.api.services.exceptions.UnknownEntityException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Rest exception handler to digest inner exceptions and filter them before sending them to the client
 */
@Log4j2
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * Handle application custom exceptions
     *
     * @param exception Application exception
     * @return The formatted 400 associated
     */
    @ResponseBody
    @ExceptionHandler(value = MembershipManagementException.class)
    public ResponseEntity<?> handleMembershipManagementException(MembershipManagementException exception) {
        log.error(
                "BAD REQUEST | {} : {}",
                exception.getClass().getSimpleName(),
                exception.getMessage());

        return ResponseEntity.badRequest()
                .body(exception.getReason());
    }

    /**
     * Handle application custom exceptions on missing entities
     *
     * @param exception Application exception
     * @return A formatted 404 error
     */
    @ResponseBody
    @ExceptionHandler(value = UnknownEntityException.class)
    public ResponseEntity<?> handleUnknownMemberException(UnknownEntityException exception) {
        log.error(
                "NOT FOUND | {} : {}",
                exception.getClass().getSimpleName(),
                exception.getMessage());

        return ResponseEntity.notFound().build();
    }

    /**
     * Handle Hibernate validation exceptions
     * From: https://www.baeldung.com/spring-boot-bean-validation
     *
     * @param exception Hibernate validation exception
     * @return The formatted 400 associated
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getAllErrors()
                .forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        //noinspection ConstantConditions
        log.error(
                "VALIDATION | Intercepted validation errors for {} with messages : {}",
                exception.getBindingResult().getTarget().getClass(),
                errors);

        return ResponseEntity.badRequest()
                .body(errors);
    }

}
