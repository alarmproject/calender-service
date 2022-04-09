package io.my.calender.restdocs;

import io.my.calender._class.payload.request.*;
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
        requestBody.setProfessorId(1L);

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
    void acceptClass() {
        AcceptClassRequest requestBody = new AcceptClassRequest();
        requestBody.setClassId(1L);
        requestBody.setAccept(Boolean.TRUE);

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
        var requestBody = new ModifyClassInfoRequest();
        requestBody.setId(1L);
        requestBody.setProfessorId(1L);
        requestBody.setLocation("인문관 1호");
        requestBody.setTitle("경제학원론");
        requestBody.setContent("경제학과 1학년 필수 수업입니다.");

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
                        fieldWithPath("content").description("수업 설명")
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

        patchWebTestClient(requestBody, "/calender/class/info").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/modifyclassinfo", requestFieldsSnippet, responseFieldsSnippet));

    }

    @Test
    @DisplayName("강의 검색")
    void searchClasses() {
        List<SearchClassResponse> list = new ArrayList<>();

        SearchClassResponse response = SearchClassResponse.builder()
                .id(1L)
                .userId(1L)
                .startDate(1647652678000L)
                .endDate(1658193478000L)
                .title("경제학원론")
                .content("경제학원론 수업입니다.")
                .location("인문관 1호")
                .professorName("김교수")
                .inviteUserCount(30)
                .acceptUserCount(2)
                .day("수,월")
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
                        fieldWithPath("returnValue.[].content").description("수업 소개(메모)")
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
                        fieldWithPath("returnValue.[].day").description("수업 요일")
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
        list.add(InviteClassListResponse.builder()
                .id(1L)
                .title("경제학원론")
                .content("경제학 1학년 수업")
                .location("인문관 1호")
                .professorName("김교수")
                .imageUrl(null)
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
                        fieldWithPath("returnValue.[].content").description("수업 소개(메모)")
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
                                        RestDocAttributes.format("String"))
                );

        getWebTestClient("/calender/class/invite").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/findinviteclasslist", responseFieldsSnippet));

    }

}
