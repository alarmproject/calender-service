package io.my.calender.personal;

import io.my.calender.base.annotation.Logger;
import io.my.calender.base.payload.BaseExtentionResponse;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.personal.payload.request.*;
import io.my.calender.personal.payload.response.MyPersonalCalenderListResponse;
import io.my.calender.personal.payload.response.PersonalCalenderInviteResponse;
import io.my.calender.personal.payload.response.SearchPersonalCalenderListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calender/personal")
public class PersonalController {
    private final PersonalService personalService;

    @Logger
    @PostMapping
    public Mono<BaseResponse> createPersonalCalender(
            @RequestBody CreatePersonalRequest requestBody) {
        return this.personalService.createPersonalCalender(requestBody);
    }

    @Logger
    @PostMapping("/invite")
    public Mono<BaseResponse> invitePersonalCalender(
            @RequestBody InvitePersonalRequest requestBody) {
        return this.personalService.invitePersonalCalender(requestBody);
    }

    @Logger
    @DeleteMapping("/{id}")
    public Mono<BaseResponse> removePersonalCalender(
            @PathVariable("id") Long personalCalenderId) {
        return this.personalService.removePersonalCalender(personalCalenderId);
    }

    @Logger
    @PatchMapping("/accept")
    public Mono<BaseResponse> acceeptPersonalCalender(
            @RequestBody AcceptPersonalCalenderRequest requestBody) {
        return this.personalService.acceeptPersonalCalender(requestBody);
    }

    @Logger
    @PatchMapping("/info")
    public Mono<BaseResponse> modifyPersonalCalenderInfo(
            @RequestBody ModifyPersonalCalenderRequest requestBody) {
        return this.personalService.modifyPersonalCalenderInfo(requestBody);
    }

    @Logger
    @GetMapping("/my/list")
    public Mono<BaseExtentionResponse<List<MyPersonalCalenderListResponse>>> myPersonalCalenderList(
            @RequestParam(required = false, name = "id") Long id,
            @RequestParam(required = false, name = "aceept") Boolean accept,
            @RequestParam(required = false, name = "open") Boolean open) {
        return this.personalService.myPersonalCalenderList(id, accept, open);
    }

    @Logger
    @GetMapping
    public Mono<BaseExtentionResponse<List<SearchPersonalCalenderListResponse>>> searchPersonalCalenderList(
            @RequestParam(required = false, name = "personalCalenderId") Long personalCalenderId,
            @RequestParam(required = false, name = "perPage", defaultValue = "10") Integer perPage,
            @RequestParam(required = false, name = "title") String title) {

        return this.personalService.searchPersonalCalenderList(personalCalenderId, perPage, title);
    }

    @Logger
    @GetMapping("/detail/{id}")
    public Mono<BaseExtentionResponse<PersonalCalenderDetailResponse>> findPersonalCalenderDetail(
            @PathVariable("id") Long id) {
        return this.personalService.findPersonalCalenderDetail(id);
    }

    @Logger
    @GetMapping("/invite/count")
    public Mono<BaseExtentionResponse<Integer>> findPersonalInviteCount() {
        return this.personalService.findPersonalInviteCount();
    }

    @Logger
    @GetMapping("/invite")
    public Mono<BaseExtentionResponse<List<PersonalCalenderInviteResponse>>> findPersonalInvite() {
        return this.personalService.findPersonalInvite();
    }


}
