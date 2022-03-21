package io.my.calender.calender;

import io.my.calender.base.payload.BaseExtentionResponse;
import io.my.calender.calender.payload.response.CalenderListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calender")
public class CalenderController {
    private final CalenderService calenderService;

    @GetMapping
    public Mono<BaseExtentionResponse<List<CalenderListResponse>>> getCalender(
            @RequestParam("type") String type) {
        return calenderService.getCalender(type);
    }




}
