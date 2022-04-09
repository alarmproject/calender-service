package io.my.calender.calender;

import io.my.calender.active.service.ActiveService;
import io.my.calender.base.payload.BaseExtentionResponse;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.calender.payload.request.ModifyCalender;
import io.my.calender.calender.payload.response.CalenderListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calender")
public class CalenderController {
    private final ActiveService activeService;
    private final CalenderService calenderService;

    @GetMapping
    public Mono<BaseExtentionResponse<List<CalenderListResponse>>> getCalender(
            @RequestParam("type") String type) {
        return this.calenderService.getCalender(type);
    }

    @PatchMapping
    public Mono<BaseResponse> modifyCalender(
            @RequestBody ModifyCalender requestBody) {
        Mono.fromCallable(() -> this.activeService.modifyCalender(requestBody.getId()).subscribe())
                .subscribeOn(Schedulers.boundedElastic());

        return this.activeService.modifyCalender(requestBody.getId()).then(this.calenderService.modifyCalender(requestBody));
    }

    @DeleteMapping("/{id}")
    public Mono<BaseResponse> removeCalender(@PathVariable("id") Long id) {
        return this.activeService.removeCalender(id)
                .then(this.calenderService.removeCalender(id));
    }


}
