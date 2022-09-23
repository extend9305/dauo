package tech.exam.dauo.validate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.exam.dauo.response.DaouExamAPIResponse;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class InvalidExceptionHandeler {
    DaouExamAPIResponse response = new DaouExamAPIResponse();
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<InvalidExceptionHandeler> constraintViolationException(ConstraintViolationException ex) {

        response = DaouExamAPIResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("잘못된 값으로 요청하였습니다.")
                    .count(0)
                    .build();
            return new ResponseEntity(response, response.getHttpStatus());
    }
}
