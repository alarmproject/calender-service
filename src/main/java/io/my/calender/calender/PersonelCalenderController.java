package io.my.calender.calender;

import io.my.calender.base.payload.BaseResponse;
import io.my.calender.calender.payload.request.personel.CreatePersonelCalenderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calender/personel")
public class PersonelCalenderController {
    private final PersonelCalenderService personelCalenderService;

    @PostMapping
    public Mono<BaseResponse> createPersonelCalender(
            @RequestBody CreatePersonelCalenderRequest requestBody) {
        return this.personelCalenderService.createPersonelCalender(requestBody);
    }


}
