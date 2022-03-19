package io.my.calender.restdocs;

import io.my.calender.base.base.RestDocAttributes;
import io.my.calender.base.base.RestdocsBase;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.calender.payload.request._class.CreateClassRequest;
import io.my.calender.calender.payload.request._class.CreateClassTimeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;

class ClassRestdocsTest extends RestdocsBase {

    @Test
    @DisplayName("수업 생성 API")
    void createClass() {
        CreateClassRequest requestBody = new CreateClassRequest();
        requestBody.setTitle("경제학원론");
        requestBody.setContent("경제학원론 수업입니다.");
        requestBody.setLocation("인문관 101호");
        requestBody.setCollegeId(1L);
        requestBody.setStartDate(1647652678000L);
        requestBody.setEndDate(1658193478000L);

        List<CreateClassTimeRequest> list = new ArrayList<>();
        CreateClassTimeRequest classTimeRequest1 = new CreateClassTimeRequest();
        classTimeRequest1.setStartTime(10);
        classTimeRequest1.setEndTime(12);
        classTimeRequest1.setDay("월");

        CreateClassTimeRequest classTimeRequest2 = new CreateClassTimeRequest();
        classTimeRequest2.setStartTime(11);
        classTimeRequest2.setEndTime(12);
        classTimeRequest2.setDay("수");

        list.add(classTimeRequest1);
        list.add(classTimeRequest2);

        requestBody.setClassTimeList(list);

        Mockito.when(classService.createClass(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

        RequestFieldsSnippet requestFieldsSnippet =
                requestFields(
                        fieldWithPath("title").description("수업명")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("content").description("메모")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("location").description("수업 장소")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("collegeId").description("대학교 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("startDate").description("시작일")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("unixTime")),
                        fieldWithPath("endDate").description("종료일")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("unixTime")),
                        fieldWithPath("classTimeList.[].startTime").description("시작시간")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("classTimeList.[].endTime").description("종료시간")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("classTimeList.[].day").description("요일")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String"))
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
                                        RestDocAttributes.format("Integer"))
                );

        postWebTestClient(requestBody, "/calender/class").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/createclass", requestFieldsSnippet, responseFieldsSnippet));
    }

}
