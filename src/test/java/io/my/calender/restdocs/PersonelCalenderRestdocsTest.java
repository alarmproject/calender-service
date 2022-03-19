package io.my.calender.restdocs;

import io.my.calender.base.base.RestDocAttributes;
import io.my.calender.base.base.RestdocsBase;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.calender.payload.request.personel.CreatePersonelCalenderRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import reactor.core.publisher.Mono;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;

class PersonelCalenderRestdocsTest extends RestdocsBase {

    @Test
    @DisplayName("개인 일정 생성 API")
    void createPersonelCalender() {
        CreatePersonelCalenderRequest requestBody = new CreatePersonelCalenderRequest();
        requestBody.setTitle("사진동아리 신입생 환영회");
        requestBody.setContent("신입생 환영회입니다.");
        requestBody.setLocation("사진동아리 동아리실");
        requestBody.setStartTime(1647652678000L);
        requestBody.setEndTime(1647658613000L);
        requestBody.setOpen(Boolean.TRUE);

        Mockito.when(personelCalenderService.createPersonelCalender(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

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
                .consumeWith(createConsumer("/createPersonelCalender", requestFieldsSnippet, responseFieldsSnippet));
    }
}
