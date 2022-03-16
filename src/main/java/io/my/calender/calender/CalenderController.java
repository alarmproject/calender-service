package io.my.calender.calender;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CalenderController {
    private final CalenderService calenderService;


}
