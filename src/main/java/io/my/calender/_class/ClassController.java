package io.my.calender._class;

import io.my.calender._class.payload.request.*;
import io.my.calender._class.payload.response.SearchClassResponse;
import io.my.calender.base.annotation.Logger;
import io.my.calender.base.payload.BaseExtentionResponse;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender._class.payload.response.InviteClassListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calender/class")
public class ClassController {
    private final ClassService classService;

    @Logger
    @PostMapping
    public Mono<BaseExtentionResponse<Long>> createClass(
            @RequestBody CreateClassRequest requestBody) {
        return this.classService.createClass(requestBody);
    }

    @Logger
    @PostMapping("/invite")
    public Mono<BaseResponse> inviteClass(
            @RequestBody InviteClassRequeset requestBody) {
        return this.classService.inviteUser(requestBody);
    }

    @Logger
    @PostMapping("/join")
    public Mono<BaseResponse> joinClass(
            @RequestBody JoinClassRequest requestBody) {
        return this.classService.joinClass(requestBody);
    }

    @Logger
    @PatchMapping("/accept")
    public Mono<BaseResponse> acceptClass(
            @RequestBody AcceptClassRequest requestBody) {
        return this.classService.acceptClass(requestBody);
    }

    @Logger
    @PutMapping("/info")
    public Mono<BaseResponse> modifyClassInfo(
            @RequestBody ModifyClassInfoRequest requestBody) {

        return this.classService.modifyClassInfo(requestBody);
    }

    @Logger
    @GetMapping
    public Mono<BaseExtentionResponse<List<SearchClassResponse>>> searchClasses(
            @RequestParam(required = false, name = "classId") Long classId,
            @RequestParam(required = false, name = "perPage", defaultValue = "10") Integer perPage,
            @RequestParam("title") String title) {

        return this.classService.searchClasses(classId, perPage, title);
    }

    @Logger
    @GetMapping("/invite")
    public Mono<BaseExtentionResponse<List<InviteClassListResponse>>> findInviteClassList() {
        return this.classService.findInviteClassList();
    }

    @Logger
    @GetMapping("/detail/{id}")
    public Mono<BaseExtentionResponse<ClassDetailResponse>> findClassDetail(
            @PathVariable("id") Long id) {
        return this.classService.findClassDetail(id);
    }

    @Logger
    @GetMapping("/invite/count")
    public Mono<BaseExtentionResponse<Integer>> getClassInviteCount() {
        return this.classService.findClassInviteCount();
    }

}
