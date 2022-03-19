package io.my.calender.calender;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calender/personel")
public class PersonelCalenderController {
    private final PersonelCalenderService personelCalenderService;


}
