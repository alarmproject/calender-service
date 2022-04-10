package io.my.calender.restdocs;

import io.my.calender.base.base.RestDocAttributes;
import io.my.calender.base.base.RestdocsBase;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender._class.payload.request.ModifyClassTimeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.PathParametersSnippet;
import reactor.core.publisher.Mono;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

class ClassTimeRestdocsTest extends RestdocsBase {

    @Test
    @DisplayName("수업 일정 수정 API")
    void modifyClassTime() {
        var requestBody = ModifyClassTimeRequest
                .builder()
                .startTime(10)
                .endTime(12)
                .day("수")
                .build();

        Mockito.when(classTimeService.modifyClassTime(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

        RequestFieldsSnippet requestFieldsSnippet =
                requestFields(
                        fieldWithPath("id").description("수업 시간 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("startTime").description("시작시간")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("endTime").description("종료시간")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("day").description("요일")
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

        patchWebTestClient(requestBody, "/calender/class/time").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/modifyclasstime", requestFieldsSnippet, responseFieldsSnippet));
    }

    @Test
    @DisplayName("수업 일정 삭제 API")
    void removeClassTime() {
        Mockito.when(classTimeService.removeClassTime(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

        PathParametersSnippet pathParametersSnippet = pathParameters(
                parameterWithName("id").description("수업 일정 번호")
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

        deleteWebTestClient(1, "/calender/class/time/{id}").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/removeclasstime", pathParametersSnippet, responseFieldsSnippet));
    }
}
