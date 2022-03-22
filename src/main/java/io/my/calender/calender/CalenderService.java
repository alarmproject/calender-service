package io.my.calender.calender;

import io.my.calender.base.context.JwtContextHolder;
import io.my.calender.base.payload.BaseExtentionResponse;
import io.my.calender.base.repository.custom.CustomCalenderRepository;
import io.my.calender.base.util.DateUtil;
import io.my.calender.calender.payload.response.CalenderListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalenderService {
    private final DateUtil dateUtil;
    private final CustomCalenderRepository customCalenderRepository;

    public Mono<BaseExtentionResponse<List<CalenderListResponse>>> getCalender(String type) {
        return JwtContextHolder.getMonoUserId().flatMapMany(userId -> {
            LocalDate now = LocalDate.now();
            LocalDate startDate;
            LocalDate endDate;

            if (type.equals("week")) {
                startDate = dateUtil.findWeekStart(now);
                endDate = dateUtil.findWeekEnd(now).plusDays(1);
            } else if (type.equals("month")) {
                startDate = dateUtil.findMonthStart(now);
                endDate = dateUtil.findMonthEnd(now).plusDays(1);
            } else {
                throw new IllegalArgumentException();
            }

            return customCalenderRepository.findCalenderListResponse(userId, startDate, endDate);
        }).collectList()
        .map(list -> {
            var responseBody = new BaseExtentionResponse<List<CalenderListResponse>>();
            responseBody.setReturnValue(list);
            return responseBody;
        });

    }

}
