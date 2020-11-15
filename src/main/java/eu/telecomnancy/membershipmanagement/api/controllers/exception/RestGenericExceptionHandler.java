package eu.telecomnancy.membershipmanagement.api.controllers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Sinkhole for all runtime exception to translate them as HTTP 400 BAD REQUEST
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class RestGenericExceptionHandler extends RuntimeException { }
