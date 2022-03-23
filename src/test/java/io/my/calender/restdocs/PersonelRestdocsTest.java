package io.my.calender.restdocs;

import io.my.calender.base.base.RestDocAttributes;
import io.my.calender.base.base.RestdocsBase;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.calender.personel.payload.request.CreatePersonelRequest;
import io.my.calender.calender.personel.payload.request.InvitePersonelRequest;
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

class PersonelRestdocsTest extends RestdocsBase {

    @Test
    @DisplayName("개인 일정 생성 API")
    void createPersonel() {
        CreatePersonelRequest requestBody = new CreatePersonelRequest();
        requestBody.setTitle("사진동아리 신입생 환영회");
        requestBody.setContent("신입생 환영회입니다.");
        requestBody.setLocation("사진동아리 동아리실");
        requestBody.setStartTime(1647652678000L);
        requestBody.setEndTime(1647658613000L);
        requestBody.setOpen(Boolean.TRUE);

        Mockito.when(personelService.createPersonelCalender(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

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
                        fieldWithPath("startTime").description("시작일")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("unixTime")),
                        fieldWithPath("endTime").description("종료일")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("unixTime")),
                        fieldWithPath("open").description("공개 여부(detault: false.비공개)")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Boolean"))
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

        postWebTestClient(requestBody, "/calender/personel").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/createpersonel", requestFieldsSnippet, responseFieldsSnippet));
    }

    @Test
    @DisplayName("일정 초대 API")
    void invitePersonelCalener() {
        var requestBody = new InvitePersonelRequest();
        requestBody.setPersonelCalenderId(1L);
        List<Long> userList = new ArrayList<>();
        userList.add(1L);
        userList.add(2L);

        requestBody.setUserList(userList);

        Mockito.when(personelService.invitePersonelCalender(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

        RequestFieldsSnippet requestFieldsSnippet =
                requestFields(
                        fieldWithPath("personelCalenderId").description("일정 번호")
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

        postWebTestClient(requestBody, "/calender/personel/invite").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/invitepersonelcalender", requestFieldsSnippet, responseFieldsSnippet));
    }

    @Test
    @DisplayName("일정 초대 수락 API")
    void joinPersonelCalender() {
        Mockito.when(personelService.joinPersonelCalender(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

        RequestParametersSnippet requestParametersSnippet =
                requestParameters(
                        parameterWithName("personelCalenderId").description("일정 번호")
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
        String params = "?personelCalenderId=3";

        patchWebTestClient("/calender/personel/join" + params).expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/joinpersonelcalender", requestParametersSnippet, responseFieldsSnippet));

    }
}
