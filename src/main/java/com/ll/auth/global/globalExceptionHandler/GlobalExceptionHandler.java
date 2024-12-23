package com.ll.auth.global.globalExceptionHandler;

import com.ll.auth.global.app.AppConfig.AppConfig;
import com.ll.auth.global.exceptions.ServiceException;
import com.ll.auth.global.rsdata.RsData;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<RsData<Void>> handle(NoSuchElementException ex) {
        // 개발자입장에서 에러 보기
        if(AppConfig.isNotProd()){ ex.printStackTrace();}
        return  ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new RsData<>(
                        "404-1",
                        "해당 데이터가 존재하지 않습니다."
                        )
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RsData<Void>> handle(MethodArgumentNotValidException ex) {

        if(AppConfig.isNotProd()){ ex.printStackTrace();}

        String msg = ex.getBindingResult().getAllErrors()
                .stream()
                .filter(error -> error instanceof FieldError)
                .map(error -> (FieldError) error)
                .sorted(Comparator.comparing(FieldError::getField))
                .map(error -> error.getField() + "-" + error.getCode() + "-" + error.getDefaultMessage())
                .collect(Collectors.joining("\n"));
        return  ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new RsData<>(
                                "400-1",
                                 msg
                        )
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RsData<Void>> handle(HttpMessageNotReadableException ex) {
        if(AppConfig.isNotProd()){ ex.printStackTrace();}
        return  ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new RsData<>(
                                "400-HttpNotReadable",
                                "HTTP 메세지가 이상합니다."
                        )
                );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<RsData<Void>> handle(DataIntegrityViolationException ex) {
        if(AppConfig.isNotProd()){ ex.printStackTrace();}
        return  ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new RsData<>(
                                "400-1",
                                "이미 존재하는 데이터입니다."
                        )
                );
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<RsData<Void>> handle(ServiceException ex) {
        if(AppConfig.isNotProd()){ ex.printStackTrace();}
        RsData<Void> rsData = ex.getRsData();
        return ResponseEntity
                .status(rsData.getStatusCode())
                .body(rsData);
    }
}
