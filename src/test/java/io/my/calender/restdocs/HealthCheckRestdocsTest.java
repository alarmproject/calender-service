package io.my.calender.restdocs;

import io.my.calender.base.base.RestDocAttributes;
import io.my.calender.base.base.RestdocsBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.RequestParametersSnippet;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

class HealthCheckRestdocsTest extends RestdocsBase {

    @Test
    @DisplayName("HealthCheck Controller")
    void healthCheck() {
        RequestParametersSnippet requestParametersSnippet = requestParameters();
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

        getWebTestClientUnAuth("/healthcheck").expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(createConsumer("/healthcheck", requestParametersSnippet, responseFieldsSnippet));
    }

}