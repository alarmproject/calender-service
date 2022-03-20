package io.my.calender.calender;

import io.my.calender.base.annotation.Logger;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.calender.payload.request.personel.CreatePersonelRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.flogger.Flogger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calender/personel")
public class PersonelController {
    private final PersonelService personelService;

    @Logger
    @PostMapping
    public Mono<BaseResponse> createPersonelCalender(
            @RequestBody CreatePersonelRequest requestBody) {
        return this.personelService.createPersonelCalender(requestBody);
    }


}
