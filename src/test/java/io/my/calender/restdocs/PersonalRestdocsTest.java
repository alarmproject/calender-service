package io.my.calender.restdocs;

import io.my.calender.base.base.RestDocAttributes;
import io.my.calender.base.base.RestdocsBase;
import io.my.calender.base.payload.BaseExtentionResponse;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.personal.payload.request.*;
import io.my.calender.personal.payload.response.MyPersonalCalenderListResponse;
import io.my.calender.personal.payload.response.PersonalCalenderInviteResponse;
import io.my.calender.personal.payload.response.PersonalCalenderJoinUserInfoResponse;
import io.my.calender.personal.payload.response.SearchPersonalCalenderListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.PathParametersSnippet;
import org.springframework.restdocs.request.RequestParametersSnippet;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

class PersonalRestdocsTest extends RestdocsBase {

    @Test
    @DisplayName("개인 일정 생성 API")
    void createPersonal() {
        CreatePersonalRequest requestBody = CreatePersonalRequest
                .builder()
                .title("사진동아리 신입생 환영회")
                .content("메모메모")
                .location("사진동아리 동아리실")
                .startTime(1647652678000L)
                .endTime(1647658613000L)
                .open(Boolean.TRUE)
                .alarmType("personal")
                .build();

        Mockito.when(personalService.createPersonalCalender(Mockito.any())).thenReturn(Mono.just(new BaseExtentionResponse<>(1L)));

        RequestFieldsSnippet requestFieldsSnippet =
                requestFields(
                        fieldWithPath("title").description("수업명")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("location").description("수업 장소")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("content").description("메모")
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
                                        RestDocAttributes.format("Boolean")),
                        fieldWithPath("alarmType").description("알람 타입( class, alarmType )")
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
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue").description("생성된 일정 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer"))
                );

