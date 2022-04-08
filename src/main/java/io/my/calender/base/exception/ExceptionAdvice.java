package io.my.calender.base.exception;

import io.my.calender.base.payload.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.server.EntityResponse;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    protected Mono<EntityResponse<BaseResponse>> exceptionAdvice(Exception e) {
        e.printStackTrace();
        return EntityResponse.fromObject(new BaseResponse(1, "Server Error")).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
