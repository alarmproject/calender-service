package io.my.calender.restdocs;

import io.my.calender.base.base.RestDocAttributes;
import io.my.calender.base.base.RestdocsBase;
import io.my.calender.base.payload.BaseExtentionResponse;
import io.my.calender.calender.payload.response.CalenderListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.RequestParametersSnippet;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

class CalenderRestdocsTest extends RestdocsBase {

    @Test
    @DisplayName("개인 일정 조회")
    void getCalender() {
        var list = new ArrayList<CalenderListResponse>();
        for (int index=1; index<5; index++) {
            var response = new CalenderListResponse();
            response.setId((long) index);
            response.setClassId((long) index);
            response.setClassTitle("경제학원론");
            response.setClassLocation("인문관 101호");
            list.add(response);
        }

        list.get(0).setStartTime(1647824400000L);
        list.get(0).setEndTime(1647831600000L);

        list.get(1).setStartTime(1648429200000L);
        list.get(1).setEndTime(1648436400000L);

        list.get(2).setStartTime(1648000800000L);
        list.get(2).setEndTime(1648004400000L);

        list.get(3).setStartTime(1648605600000L);
        list.get(3).setEndTime(1648609200000L);

        var response = new CalenderListResponse();
        response.setId(5L);
        response.setPersonelCalenderId(5L);
        response.setPersonelCalenderTitle("사진동아리 신입생 환영회");
        response.setPersonelCalenderLocation("동아리실");
        response.setStartTime(1647652678000L);
        response.setEndTime(1647658613000L);
        list.add(response);

        Mockito.when(calenderService.getCalender(Mockito.any())).thenReturn(Mono.just(new BaseExtentionResponse<>(list)));

        RequestParametersSnippet requestParametersSnippet =
                requestParameters(
                        parameterWithName("type").description("기간 (week, month)")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")
                                )
                );

        ResponseFieldsSnippet responseFieldsSnippet =
                responseFields(
                        fieldWithPath("result").description("결과 메시지")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("code").description("결과 코드")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.[].id").description("일정 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.[].startTime").description("일정 시작시간")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("unixTime")),
                        fieldWithPath("returnValue.[].endTime").description("일정 종료시간")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("unixTime")),
                        fieldWithPath("returnValue.[].classId").description("수업 번호").optional()
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.[].classTitle").description("수업 제목").optional()
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].classLocation").description("수업 장소").optional()
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].personelCalenderId").description("개인 일정 번호").optional()
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.[].personelCalenderTitle").description("개인 일정 제목").optional()
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].personelCalenderLocation").description("개인 일정 장소").optional()
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String"))
                );

        String params = "?type=month";

        getWebTestClient("/calender" + params).expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/getcalender", requestParametersSnippet, responseFieldsSnippet));
    }


}
