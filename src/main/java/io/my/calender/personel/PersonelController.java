package io.my.calender.personel;

import io.my.calender.base.annotation.Logger;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.personel.payload.request.AcceptPersoneCalenderRequest;
import io.my.calender.personel.payload.request.CreatePersonelRequest;
import io.my.calender.personel.payload.request.InvitePersonelRequest;
import io.my.calender.personel.payload.request.ModifyPersonelCalenderRequest;
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
    @DeleteMapping("/{id}")
    public Mono<BaseResponse> removePersonelCalender(
            @PathVariable("id") Long personelCalenderId) {
        return this.personelService.removePersonelCalender(personelCalenderId);
    }

    @Logger
    @PatchMapping("/accept")
    public Mono<BaseResponse> acceeptPersonelCalender(
            @RequestBody AcceptPersoneCalenderRequest requestBody) {
        return this.personelService.acceeptPersonelCalender(requestBody);
    }

    @Logger
    @PatchMapping("/info")
    public Mono<BaseResponse> modifyPersonelCalenderInfo(
            @RequestBody ModifyPersonelCalenderRequest requestBody) {
        return this.personelService.modifyPersonelCalenderInfo(requestBody);
    }


}
