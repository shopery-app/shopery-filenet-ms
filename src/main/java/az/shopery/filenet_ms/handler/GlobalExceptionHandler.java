package az.shopery.filenet_ms.handler;

import az.shopery.filenet_ms.handler.exception.FileNotFoundException;
import az.shopery.filenet_ms.handler.exception.FileSavingException;
import az.shopery.filenet_ms.model.dto.shared.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.HtmlUtils;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFileNotFoundException(Exception exception, HttpServletRequest httpServletRequest) {
        log.error("file not found: {}", exception.getMessage());
        return buildErrorResponse(exception, HttpStatus.NOT_FOUND, httpServletRequest);
    }

    @ExceptionHandler(FileSavingException.class)
    public ResponseEntity<ErrorResponse> handleFileSavingException(Exception exception, HttpServletRequest httpServletRequest) {
        log.error("error while saving the file: {}", exception.getMessage());
        return buildErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, httpServletRequest);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception, HttpServletRequest httpServletRequest) {
        log.error("unexpected error occurred: {}", exception.getMessage());
        return buildErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, httpServletRequest);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception exception, HttpStatus httpStatus, HttpServletRequest httpServletRequest) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(httpStatus)
                .statusCode(httpStatus.value())
                .timestamp(LocalDateTime.now())
                .message(HtmlUtils.htmlEscape(exception.getMessage()))
                .path(HtmlUtils.htmlEscape(httpServletRequest.getRequestURI()))
                .build();

        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
