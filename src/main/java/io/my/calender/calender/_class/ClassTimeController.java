package io.my.calender.calender._class;

import io.my.calender.base.annotation.Logger;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.calender._class.payload.request.ModifyClassTimeRequest;
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
            @RequestBody ModifyClassTimeRequest requestBody) {
        return this.classTimeService.modifyClassTime(requestBody);
    }

    @Logger
    @DeleteMapping("/{id}")
    public Mono<BaseResponse> removeClassTime(
            @PathVariable("id") Long id) {
        return this.classTimeService.removeClassTime(id);
    }

}
