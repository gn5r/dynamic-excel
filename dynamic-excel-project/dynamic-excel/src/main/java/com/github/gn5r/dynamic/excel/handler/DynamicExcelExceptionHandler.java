package com.github.gn5r.dynamic.excel.handler;

import com.github.gn5r.dynamic.excel.common.exception.RestRuntimeException;
import com.github.gn5r.dynamic.excel.common.resource.ErrorResource;

import org.apache.commons.collections4.CollectionUtils;
import org.seasar.doma.DomaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class DynamicExcelExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestRuntimeException.class)
    public ResponseEntity<?> handleRestRuntimeException(RestRuntimeException e) {
        // エラートレース
        log.error(e.getMessage(), e);

        // エラーレスポンスを作成
        ErrorResource error = new ErrorResource();
        error.setStatus(e.getStatus());
        error.setMessage(e.getMessage());

        if (CollectionUtils.isNotEmpty(e.getFieldErrors())) {
            error.setFieldErrors(e.getFieldErrors());
        }

        return new ResponseEntity<ErrorResource>(error, e.getStatus());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException e) {
        // エラートレース
        log.error(e.getMessage(), e);

        ErrorResource error = new ErrorResource();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        error.setMessage("Nullアクセスエラー");

        return new ResponseEntity<ErrorResource>(error, error.getStatus());
    }

    @ExceptionHandler(DomaException.class)
    public ResponseEntity<?> handleDomaException(DomaException e) {
        // エラートレース
        log.error(e.getMessage(), e);

        ErrorResource error = new ErrorResource();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        error.setMessage(e.getMessage());

        return new ResponseEntity<ErrorResource>(error, error.getStatus());
    }

    @ExceptionHandler(NoClassDefFoundError.class)
    public ResponseEntity<?> handleNoClassDefFoundError(NoClassDefFoundError e) {
        // エラートレース
        log.error(e.getMessage(), e);

        ErrorResource error = new ErrorResource();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        error.setMessage("クラスが見つかりません");

        return new ResponseEntity<ErrorResource>(error, error.getStatus());
    }
}
