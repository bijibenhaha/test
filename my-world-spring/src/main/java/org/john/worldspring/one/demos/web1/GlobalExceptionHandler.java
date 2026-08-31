package org.john.worldspring.one.demos.web1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.StringJoiner;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<?> handleValidationError(MethodArgumentNotValidException ex) {
        StringJoiner joiner = new StringJoiner("; ");
        ex.getBindingResult().getFieldErrors().forEach(
                e -> joiner.add(e.getField() + ": " + e.getDefaultMessage()));
        String msg = joiner.toString();
        log.warn("参数校验失败: {}", msg);
        return R.fail(HttpStatus.BAD_REQUEST.value(), msg);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<?> handleConstraint(ConstraintViolationException ex) {
        StringJoiner joiner = new StringJoiner("; ");
        ex.getConstraintViolations().forEach(
                e -> joiner.add(e.getPropertyPath() + ": " + e.getMessage()));
        String msg = joiner.toString();
        log.warn("参数校验失败: {}", msg);
        return R.fail(HttpStatus.BAD_REQUEST.value(), msg);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<?> handleException(Exception ex) {
        log.error("未处理异常", ex);
        return R.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }
}
