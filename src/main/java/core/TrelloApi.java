package core;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

import static constants.ApiSettings.MAX_RESPONSE_TIME;
import static org.hamcrest.Matchers.lessThan;

public abstract class TrelloApi {
    public static ResponseSpecification goodResponseSpecification() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(MAX_RESPONSE_TIME))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static ResponseSpecification notFoundResponseSpecification() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.TEXT)
                .expectResponseTime(lessThan(MAX_RESPONSE_TIME))
                .expectStatusCode(HttpStatus.SC_NOT_FOUND)
                .build();
    }

    public static ResponseSpecification badResponseSpecification() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.TEXT)
                .expectResponseTime(lessThan(MAX_RESPONSE_TIME))
                .expectStatusCode(Matchers.oneOf(HttpStatus.SC_BAD_REQUEST,
                        HttpStatus.SC_INTERNAL_SERVER_ERROR))
                .build();
    }
}