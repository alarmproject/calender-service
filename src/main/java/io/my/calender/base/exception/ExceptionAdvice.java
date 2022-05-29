package io.my.calender.base.exception;

import io.my.calender.base.exception.object.DatabaseException;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.slack.SlackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {
    private final SlackService slackService;

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<BaseResponse> exceptionAdvice(Exception e) {
        slackService.sendSlackException(e);
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(
                new BaseResponse(
                        ErrorTypeEnum.SERVER_ERROR.getCode(),
                        ErrorTypeEnum.SERVER_ERROR.getResult()));
    }

    @ExceptionHandler(DatabaseException.class)
    protected ResponseEntity<BaseResponse> exceptionAdvice(DatabaseException e) {
        slackService.sendSlackException(e);
        return ResponseEntity.internalServerError()
                .body(new BaseResponse(
                        ErrorTypeEnum.DATABASE_EXCEPTION.getCode(),
                        ErrorTypeEnum.DATABASE_EXCEPTION.getResult()
                ));
    }



}
