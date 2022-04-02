package io.my.calender.restdocs;

import io.my.calender.base.base.RestDocAttributes;
import io.my.calender.base.base.RestdocsBase;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.personel.payload.request.AcceptPersoneCalenderRequest;
import io.my.calender.personel.payload.request.CreatePersonelRequest;
import io.my.calender.personel.payload.request.InvitePersonelRequest;
import io.my.calender.personel.payload.request.ModifyPersonelCalenderRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.PathParametersSnippet;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

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
    @DisplayName("개인 일정 삭제")
    void removePersonelCalender() {
        Mockito.when(personelService.removePersonelCalender(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

        PathParametersSnippet pathParametersSnippet = pathParameters(
                parameterWithName("id").description("개인 일정 번호")
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

        deleteWebTestClient(1, "/calender/personel/{id}").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/removepersonelcalender", pathParametersSnippet, responseFieldsSnippet));
    }

    @Test
    @DisplayName("개인 일정 수락 or 거절")
    void acceptPersonelCalender() {
        var requestBody = new AcceptPersoneCalenderRequest();
        requestBody.setAccept(Boolean.FALSE);
        requestBody.setPersonelCalenderId(1L);

        Mockito.when(personelService.acceeptPersonelCalender(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

        RequestFieldsSnippet requestFieldsSnippet =
                requestFields(
                        fieldWithPath("personelCalenderId").description("일정 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("accept").description("수락 여부")
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
        patchWebTestClient(requestBody, "/calender/personel/accept").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/acceptpersonelcalender", requestFieldsSnippet, responseFieldsSnippet));
    }

    @Test
    @DisplayName("개인 일정 정보 수정")
    void modifyPersonelCalenderInfo() {
        var requestBody = new ModifyPersonelCalenderRequest();
        requestBody.setPersonelCalenderId(1L);
        requestBody.setContent("신입생 환영회");
        requestBody.setTitle("사진동아리 신입생 환영회");
        requestBody.setLocation("사진동아리 동아리실");
        requestBody.setOpen(Boolean.TRUE);
        Mockito.when(personelService.modifyPersonelCalenderInfo(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

        RequestFieldsSnippet requestFieldsSnippet =
                requestFields(
                        fieldWithPath("personelCalenderId").description("일정 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("title").description("일정 제목")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("content").description("설명")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("location").description("장소")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("open").description("일정 공개 여부")
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
        patchWebTestClient(requestBody, "/calender/personel/info").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/modifypersonelcalenderinfo", requestFieldsSnippet, responseFieldsSnippet));
    }
}
