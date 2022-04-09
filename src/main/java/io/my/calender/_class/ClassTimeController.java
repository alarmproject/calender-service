package io.my.calender._class;

import io.my.calender._class.payload.request.ModifyClassTimeRequest;
import io.my.calender.active.service.ActiveService;
import io.my.calender.base.annotation.Logger;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.base.repository.ActiveHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calender/class/time")
public class ClassTimeController {
    private final ClassTimeService classTimeService;
    private final ActiveService activeService;

    @Logger
    @PatchMapping
    public Mono<BaseResponse> modifyClassTime(
            @RequestBody ModifyClassTimeRequest requestBody) {
        Mono.fromCallable(() -> this.activeService.modifyClassTime(requestBody.getId()).subscribe())
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();

        return this.classTimeService.modifyClassTime(requestBody);
    }

    @Logger
    @DeleteMapping("/{id}")
    public Mono<BaseResponse> removeClassTime(
            @PathVariable("id") Long id) {
        return this.activeService.removeClassTime(id)
                .then(this.classTimeService.removeClassTime(id));
    }

}
