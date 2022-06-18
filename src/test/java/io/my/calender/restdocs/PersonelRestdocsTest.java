package io.my.calender.restdocs;

import io.my.calender.base.base.RestDocAttributes;
import io.my.calender.base.base.RestdocsBase;
import io.my.calender.base.payload.BaseExtentionResponse;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender.personel.payload.request.*;
import io.my.calender.personel.payload.response.MyPersonelCalenderListResponse;
import io.my.calender.personel.payload.response.PersonelCalenderJoinUserInfoResponse;
import io.my.calender.personel.payload.response.SearchPersonelCalenderListResponse;
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

class PersonelRestdocsTest extends RestdocsBase {

    @Test
    @DisplayName("개인 일정 생성 API")
    void createPersonel() {
        CreatePersonelRequest requestBody = CreatePersonelRequest
                .builder()
                .title("사진동아리 신입생 환영회")
                .location("사진동아리 동아리실")
                .startTime(1647652678000L)
                .endTime(1647658613000L)
                .open(Boolean.TRUE)
                .alarmType("personel")
                .build();

        Mockito.when(personelService.createPersonelCalender(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

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
        List<Long> userList = new ArrayList<>();
        userList.add(1L);
        userList.add(2L);

        var requestBody = InvitePersonelRequest.builder()
                .personelCalenderId(1L)
                .userList(userList)
                .build()
                ;

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
        var requestBody = AcceptPersoneCalenderRequest.builder()
                .accept(Boolean.FALSE)
                .content("메모입니다.")
                .alarmType("personnel")
                .personelCalenderId(1L)
                .build();

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
        patchWebTestClient(requestBody, "/calender/personel/accept").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/acceptpersonelcalender", requestFieldsSnippet, responseFieldsSnippet));
    }

    @Test
    @DisplayName("개인 일정 정보 수정")
    void modifyPersonelCalenderInfo() {
        var requestBody = ModifyPersonelCalenderRequest.builder()
                .personelCalenderId(1L)
                .title("사진동아리 신입생 환영회")
                .location("사진동아리 동아리실")
                .open(Boolean.TRUE)
                .alarmType("personel")
                .build();

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
                        fieldWithPath("location").description("장소")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("open").description("일정 공개 여부")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Boolean")),
                        fieldWithPath("alarmType").description("알람 타입( class, personel )")
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
        patchWebTestClient(requestBody, "/calender/personel/info").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/modifypersonelcalenderinfo", requestFieldsSnippet, responseFieldsSnippet));
    }

    @Test
    @DisplayName("내 개인 일정 목록 조회")
    void test() {
        List<MyPersonelCalenderListResponse> list = new ArrayList<>();

        list.add(
                MyPersonelCalenderListResponse.builder()
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
                        .alarmType("personel")
                        .imageUrl("http://mysend.co.kr:8080/image/image?fileName=65632a55-0280-4afb-b19d-c62fdf15b87e_charactor.jpeg")
                        .build()
        );

        BaseExtentionResponse<List<MyPersonelCalenderListResponse>> responseBody = new BaseExtentionResponse<>(list);
        Mockito.when(personelService.myPersonelCalenderList(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Mono.just(responseBody));
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

        getWebTestClient("/calender/personel/my/list").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/mypersonelcalenderlist", requestParametersSnippet, responseFieldsSnippet));
    }

    @Test
    @DisplayName("개인 일정 검색")
    void searchPersonelCalenderList() {
        List<SearchPersonelCalenderListResponse> list = new ArrayList<>();

        list.add(
                SearchPersonelCalenderListResponse.builder()
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

        BaseExtentionResponse<List<SearchPersonelCalenderListResponse>> responseBody = new BaseExtentionResponse<>(list);
        Mockito.when(personelService.searchPersonelCalenderList(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Mono.just(responseBody));
        RequestParametersSnippet requestParametersSnippet =
                requestParameters(
                        parameterWithName("personelCalenderId").description("마지막 id (페이징용)").optional()
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

        getWebTestClient("/calender/personel").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/searchpersonelcalenderlist", requestParametersSnippet, responseFieldsSnippet));
    }

    @Test
    @DisplayName("개인 일정 상세 조회")
    void findPersonelCalenderDetail() {
        List<PersonelCalenderJoinUserInfoResponse> joinUserList = new ArrayList<>();

        joinUserList.add(
                PersonelCalenderJoinUserInfoResponse.builder()
                        .userId(1L)
                        .nickname("nickname")
                        .name("name")
                        .accept((byte)1)
                        .imageUrl("http://mysend.co.kr:8080/image/image?fileName=c91a6281-d9bd-4119-95ac-d57c17c0451a_charactor.jpeg")
                .build()
        );
        joinUserList.add(
                PersonelCalenderJoinUserInfoResponse.builder()
                        .userId(2L)
                        .nickname("nickname1")
                        .name("name1")
                        .accept((byte)1)
                        .imageUrl("http://mysend.co.kr:8080/image/image?fileName=c91a6281-d9bd-4119-95ac-d57c17c0451a_charactor.jpeg")
                .build()
        );

        PersonelCalenderDetailResponse responseBody = PersonelCalenderDetailResponse.builder()
                .id(3L)
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

        Mockito.when(personelService.findPersonelCalenderDetail(Mockito.any())).thenReturn(Mono.just(new BaseExtentionResponse<>(responseBody)));

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

        getWebTestClientPathVariable(3, "/calender/personel/detail/{id}").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/calenderpersoneldetail", pathParametersSnippet, responseFieldsSnippet));


    }
    @Test
    @DisplayName("초대받은 개인 일정 개수 조회")
    void findClassInviteCount() {
        Mockito.when(personelService.findPersonelInviteCount()).thenReturn(Mono.just(new BaseExtentionResponse<>(3)));

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
                        fieldWithPath("returnValue").description("초대받은 수업 개수")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer"))
                );

        getWebTestClient("/calender/personel/invite/count").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/findpersonelinvitecount", responseFieldsSnippet));

    }
}