        postWebTestClient(requestBody, "/calender/personal").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/createpersonal", requestFieldsSnippet, responseFieldsSnippet));
    }

    @Test
    @DisplayName("일정 초대 API")
    void invitePersonalCalener() {
        List<Long> userList = new ArrayList<>();
        userList.add(1L);
        userList.add(2L);

        var requestBody = InvitePersonalRequest.builder()
                .personalCalenderId(1L)
                .userList(userList)
                .build()
                ;

        Mockito.when(personalService.invitePersonalCalender(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

        RequestFieldsSnippet requestFieldsSnippet =
                requestFields(
                        fieldWithPath("personalCalenderId").description("일정 번호")
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

        postWebTestClient(requestBody, "/calender/personal/invite").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/invitepersonalcalender", requestFieldsSnippet, responseFieldsSnippet));
    }

    @Test
    @DisplayName("개인 일정 삭제")
    void removePersonalCalender() {
        Mockito.when(personalService.removePersonalCalender(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

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

        deleteWebTestClient(1, "/calender/personal/{id}").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/removepersonalcalender", pathParametersSnippet, responseFieldsSnippet));
    }

    @Test
    @DisplayName("개인 일정 수락 or 거절")
    void acceptPersonalCalender() {
        var requestBody = AcceptPersonalCalenderRequest.builder()
                .accept(Boolean.FALSE)
                .content("메모입니다.")
                .alarmType("personnel")
                .personalCalenderId(1L)
                .build();

        Mockito.when(personalService.acceeptPersonalCalender(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

        RequestFieldsSnippet requestFieldsSnippet =
                requestFields(
                        fieldWithPath("personalCalenderId").description("일정 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("accept").description("수락 여부")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Boolean")),
                        fieldWithPath("content").description("메모")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("alarmType").description("알람 타입")
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
        patchWebTestClient(requestBody, "/calender/personal/accept").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/acceptpersonalcalender", requestFieldsSnippet, responseFieldsSnippet));
    }

    @Test
    @DisplayName("개인 일정 정보 수정")
    void modifyPersonalCalenderInfo() {
        var requestBody = ModifyPersonalCalenderRequest.builder()
                .personalCalenderId(1L)
                .title("사진동아리 신입생 환영회")
                .location("사진동아리 동아리실")
                .content("메모메모")
                .open(Boolean.TRUE)
                .alarmType("personal")
                .isChangeActiveHistory(true)
                .startTime(1659240812563L)
                .endTime(1659240812563L)
                .build();

        Mockito.when(personalService.modifyPersonalCalenderInfo(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

        RequestFieldsSnippet requestFieldsSnippet =
                requestFields(
                        fieldWithPath("personalCalenderId").description("일정 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("title").description("일정 제목")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("location").description("장소")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("content").description("메모")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("open").description("일정 공개 여부")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Boolean")),
                        fieldWithPath("alarmType").description("알람 타입( class, personal )")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("isChangeActiveHistory").description("활동 내역에 추가 여부")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Boolean")),
                        fieldWithPath("startTime").description("시작 시간")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("unixtime")),
                        fieldWithPath("endTime").description("종료 시간")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("unixtime"))
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
        patchWebTestClient(requestBody, "/calender/personal/info").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/modifypersonalcalenderinfo", requestFieldsSnippet, responseFieldsSnippet));
    }

    @Test
    @DisplayName("내 개인 일정 목록 조회")
    void test() {
        List<MyPersonalCalenderListResponse> list = new ArrayList<>();

        list.add(
                MyPersonalCalenderListResponse.builder()
                        .id(1L)
                        .day("수")
                        .title("title")
                        .content("content")
                        .location("학교 정문")
                        .open(Boolean.TRUE)
                        .accept(Boolean.FALSE)
                        .userId(1L)
                        .name("name")
                        .nickname("nickname")
                        .email(EMAIL)
                        .alarmType("personal")
                        .imageUrl("http://mysend.co.kr:8080/image/image?fileName=65632a55-0280-4afb-b19d-c62fdf15b87e_charactor.jpeg")
                        .build()
        );

        BaseExtentionResponse<List<MyPersonalCalenderListResponse>> responseBody = new BaseExtentionResponse<>(list);
        Mockito.when(personalService.myPersonalCalenderList(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Mono.just(responseBody));
        RequestParametersSnippet requestParametersSnippet =
                requestParameters(
                        parameterWithName("id").description("마지막 id (페이징용)").optional()
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")
                                ),
                        parameterWithName("accept").description("승인 여부").optional()
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Boolean")
                                ),
                        parameterWithName("open").description("공개 여부").optional()
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Boolean")
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
                        fieldWithPath("returnValue.[].id").description("조인 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.[].day").description("일정 요일")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].title").description("일정 제목")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].content").description("일정 내용")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].location").description("일정 장소")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].open").description("공개 여부")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Boolean")),
                        fieldWithPath("returnValue.[].accept").description("승낙 여부")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Boolean")),
                        fieldWithPath("returnValue.[].alarmType").description("알람 타입")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].userId").description("일정 생성 유저 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.[].name").description("일정 생성 유저 이름")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].nickname").description("일정 생성 유저 닉네임")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].email").description("일정 생성 유저 이메일")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].imageUrl").description("일정 생성 유저 프로필 사진 경로")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String"))
                );

        getWebTestClient("/calender/personal/my/list").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/mypersonalcalenderlist", requestParametersSnippet, responseFieldsSnippet));
    }

    @Test
    @DisplayName("개인 일정 검색")
    void searchPersonalCalenderList() {
        List<SearchPersonalCalenderListResponse> list = new ArrayList<>();

        list.add(
                SearchPersonalCalenderListResponse.builder()
                        .id(1L)
                        .title("title")
                        .location("location")
                        .day("토")
                        .open(Boolean.TRUE)
                        .regDateTime(1647853895000L)
                        .modDateTime(1647853895000L)
                        .startTime(1647652678000L)
                        .endTime(1647658613000L)
                        .userId(1L)
                        .name("userName")
                        .nickname("nickname")
                        .email(EMAIL)
                        .imageUrl("http://mysend.co.kr:8080/image/image?fileName=65632a55-0280-4afb-b19d-c62fdf15b87e_charactor.jpeg")
                        .build()
        );

        BaseExtentionResponse<List<SearchPersonalCalenderListResponse>> responseBody = new BaseExtentionResponse<>(list);
        Mockito.when(personalService.searchPersonalCalenderList(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Mono.just(responseBody));
        RequestParametersSnippet requestParametersSnippet =
                requestParameters(
                        parameterWithName("personalCalenderId").description("마지막 id (페이징용)").optional()
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")
                                ),
                        parameterWithName("perPage").description("페이지당 개수").optional()
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Boolean")
                                ),
                        parameterWithName("title").description("제목").optional()
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Boolean")
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
                        fieldWithPath("returnValue.[].id").description("조인 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.[].day").description("일정 요일")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].title").description("일정 제목")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].location").description("일정 장소")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].open").description("공개 여부")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Boolean")),
                        fieldWithPath("returnValue.[].regDateTime").description("등록일시")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("unixtime")),
                        fieldWithPath("returnValue.[].modDateTime").description("수정일시")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("unixtime")),
                        fieldWithPath("returnValue.[].startTime").description("시작일시")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("unixtime")),
                        fieldWithPath("returnValue.[].endTime").description("종료일시")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("unixtime")),
                        fieldWithPath("returnValue.[].userId").description("일정 생성 유저 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.[].name").description("일정 생성 유저 이름")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].nickname").description("일정 생성 유저 닉네임")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].email").description("일정 생성 유저 이메일")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].imageUrl").description("일정 생성 유저 프로필 사진 경로")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String"))
                );

        getWebTestClient("/calender/personal").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/searchpersonalcalenderlist", requestParametersSnippet, responseFieldsSnippet));
    }

    @Test
    @DisplayName("개인 일정 상세 조회")
    void findPersonalCalenderDetail() {
        List<PersonalCalenderJoinUserInfoResponse> joinUserList = new ArrayList<>();

        joinUserList.add(
                PersonalCalenderJoinUserInfoResponse.builder()
                        .userId(1L)
                        .nickname("nickname")
                        .name("name")
                        .accept((byte)1)
                        .imageUrl("http://mysend.co.kr:8080/image/image?fileName=c91a6281-d9bd-4119-95ac-d57c17c0451a_charactor.jpeg")
                .build()
        );
        joinUserList.add(
                PersonalCalenderJoinUserInfoResponse.builder()
                        .userId(2L)
                        .nickname("nickname1")
                        .name("name1")
                        .accept((byte)1)
                        .imageUrl("http://mysend.co.kr:8080/image/image?fileName=c91a6281-d9bd-4119-95ac-d57c17c0451a_charactor.jpeg")
                .build()
        );

        PersonalCalenderDetailResponse responseBody = PersonalCalenderDetailResponse.builder()
                .id(3L)
                .userId(1L)
                .title("신입생 환영회 1111")
                .content("수정수")
                .location("수정입문학과 101호")
                .day("금")
                .open(Boolean.FALSE)
                .startTime(1650417478000L)
                .endTime(1650423413000L)
                .joinUserList(joinUserList)
                .inviteUserCount(3)
                .acceptUserCount(1)
                .isAccept(Boolean.TRUE)
                .build();

        Mockito.when(personalService.findPersonalCalenderDetail(Mockito.any())).thenReturn(Mono.just(new BaseExtentionResponse<>(responseBody)));

        PathParametersSnippet pathParametersSnippet = pathParameters(
                parameterWithName("id").description("개인 일정 번호")
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
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.id").description("일정 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.userId").description("유저 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.title").description("일정 제목")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.content").description("일정 내용")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.location").description("일정 위치")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.startTime").description("시작 시간")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Unixtime")),
                        fieldWithPath("returnValue.endTime").description("종료 시간")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Unixtime")),
                        fieldWithPath("returnValue.day").description("수업일")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.open").description("공개 여부")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Boolean")),
                        fieldWithPath("returnValue.joinUserList.[].userId").description("사용자 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.joinUserList.[].accept").description("승낙 여부")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.joinUserList.[].imageUrl").description("프로필 사진 주소")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.joinUserList.[].name").description("사용자 이름")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.joinUserList.[].nickname").description("사용자 닉네임")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.inviteUserCount").description("초대 유저 수")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.acceptUserCount").description("승낙 유저 수")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.isAccept").description("승낙 여부(요청자)")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Boolean"))
                );

        getWebTestClientPathVariable(3, "/calender/personal/detail/{id}").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/calenderpersonaldetail", pathParametersSnippet, responseFieldsSnippet));


    }
    @Test
    @DisplayName("초대받은 개인 일정 개수 조회")
    void findClassInviteCount() {
        Mockito.when(personalService.findPersonalInviteCount()).thenReturn(Mono.just(new BaseExtentionResponse<>(3)));

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
                        fieldWithPath("returnValue").description("초대받은 일정 개수")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer"))
                );

        getWebTestClient("/calender/personal/invite/count").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/findpersonalinvitecount", responseFieldsSnippet));

    }
    @Test
    @DisplayName("초대받은 개인 일정 조회")
    void findClassInvite() {
        PersonalCalenderInviteResponse firstResponse = PersonalCalenderInviteResponse
                .builder()
                .id(1L)
                .title("Title")
                .location("Location")
                .alarmType("personal")
                .day("수")
                .imageUrl("http://mysend.co.kr:8080/image/image?fileName=c91a6281-d9bd-4119-95ac-d57c17c0451a_charactor.jpeg")
                .startTime(null)
                .endTime(null)
                .inviteUser("김민수")
                .acceptUserCount(3)
                .build();
        List<PersonalCalenderInviteResponse> responseBody = List.of(firstResponse);
        Mockito.when(personalService.findPersonalInvite()).thenReturn(Mono.just(new BaseExtentionResponse<>(responseBody)));

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
                        fieldWithPath("returnValue.[].id").description("초대받은 일정 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.[].title").description("초대받은 일정명")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].location").description("위치")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].alarmType").description("알람타입")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].day").description("일자")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].imageUrl").description("일정 만든사람 이미지")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].startTime").description("시작일시")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("unixtime")),
                        fieldWithPath("returnValue.[].endTime").description("종료일시")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("unixtime")),
                        fieldWithPath("returnValue.[].inviteUser").description("초대한 사람")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].acceptUserCount").description("수락 유저 수")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer"))
                );

        getWebTestClient("/calender/personal/invite").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/findpersonalinvite", responseFieldsSnippet));

    }
}
