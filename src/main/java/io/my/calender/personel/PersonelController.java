package io.my.calender.personel;

import io.my.calender.base.annotation.Logger;
import io.my.calender.base.payload.BaseExtentionResponse;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.personel.payload.request.*;
import io.my.calender.personel.payload.response.MyPersonelCalenderListResponse;
import io.my.calender.personel.payload.response.SearchPersonelCalenderListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

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

    @Logger
    @GetMapping("/my/list")
    public Mono<BaseExtentionResponse<List<MyPersonelCalenderListResponse>>> myPersonelCalenderList(
            @RequestParam(required = false, name = "id") Long id,
            @RequestParam(required = false, name = "aceept") Boolean accept,
            @RequestParam(required = false, name = "open") Boolean open) {
        return this.personelService.myPersonelCalenderList(id, accept, open);
    }

    @Logger
    @GetMapping
    public Mono<BaseExtentionResponse<List<SearchPersonelCalenderListResponse>>> searchPersonelCalenderList(
            @RequestParam(required = false, name = "personelCalenderId") Long personelCalenderId,
            @RequestParam(required = false, name = "perPage", defaultValue = "10") Integer perPage,
            @RequestParam(required = false, name = "title") String title) {

        return this.personelService.searchPersonelCalenderList(personelCalenderId, perPage, title);
    }

    @Logger
    @GetMapping("/detail/{id}")
    public Mono<BaseExtentionResponse<PersonelCalenderDetailResponse>> findPersonelCalenderDetail(
            @PathVariable("id") Long id) {
        return this.personelService.findPersonelCalenderDetail(id);
    }


}
