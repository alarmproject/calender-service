package io.my.calender.calender;

import io.my.calender.base.context.JwtContextHolder;
import io.my.calender.base.entity.Calender;
import io.my.calender.base.payload.BaseExtentionResponse;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.base.repository.CalenderRepository;
import io.my.calender.base.repository.dao.CalenderDAO;
import io.my.calender.base.util.DateUtil;
import io.my.calender.calender.payload.request.ModifyCalender;
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
    private final CalenderDAO calenderDAO;
    private final CalenderRepository calenderRepository;

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

            return calenderDAO.findCalenderListResponse(userId, startDate, endDate);
        }).collectList()
        .map(list -> {
            var responseBody = new BaseExtentionResponse<List<CalenderListResponse>>();
            responseBody.setReturnValue(list);
            return responseBody;
        });

    }

    public Mono<BaseResponse> modifyCalender(ModifyCalender requestBody) {
        return calenderRepository.findById(requestBody.getId()).flatMap(entity -> {
            entity.setStartTime(
                    dateUtil.unixTimeToLocalDateTime(requestBody.getStartTime())
            );
            entity.setEndTime(
                    dateUtil.unixTimeToLocalDateTime(requestBody.getEndTime())
            );
            return calenderRepository.save(entity);
        })
        .map(entity -> new BaseResponse())
        ;
    }

    public Mono<BaseResponse> removeCalender(Long id) {
        return calenderRepository.deleteById(id).map(o -> new BaseResponse());
    }
}
