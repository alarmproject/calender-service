package io.my.calender.base.controller;

import io.my.calender.base.annotation.Logger;
import io.my.calender.base.context.JwtContextHolder;
import io.my.calender.base.exception.object.DatabaseException;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.base.repository.dao.DualDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HealthCheckController {
    private final DualDAO dualDAO;

    @Logger
    @GetMapping("/healthcheck")
    public Mono<ResponseEntity<BaseResponse>> healthCheck() {
        return dualDAO.callDbTest().map(result -> {
            if (!result) throw new DatabaseException();
            return ResponseEntity.ok(new BaseResponse("service is health"));
        });
    }
}
