import com.thedeanda.lorem.LoremIpsum;
import core.DataProvidersForTrelloBoard;
import core.TrelloBoardServiceObj;
import entities.TrelloBoard;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static constants.TestData.*;
import static core.TrelloApi.*;
import static core.TrelloBoardServiceObj.getAnswer;
import static matchers.IsBoardContainsProperties.boardContainsProperties;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TrelloBoardApiTests {
    private Map<String, TrelloBoard> boards = new HashMap<>();

    @BeforeTest()
    public void beforeTest() {
        boards.clear();
    }

    @BeforeMethod()
    public void afterTest() {

    }

    @Test(dataProviderClass = DataProvidersForTrelloBoard.class,
            dataProvider = "boardsProvider",
            groups = "cru")
    public void createBoard(String name, String description) {
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

        TrelloBoard answer = getAnswer(response);

        //Save Boards for next tests
        boards.put(answer.getId(), answer);

        assertThat("Response should contain id tag",
                answer,
                hasProperty("id"));

        assertThat(answer, is(boardContainsProperties(name, description)));
    }

    @Test(dependsOnMethods = "createBoard", groups = "cru")
    public void getBoard() {
        TrelloBoard board = getTrelloBoard();
        Response response =
                TrelloBoardServiceObj.requestBuilder()
                        .setMethod(Method.GET)
                        .setBasePath(board.getId())
                        .buildRequest()
                        .sendRequest();
        response
                .then()
                .assertThat()
                .spec(goodResponseSpecification());

        TrelloBoard answer = getAnswer(response);
        assertThat(answer, is(
                boardContainsProperties(
                        board.getName(),
                        board.getDesc())
                )
        );
    }

    @Test(dataProviderClass = DataProvidersForTrelloBoard.class,
            dataProvider = "notExistentBoardIds")
    public void getNonExistentBoard(String boardId) {
        Response response =
                TrelloBoardServiceObj.requestBuilder()
                        .setMethod(Method.GET)
                        .setBasePath(boardId)
                        .buildRequest()
                        .sendRequest();
        response
                .then()
                .assertThat()
                .spec(notFoundResponseSpecification());
    }

    @Test(dataProviderClass = DataProvidersForTrelloBoard.class,
            dataProvider = "notExistentBoardIds")
    public void deleteNonExistentBoard(String boardId) {
        Response response =
                TrelloBoardServiceObj.requestBuilder()
                        .setMethod(Method.DELETE)
                        .setBasePath(boardId)
                        .buildRequest()
                        .sendRequest();
        response
                .then()
                .assertThat()
                .spec(notFoundResponseSpecification());
    }

    @Test(dataProviderClass = DataProvidersForTrelloBoard.class,
            dataProvider = "invalidId")
    public void getByInvalidId(String invalidId) {
        Response response =
                TrelloBoardServiceObj.requestBuilder()
                        .setMethod(Method.GET)
                        .setBasePath(invalidId)
                        .buildRequest()
                        .sendRequest();
        response
                .then()
                .assertThat()
                .spec(badResponseSpecification())
                .body(equalToIgnoringCase(INVALID_ID_RESPONSE_TEXT));
    }

    @Test(dataProviderClass = DataProvidersForTrelloBoard.class,
            dataProvider = "longNames")
    public void createWithInvalidLongName(String name) {
        Response response =
                TrelloBoardServiceObj.requestBuilder()
                        .setMethod(Method.POST)
                        .setName(name)
                        .buildRequest()
                        .sendRequest();

        response
                .then()
                .assertThat()
                .spec(badResponseSpecification())
                .body(equalToIgnoringCase(INVALID_NAME_RESPONSE_TEXT));
    }

    @Test()
    public void createWithInvalidEmptyName() {
        Response response =
                TrelloBoardServiceObj.requestBuilder()
                        .setMethod(Method.POST)
                        .setName("")
                        .buildRequest()
                        .sendRequest();

        response
                .then()
                .assertThat()
                .spec(badResponseSpecification())
                .body(equalToIgnoringCase(INVALID_NAME_RESPONSE_TEXT));
    }

    @Test(dependsOnMethods = "createBoard", groups = "cru")
    public void updateExistingBoard() {
        TrelloBoard board = getTrelloBoard();
        String newName = LoremIpsum.getInstance().getTitle(3);
        String newDescription = LoremIpsum.getInstance().getParagraphs(1, 2);

        Response response =
                TrelloBoardServiceObj.requestBuilder()
                        .setMethod(Method.PUT)
                        .setBasePath(board.getId())
                        .setName(newName)
                        .setDescription(newDescription)
                        .buildRequest()
                        .sendRequest();
        response
                .then()
                .assertThat()
                .spec(goodResponseSpecification());

        TrelloBoard answer = getAnswer(response);
        assertThat(answer, is(
                boardContainsProperties(
                        newName,
                        newDescription)
                )
        );
    }

    @Test(dataProviderClass = DataProvidersForTrelloBoard.class,
            dataProvider = "notExistentBoardIds")
    public void updateNonExistentBoard(String boardId) {
        Response response =
                TrelloBoardServiceObj.requestBuilder()
                        .setMethod(Method.PUT)
                        .setBasePath(boardId)
                        .setName("foo")
                        .buildRequest()
                        .sendRequest();
        response
                .then()
                .assertThat()
                .spec(notFoundResponseSpecification())
                .body(equalToIgnoringCase(RESOURCE_NOT_FOUND_RESPONSE_TEXT));
    }

    @Test(dependsOnGroups = {"cru"})
    public void deleteExistingBoards() {
        for (Map.Entry<String, TrelloBoard> entry : boards.entrySet()) {
            Response response =
                    TrelloBoardServiceObj.requestBuilder()
                            .setMethod(Method.DELETE)
                            .setBasePath(entry.getKey())
                            .buildRequest()
                            .sendRequest();
            response
                    .then()
                    .assertThat()
                    .spec(goodResponseSpecification());
        }
    }

    private TrelloBoard getTrelloBoard() {
        return boards.entrySet().stream().findFirst().get().getValue();
    }
}
