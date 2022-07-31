package io.my.calender.restdocs;

import io.my.calender._class.payload.request.*;
import io.my.calender._class.payload.response.ClassJoinUserInfoResponse;
import io.my.calender._class.payload.response.ClassTimeListResponse;
import io.my.calender.base.base.RestDocAttributes;
import io.my.calender.base.base.RestdocsBase;
import io.my.calender.base.payload.BaseExtentionResponse;
import io.my.calender.base.payload.BaseResponse;
import io.my.calender._class.payload.response.InviteClassListResponse;
import io.my.calender._class.payload.response.SearchClassResponse;
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

class ClassRestdocsTest extends RestdocsBase {

    @Test
    @DisplayName("수업 생성 API")
    void createClass() {

        List<CreateClassTimeRequest> list = new ArrayList<>();
        CreateClassTimeRequest classTimeRequest1 = CreateClassTimeRequest
                .builder()
                .startHour(11)
                .startMinutes(0)
                .endHour(12)
                .endMinutes(0)
                .day("월")
                .build()
                ;

        CreateClassTimeRequest classTimeRequest2 = CreateClassTimeRequest
                .builder()
                .startHour(11)
                .startMinutes(0)
                .endHour(12)
                .endMinutes(0)
                .day("수")
                .build();

        list.add(classTimeRequest1);
        list.add(classTimeRequest2);

        CreateClassRequest requestBody = CreateClassRequest
                .builder()
                .title("경제학원론")
                .content("메모메모메모")
                .location("인문관 101호")
                .collegeId(1L)
                .startDate(1647652678000L)
                .endDate(1658193478000L)
                .professorId(1L)
                .classTimeList(list)
                .alarmType("class")
                .build();

        Mockito.when(classService.createClass(Mockito.any())).thenReturn(Mono.just(new BaseExtentionResponse<>(1L)));

        RequestFieldsSnippet requestFieldsSnippet =
                requestFields(
                        fieldWithPath("title").description("수업명")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("content").description("메모").optional()
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
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("professorId").description("교수님 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("startDate").description("시작일")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("unixTime")),
                        fieldWithPath("endDate").description("종료일")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("unixTime")),
                        fieldWithPath("alarmType").description("알람 타입(class, personal)")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("classTimeList.[].startHour").description("시작시간 (시)")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("classTimeList.[].startMinutes").description("시작시간 (분)")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("classTimeList.[].endHour").description("종료시간 (시)")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("classTimeList.[].endMinutes").description("종료시간 (분)")
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
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue").description("수업 번호")
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
        List<Long> userList = new ArrayList<>();
        userList.add(1L);
        userList.add(2L);

        var requestBody = InviteClassRequeset.builder()
                .classId(3L)
                .userList(userList)
                .build()
                ;

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
        JoinClassRequest requestBody = JoinClassRequest
                .builder()
                .classId(1L)
                .content("메모입니다")
                .alarmType("class")
                .build();
        Mockito.when(classService.joinClass(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

        RequestFieldsSnippet requestFieldsSnippet =
                requestFields(
                        fieldWithPath("classId").description("수업 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")
                                ),
                        fieldWithPath("content").description("메모")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")
                                ),
                        fieldWithPath("alarmType").description("알람 타입")
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
                                        RestDocAttributes.format("Integer"))
                );

        postWebTestClient(requestBody, "/calender/class/join").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/joinclass", requestFieldsSnippet, responseFieldsSnippet));

    }

