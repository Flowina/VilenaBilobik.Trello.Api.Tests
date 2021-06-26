import actions.TrelloBoardActions;
import com.thedeanda.lorem.LoremIpsum;
import core.DataProvidersForTrelloBoard;
import core.TrelloBoardServiceObj;
import entities.TrelloBoard;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

import static constants.TestData.*;
import static core.TrelloApi.*;
import static matchers.IsBoardContainsProperties.boardContainsProperties;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TrelloBoardApiTests {
    private List<TrelloBoard> testBoards = new LinkedList<>();

    @AfterTest(alwaysRun = true)
    public void afterTest() {
        while (testBoards.size() > 0) {
            TrelloBoard board = testBoards.get(0);
            if (board != null) {
                try {
                    TrelloBoardActions.deleteBoard(board);
                } catch (Exception e) {
                    System.out.println("\u001B[31m" + "TrelloBoardApiTests. Clean test data ERROR. BoardId = " +
                            board.getId() + "\u001B[0m");
                    e.printStackTrace();
                } finally {
                    testBoards.remove(board);
                }
            }
        }
    }

    @Test(dataProviderClass = DataProvidersForTrelloBoard.class,
            dataProvider = "boardPropertiesProvider")
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

        TrelloBoard board = TrelloBoardServiceObj.getBoard(response);

        assertThat("Response should contain id tag",
                board,
                hasProperty("id"));

        assertThat(board, is(boardContainsProperties(name, description)));

        testBoards.add(board);
    }

    @Test(dataProviderClass = DataProvidersForTrelloBoard.class,
            dataProvider = "boardsProvider")
    public void getBoard(TrelloBoard existedBoard) {
        testBoards.add(existedBoard);
        Response response =
                TrelloBoardServiceObj.requestBuilder()
                        .setMethod(Method.GET)
                        .setBasePath(existedBoard.getId())
                        .buildRequest()
                        .sendRequest();
        response
                .then()
                .assertThat()
                .spec(goodResponseSpecification());

        TrelloBoard board = TrelloBoardServiceObj.getBoard(response);
        assertThat(board, is(
                boardContainsProperties(
                        existedBoard.getName(),
                        existedBoard.getDesc())
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

    @Test(dataProviderClass = DataProvidersForTrelloBoard.class,
            dataProvider = "boardsProvider")
    public void updateExistingBoard(TrelloBoard existedBoard) {
        testBoards.add(existedBoard);
        String newName = LoremIpsum.getInstance().getTitle(3);
        String newDescription = LoremIpsum.getInstance().getParagraphs(1, 2);

        Response response =
                TrelloBoardServiceObj.requestBuilder()
                        .setMethod(Method.PUT)
                        .setBasePath(existedBoard.getId())
                        .setName(newName)
                        .setDescription(newDescription)
                        .buildRequest()
                        .sendRequest();
        response
                .then()
                .assertThat()
                .spec(goodResponseSpecification());

        TrelloBoard board = TrelloBoardServiceObj.getBoard(response);
        assertThat(board, is(
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

    @Test(dataProviderClass = DataProvidersForTrelloBoard.class,
            dataProvider = "boardsProvider")
    public void deleteExistingBoards(TrelloBoard board) {
        Response response =
                TrelloBoardServiceObj.requestBuilder()
                        .setMethod(Method.DELETE)
                        .setBasePath(board.getId())
                        .buildRequest()
                        .sendRequest();
        response
                .then()
                .assertThat()
                .spec(goodResponseSpecification());
    }
}
