package io.my.calender.base.exception;

import io.my.calender.base.exception.object.DatabaseException;
import io.my.calender.base.payload.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<BaseResponse> exceptionAdvice(Exception e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(
                new BaseResponse(
                        ErrorTypeEnum.SERVER_ERROR.getCode(),
                        ErrorTypeEnum.SERVER_ERROR.getResult()));
    }

    @ExceptionHandler(DatabaseException.class)
    protected ResponseEntity<BaseResponse> exceptionAdvice(DatabaseException e) {
        return ResponseEntity.internalServerError()
                .body(new BaseResponse(
                        ErrorTypeEnum.DATABASE_EXCEPTION.getCode(),
                        ErrorTypeEnum.DATABASE_EXCEPTION.getResult()
                ));
    }



}
