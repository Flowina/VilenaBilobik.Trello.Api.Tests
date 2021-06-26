package core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import constants.ParameterName;
import entities.TrelloBoard;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static constants.ApiSettings.API_KEY;
import static constants.ApiSettings.API_TOKEN;

public class TrelloBoardServiceObj extends TrelloApi {
    public static final URI API_URL =
            URI.create("https://api.trello.com/1/boards/");

    private static long requestNumber = 0L;
    private Method requestMethod;
    private String basePath;
    private Map<String, String> parameters;
    private Map<String, String> body;

    private TrelloBoardServiceObj(
            Map<String, String> parameters,
            Map<String, String> body,
            Method requestMethod,
            String basePath) {
        this.parameters = parameters;
        this.body = body;
        this.requestMethod = requestMethod;
        this.basePath = basePath;
    }

    public static ApiRequestBuilder requestBuilder() {
        return new ApiRequestBuilder();
    }

    public Response sendRequest() {
        return RestAssured
                .given(requestSpecification(this))
                .log()
                .all()
                .queryParams(parameters)
                .body(body)
                .request(requestMethod)
                .prettyPeek();
    }

    public static RequestSpecification requestSpecification(TrelloBoardServiceObj obj) {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .addQueryParam("requestNumber", UUID.randomUUID().toString())
                .addQueryParam("key", API_KEY)
                .addQueryParam("token", API_TOKEN)
                .setBaseUri(API_URL)
                .setBasePath(obj.basePath)
                .build();
    }

    public static TrelloBoard getBoard(Response response) {
        Type type = (new TypeToken<TrelloBoard>() {}).getType();
        TrelloBoard board = new Gson()
            .fromJson(response.asString().trim(), type);
        return board;
    }

    public static class ApiRequestBuilder {
        private Map<String, String> parameters = new HashMap<>();
        private Map<String, String> body = new HashMap<>();
        private Method requestMethod = Method.GET;
        private String basePath = "";

        public ApiRequestBuilder setMethod(Method method) {
            requestMethod = method;
            return this;
        }

        public ApiRequestBuilder setBasePath(String value) {
            basePath = value;
            return this;
        }

        public ApiRequestBuilder setId(String value) {
            parameters.put(ParameterName.ID, value);
            return this;
        }

        public ApiRequestBuilder setName(String value) {
            body.put(ParameterName.NAME, value);
            return this;
        }

        public ApiRequestBuilder setDescription(String value) {
            parameters.put(ParameterName.DESCRIPTION, value);
            return this;
        }

        public TrelloBoardServiceObj buildRequest() {
            return new TrelloBoardServiceObj(parameters, body, requestMethod, basePath);
        }
    }
}
