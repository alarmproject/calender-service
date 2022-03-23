package io.my.calender.calender._class;

import io.my.calender.base.annotation.Logger;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.calender._class.payload.request.ModifyClassTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calender/class/time")
public class ClassTimeController {
    private final ClassTimeService classTimeService;

    @Logger
    @PatchMapping
    public Mono<BaseResponse> modifyClassTime(
            @RequestBody ModifyClassTime requestBody) {
        return classTimeService.modifyClassTime(requestBody);
    }

    @Logger
    @DeleteMapping("/{id}")
    public Mono<BaseResponse> removeClassTime(
            @PathVariable("id") Long id) {
        return classTimeService.removeClassTime(id);
    }

}
