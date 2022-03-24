package io.my.calender.calender._class;

import io.my.calender.base.annotation.Logger;
import io.my.calender.base.payload.BaseExtentionResponse;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.calender._class.payload.request.CreateClassRequest;
import io.my.calender.calender._class.payload.request.InviteClassRequeset;
import io.my.calender.calender._class.payload.request.ModifyClassInfoRequest;
import io.my.calender.calender._class.payload.response.SearchClassResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    public Mono<BaseResponse> createClass(
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
            @RequestParam("classId") Long classId) {
        return this.classService.joinClass(classId);
    }

    @Logger
    @DeleteMapping("/refuse")
    public Mono<BaseResponse> refuseClass(@RequestParam("classId") Long classId) {
        return this.classService.refuseClass(classId);
    }

    @Logger
    @PatchMapping("/info")
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

}
