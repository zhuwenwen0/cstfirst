
package cn.edu.aufe.cstfirst.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;

/**
 * 处理全局异常
 *
 * @author zhuwenwen
 * @date 2018/8/17 9:31
 */
@RestControllerAdvice
public class BlogExceptionHandler extends ResponseEntityExceptionHandler {


    private Logger logger = LoggerFactory.getLogger(BlogExceptionHandler.class);

    /**
     * @see ResponseEntityExceptionHandler#handleExceptionInternal
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        logger.warn("{}", ex.getMessage());

        return buildHandleException(BlogEnum.FAILURE_HTTP.getCode() + status.value(), ex.getMessage(), null);
    }

    /**
     * 处理ServletException
     *
     * @param ex 异常
     * @return 异常处理结果
     */
    @ExceptionHandler(value = {ServletException.class})
    protected final ResponseEntity<Object> handleServletException(ServletException ex) {
        logger.error("{}", ex);

        return buildHandleException(BlogEnum.FAILURE_SERVLET.getCode(), ex.getMessage(), null);
    }

    /**
     * 处理SQLException
     *
     * @param ex 异常
     * @return 异常处理结果
     */
    @ExceptionHandler(value = {SQLException.class})
    protected final ResponseEntity<Object> handleSQLException(SQLException ex) {
        logger.error("{}", ex);

        return buildHandleException(BlogEnum.FAILURE_DB.getCode(), ex.getMessage(), null);
    }

    /**
     * 处理ConstraintViolationException
     *
     * @param ex 异常
     * @return 异常处理结果
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        logger.error("{}", ex);

        String message = ex.getMessage();
        if (ex.getConstraintViolations() != null && !ex.getConstraintViolations().isEmpty()) {
            message = ex.getConstraintViolations().stream().findFirst().isPresent()
                    ? ex.getConstraintViolations().stream().findFirst().get().getMessage() : null;
        }

        return buildHandleException(BlogEnum.FAILURE_VALIDATION.getCode(), message, null);
    }

    /**
     * 处理CustomException异常
     *
     * @param ex 异常
     * @return 异常处理结果
     */
    @ExceptionHandler(value = {BlogException.class})
    protected final ResponseEntity<Object> handleMorphedException(BlogException ex) {
        logger.info("{}", ex);

        return buildHandleException(ex.getCode(), ex.getMessage(), ex.getData());
    }

    /**
     * 处理RuntimeException
     *
     * @param ex 异常
     * @return 异常处理结果
     */
    @ExceptionHandler(value = {RuntimeException.class})
    protected final ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        logger.error("{}", ex);

        return buildHandleException(BlogEnum.FAILURE_UNKNOWN.getCode(), ex.getMessage(), null);
    }

    private ResponseEntity<Object> buildHandleException(int errno, String msg, Object data) {
        if (data == null) {
            return new ResponseEntity<>(Result.error(errno, msg), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Result.error(errno, msg, data), HttpStatus.OK);
        }
    }

}
