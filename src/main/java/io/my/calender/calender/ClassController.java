package io.my.calender.calender;

import io.my.calender.base.annotation.Logger;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.calender.payload.request._class.CreateClassRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calender/class")
public class ClassController {
    private final ClassService classService;

    @Logger
    @PostMapping
    public Mono<BaseResponse> createClass(
            @RequestBody CreateClassRequest requestBody) {
        return this.classService.createClass(requestBody);
    }

}