    @Test
    @DisplayName("수업 참가 취소 API")
    void acceptClass() {
        AcceptClassRequest requestBody = AcceptClassRequest
                .builder()
                .classId(1L)
                .accept(Boolean.TRUE)
                .content("메모 수정입니다...")
                .build();

        Mockito.when(classService.acceptClass(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

        RequestFieldsSnippet requestFieldsSnippet =
                requestFields(
                        fieldWithPath("classId").description("수업 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")
                                ),
                        fieldWithPath("accept").description("승낙 여부")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Boolean")
                                ),
                        fieldWithPath("content").description("개인 메모").optional()
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")
                                ),
                        fieldWithPath("alarmType").description("알람 타입")
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
                                        RestDocAttributes.format("Integer"))
                );

        patchWebTestClient(requestBody, "/calender/class/accept").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/acceptclass", requestFieldsSnippet, responseFieldsSnippet));
    }

    @Test
    @DisplayName("수업 정보 변경")
    void modifyClassInfo() {
        List<ClassTimeListRequest> classTimeList = new ArrayList<>();
        classTimeList.add(
                ClassTimeListRequest.builder()
                        .day("수")
                        .startHour(11)
                        .startMinutes(0)
                        .endHour(12)
                        .endMinutes(0)
                        .build()
        );
        classTimeList.add(
                ClassTimeListRequest.builder()
                        .day("목")
                        .startHour(14)
                        .startMinutes(0)
                        .endHour(16)
                        .endMinutes(0)
                        .build()
        );
        var requestBody = ModifyClassInfoRequest
                .builder()
                .id(1L)
                .professorId(1L)
                .location("인문관 1호")
                .title("경제학원론")
                .alarmType("class")
                .content("메모메모")
                .isChangeActiveHistory(true)
                .startDate(1647652678000L)
                .endDate(1658193478000L)
                .classTimeList(classTimeList)
                .build()
                ;

        Mockito.when(classService.modifyClassInfo(Mockito.any())).thenReturn(Mono.just(new BaseResponse()));

        RequestFieldsSnippet requestFieldsSnippet =
                requestFields(
                        fieldWithPath("id").description("수업 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("title").description("수업 제목")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("location").description("수업 장소")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("professorId").description("교수님 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("alarmType").description("알람 타입 ( class, personal )")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("content").description("개인 메모")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("isChangeActiveHistory").description("활동 내역에 추가 여부")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Boolean")),
                        fieldWithPath("startDate").description("시작일")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("unixTime")),
                        fieldWithPath("endDate").description("종료일")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("unixTime")),
                        fieldWithPath("classTimeList.[].day").description("요일")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("classTimeList.[].startHour").description("시작시간")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("classTimeList.[].startMinutes").description("시작분")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("classTimeList.[].endHour").description("종료시간")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("classTimeList.[].endMinutes").description("종료분")
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

        putWebTestClient(requestBody, "/calender/class/info").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/modifyclassinfo", requestFieldsSnippet, responseFieldsSnippet));

    }

    @Test
    @DisplayName("강의 검색")
    void searchClasses() {
        List<SearchClassResponse> list = new ArrayList<>();

        List<ClassTimeListResponse> classTimeList = new ArrayList<>();
        classTimeList.add(
                ClassTimeListResponse.builder()
                        .startHour(10)
                        .endHour(11)
                        .startMinutes(0)
                        .endMinutes(50)
                .build()
        );

        SearchClassResponse response = SearchClassResponse.builder()
                .id(1L)
                .userId(1L)
                .startDate(1647652678000L)
                .endDate(1658193478000L)
                .title("경제학원론")
                .location("인문관 1호")
                .professorName("김교수")
                .inviteUserCount(30)
                .acceptUserCount(2)
                .classTimeList(classTimeList)
                .imageUrl("http://mysend.co.kr:8080/image/image?fileName=c91a6281-d9bd-4119-95ac-d57c17c0451a_charactor.jpeg")
                .build()
                ;

        list.add(response);

        Mockito.when(classService.searchClasses(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(new BaseExtentionResponse<>(list)));

        RequestParametersSnippet requestParametersSnippet =
                requestParameters(
                        parameterWithName("title").description("수업 제목검색")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")
                                ),
                        parameterWithName("classId").description("수업 번호(페이징용도)").optional()
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")
                                ),
                        parameterWithName("perPage").description("페이지당 개수(default: 10)").optional()
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
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.[].id").description("수업 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.[].userId").description("수업 생성자 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.[].startDate").description("수업 시작일자")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("unixtime")),
                        fieldWithPath("returnValue.[].endDate").description("수업 종료일자")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("unixtime")),
                        fieldWithPath("returnValue.[].title").description("수업명")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].location").description("수업 장소")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].professorName").description("교수명")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].imageUrl").description("이미지 주소")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].classTimeList.[].day").description("수업 요일")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].classTimeList.[].startHour").description("시작 시간(분)")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].classTimeList.[].startMinutes").description("시작 시간(분)")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].classTimeList.[].endHour").description("종료 시간(시)")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].classTimeList.[].endMinutes").description("종료 시간(분)")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].inviteUserCount").description("수업 초대 인원")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.[].acceptUserCount").description("수업 초대 수락 인원")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer"))
                );
        String params = "?title=경제";

        getWebTestClient("/calender/class" + params).expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/searchclasses", requestParametersSnippet, responseFieldsSnippet));
    }

    @Test
    @DisplayName("초대받은 수업 목록 조회")
    void findInviteClassList() {
        List<InviteClassListResponse> list = new ArrayList<>();
        List<ClassTimeListResponse> classTimeList = new ArrayList<>();

        classTimeList.add(
                ClassTimeListResponse.builder()
                        .day("월")
                        .startHour(11)
                        .startMinutes(10)
                        .endHour(12)
                        .endMinutes(10)
                        .build()
        );

        classTimeList.add(
                ClassTimeListResponse.builder()
                        .day("화")
                        .startHour(11)
                        .startMinutes(30)
                        .endHour(12)
                        .endMinutes(30)
                        .build()
        );

        list.add(InviteClassListResponse.builder()
                .id(1L)
                .title("경제학원론")
                .location("인문관 1호")
                .professorName("김교수")
                .imageUrl(null)
                .classTimeList(classTimeList)
                .build());

        Mockito.when(classService.findInviteClassList()).thenReturn(Mono.just(new BaseExtentionResponse<>(list)));

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
                        fieldWithPath("returnValue.[].id").description("수업 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.[].title").description("수업명")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].location").description("수업 장소")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].professorName").description("교수명")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].imageUrl").description("이미지 주소")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].alarmType").description("알람 타입")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].classTimeList.[].day").description("요일")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.[].classTimeList.[].startHour").description("시작 시간 (시)")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.[].classTimeList.[].startMinutes").description("시작 시간 (분)")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.[].classTimeList.[].endHour").description("종료 시간 (시)")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.[].classTimeList.[].endMinutes").description("종료 시간 (분)")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer"))
                );

        getWebTestClient("/calender/class/invite").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/findinviteclasslist", responseFieldsSnippet));

    }

    @Test
    @DisplayName("수업 상세 조회")
    void findClassDetail() {
        List<ClassJoinUserInfoResponse> joinUserList = new ArrayList<>();

        joinUserList.add(
                ClassJoinUserInfoResponse.builder()
                        .userId(1L)
                        .nickname("nickname")
                        .name("name")
                        .accept((byte)1)
                        .alarmType("class")
                        .imageUrl("http://mysend.co.kr:8080/image/image?fileName=c91a6281-d9bd-4119-95ac-d57c17c0451a_charactor.jpeg")
                .build()
        );
        joinUserList.add(
                ClassJoinUserInfoResponse.builder()
                        .userId(2L)
                        .nickname("nickname1")
                        .name("name1")
                        .accept((byte)1)
                        .alarmType("class")
                        .imageUrl("http://mysend.co.kr:8080/image/image?fileName=c91a6281-d9bd-4119-95ac-d57c17c0451a_charactor.jpeg")
                        .build()
        );

        List<ClassTimeListResponse> classTimeList = new ArrayList<>();
        classTimeList.add(
                ClassTimeListResponse.builder()
                        .day("월")
                        .startHour(11)
                        .startMinutes(10)
                        .endHour(12)
                        .endMinutes(10)
                        .build()
        );
        classTimeList.add(
                ClassTimeListResponse.builder()
                        .day("수")
                        .startHour(11)
                        .startMinutes(30)
                        .endHour(12)
                        .endMinutes(30)
                        .build()
        );

        ClassDetailResponse responseBody = ClassDetailResponse.builder()
                .id(1L)
                .title("화학입문학")
                .startDate(1647652678000L)
                .endDate(1658193478000L)
                .content("내용입니다내용입니다.")
                .location("인문학관 101호")
                .classTimeList(classTimeList)
                .joinUserList(joinUserList)
                .inviteUserCount(2)
                .acceptUserCount(2)
                .isAccept(Boolean.TRUE)
                .professorName("김교수")
                .alarmType("class")
                .build();

        Mockito.when(classService.findClassDetail(Mockito.any())).thenReturn(Mono.just(new BaseExtentionResponse<>(responseBody)));

        PathParametersSnippet pathParametersSnippet = pathParameters(
                parameterWithName("id").description("수 번호")
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
                        fieldWithPath("returnValue.id").description("수업 번호")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.title").description("수업 제목")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.content").description("수업 내용")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.location").description("수업 위치")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.professorName").description("교수명")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.alarmType").description("알람 타입")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.startDate").description("시작일")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Unixtime")),
                        fieldWithPath("returnValue.endDate").description("종료일")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Unixtime")),
                        fieldWithPath("returnValue.classTimeList.[].day").description("수업일")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("String")),
                        fieldWithPath("returnValue.classTimeList.[].startHour").description("시작 시간 (시)")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.classTimeList.[].startMinutes").description("시작 시간 (분)")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.classTimeList.[].endHour").description("종료 시간 (시)")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
                        fieldWithPath("returnValue.classTimeList.[].endMinutes").description("종료 시간 (분)")
                                .attributes(
                                        RestDocAttributes.length(0),
                                        RestDocAttributes.format("Integer")),
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
                        fieldWithPath("returnValue.joinUserList.[].alarmType").description("알람 타입")
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

        getWebTestClientPathVariable(1, "/calender/class/detail/{id}").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/calenderclassdetail", pathParametersSnippet, responseFieldsSnippet));

    }

    @Test
    @DisplayName("초대받은 수업 개수 조회")
    void findClassInviteCount() {
        Mockito.when(classService.findClassInviteCount()).thenReturn(Mono.just(new BaseExtentionResponse<>(3)));

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

        getWebTestClient("/calender/class/invite/count").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/findclassinvitecount", responseFieldsSnippet));

    }
}
