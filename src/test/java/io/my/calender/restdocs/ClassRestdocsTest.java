package io.my.calender.restdocs;

import io.my.calender.base.base.RestDocAttributes;
import io.my.calender.base.base.RestdocsBase;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.calender._class.payload.request.CreateClassRequest;
import io.my.calender.calender._class.payload.request.CreateClassTimeRequest;
import io.my.calender.calender._class.payload.request.InviteClassRequeset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.RequestParametersSnippet;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

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

    @Test
    @DisplayName("수업 초대 API")
    public void inviteClass() {
        var requestBody = new InviteClassRequeset();
        requestBody.setClassId(3L);

        List<Long> userList = new ArrayList<>();
        userList.add(1L);
        userList.add(2L);
        requestBody.setUserList(userList);

        Mockito.when(classService.inviteUser(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

        RequestFieldsSnippet requestFieldsSnippet =
                requestFields(
                        fieldWithPath("classId").description("수업 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("userList.[]").description("초대할 회원 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer"))
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

        postWebTestClient(requestBody, "/calender/class/invite").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/inviteclass", requestFieldsSnippet, responseFieldsSnippet));
    }

    @Test
    @DisplayName("수업 참가 API")
    public void joinClass() {
        Mockito.when(classService.joinClass(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

        RequestParametersSnippet requestParametersSnippet =
                requestParameters(
                        parameterWithName("classId").description("수업 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")
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
                                        RestDocAttributes.format("Integer"))
                );

        String params = "?classId=3";

        postWebTestClient("/calender/class/join" + params).expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/joinclass", requestParametersSnippet, responseFieldsSnippet));

    }

    @Test
    @DisplayName("수업 참가 취소 API")
    void refuseClass() {
        Mockito.when(classService.refuseClass(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

        RequestParametersSnippet requestParametersSnippet =
                requestParameters(
                        parameterWithName("classId").description("수업 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")
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
                                        RestDocAttributes.format("Integer"))
                );

        String params = "?classId=3";

        deleteWebTestClient("/calender/class/refuse" + params).expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/refuseclass", requestParametersSnippet, responseFieldsSnippet));
    }

}
