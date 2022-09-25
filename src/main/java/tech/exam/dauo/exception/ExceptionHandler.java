package tech.exam.dauo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.exam.dauo.response.DaouExamAPIResponse;


import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

    DaouExamAPIResponse response = new DaouExamAPIResponse();
    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<InvalidExceptionHandeler> exceptionHandler(AccessDeniedException ex) {

        response = DaouExamAPIResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .message("허용되지 않은 접근자 입니다.")
                .count(0)
                .build();
        return new ResponseEntity(response, response.getHttpStatus());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<InvalidExceptionHandeler> exceptionHandler(UserNotFoundException ex) {

        response = DaouExamAPIResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .httpStatus(HttpStatus.NOT_FOUND)
                .message("찾을 수 없습니다.")
                .count(0)
                .build();
        return new ResponseEntity(response, response.getHttpStatus());
    }

}
