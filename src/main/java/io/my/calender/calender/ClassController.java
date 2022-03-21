package io.my.calender.calender;

import io.my.calender.base.annotation.Logger;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.calender.payload.request._class.CreateClassRequest;
import io.my.calender.calender.payload.request._class.InviteClassRequeset;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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

}
