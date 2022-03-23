package io.my.calender.restdocs;

import io.my.calender.base.base.RestDocAttributes;
import io.my.calender.base.base.RestdocsBase;
import io.my.calender.base.payload.BaseExtentionResponse;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.calender.payload.request.ModifyCalender;
import io.my.calender.calender.payload.response.CalenderListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.PathParametersSnippet;
import org.springframework.restdocs.request.RequestParametersSnippet;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

class CalenderRestdocsTest extends RestdocsBase {

    @Test
    @DisplayName("내 일정 조회")
    void getCalender() {
        var list = new ArrayList<CalenderListResponse>();

        list.add(
                CalenderListResponse.builder()
                    .id(1L)
                    .startTime(1647824400000L)
                    .endTime(1647831600000L)
                    .classId(1L)
                    .classTitle("경제학원론")
                    .classLocation("인문관 101호")
                    .userName("Kim Bosung")
                    .imageUrl("http://mysend.co.kr:8080/image?fileName=65632a55-0280-4afb-b19d-c62fdf15b87e_charactor.jpeg")
                    .inviteUserCount(15)
                    .acceptUserCount(14)
                    .build())
                ;

        list.add(
                CalenderListResponse.builder()
                        .id(1L)
                        .startTime(1648429200000L)
                        .endTime(1648436400000L)
                        .classId(1L)
                        .classTitle("경제학원론")
                        .classLocation("인문관 101호")
                        .userName("Kim Bosung")
                        .imageUrl("http://mysend.co.kr:8080/image?fileName=65632a55-0280-4afb-b19d-c62fdf15b87e_charactor.jpeg")
                        .inviteUserCount(15)
                        .acceptUserCount(14)
                        .build())
        ;

        list.add(
                CalenderListResponse.builder()
                        .id(1L)
                        .startTime(1648000800000L)
                        .endTime(1648004400000L)
                        .classId(1L)
                        .classTitle("경제학원론")
                        .classLocation("인문관 101호")
                        .userName("Kim Bosung")
                        .imageUrl("http://mysend.co.kr:8080/image?fileName=65632a55-0280-4afb-b19d-c62fdf15b87e_charactor.jpeg")
                        .inviteUserCount(15)
                        .acceptUserCount(14)
                        .build())
        ;
        list.add(
                CalenderListResponse.builder()
                        .id(1L)
                        .startTime(1648605600000L)
                        .endTime(1648609200000L)
                        .classId(1L)
                        .classTitle("경제학원론")
                        .classLocation("인문관 101호")
                        .userName("Kim Bosung")
                        .imageUrl("http://mysend.co.kr:8080/image?fileName=65632a55-0280-4afb-b19d-c62fdf15b87e_charactor.jpeg")
                        .inviteUserCount(15)
                        .acceptUserCount(14)
                        .build())
        ;
        list.add(
                CalenderListResponse.builder()
                        .id(1L)
                        .startTime(1647652678000L)
                        .endTime(1647658613000L)
                        .personelCalenderId(1L)
                        .personelCalenderTitle("사진동아리 신입생 환영회")
                        .personelCalenderLocation("동아리실")
                        .userName("Kim Bosung")
                        .imageUrl("http://mysend.co.kr:8080/image?fileName=65632a55-0280-4afb-b19d-c62fdf15b87e_charactor.jpeg")
                        .inviteUserCount(15)
                        .acceptUserCount(14)
                        .build())
        ;

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
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].userName").description("일정 생성자").optional()
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].imageUrl").description("일정 생성자 프로필 사진").optional()
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].inviteUserCount").description("일정 초대 인원").optional()
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.[].acceptUserCount").description("일정 수락 인원").optional()
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer"))
                );

        String params = "?type=month";

        getWebTestClient("/calender" + params).expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/getcalender", requestParametersSnippet, responseFieldsSnippet));
    }

    @Test
    @DisplayName("일정 수정")
    void modifyCalender() {
        var requestBody = new ModifyCalender();
        requestBody.setStartTime(1647652678000L);
        requestBody.setEndTime(1647658613000L);
        requestBody.setId(1L);

        Mockito.when(calenderService.modifyCalender(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

        RequestFieldsSnippet requestFieldsSnippet =
                requestFields(
                        fieldWithPath("id").description("일정 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("startTime").description("시작 시간")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("endTime").description("종료 시간")
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

        patchWebTestClient(requestBody, "/calender").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/modifycalender", requestFieldsSnippet, responseFieldsSnippet));
    }
    
    @Test
    @DisplayName("일정 삭제")
    void removeCalender() {

        Mockito.when(calenderService.removeCalender(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

        PathParametersSnippet pathParametersSnippet = pathParameters(
                parameterWithName("id").description("일정 번호")
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

        deleteWebTestClient(1, "/calender/{id}").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/removecalender", pathParametersSnippet, responseFieldsSnippet));
    }

}
