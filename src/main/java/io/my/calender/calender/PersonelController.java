package io.my.calender.calender;

import io.my.calender.base.annotation.Logger;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.calender.payload.request.personel.CreatePersonelRequest;
import io.my.calender.calender.payload.request.personel.InvitePersonelRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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

    @Logger
    @PostMapping("/invite")
    public Mono<BaseResponse> invitePersonelCalender(
            @RequestBody InvitePersonelRequest requestBody) {
        return this.personelService.invitePersonelCalender(requestBody);
    }

    @Logger
    @PatchMapping("join")
    public Mono<BaseResponse> joinPersonelCalender(
            @RequestParam("personelCalenderId") Long personelCalenderId) {
        return this.personelService.joinPersonelCalender(personelCalenderId);
    }


}
