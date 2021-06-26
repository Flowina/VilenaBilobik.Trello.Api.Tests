package actions;

import core.TrelloBoardServiceObj;
import entities.TrelloBoard;
import io.restassured.http.Method;
import io.restassured.response.Response;

import static core.TrelloApi.goodResponseSpecification;

public class TrelloBoardActions {
    public static TrelloBoard createBoard(String name, String description) {
        Response response =
                TrelloBoardServiceObj.requestBuilder()
                        .setMethod(Method.POST)
                        .setName(name)
                        .setDescription(description)
                        .buildRequest()
                        .sendRequest();

        response
                .then()
                .assertThat()
                .spec(goodResponseSpecification());

        TrelloBoard board = TrelloBoardServiceObj.getBoard(response);

        return board;
    }

    public static void deleteBoard(TrelloBoard board) {
        TrelloBoardServiceObj.requestBuilder()
                .setMethod(Method.DELETE)
                .setBasePath(board.getId())
                .buildRequest()
                .sendRequest();
    }
}
