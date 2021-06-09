package core;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.testng.annotations.DataProvider;

public class DataProvidersForTrelloBoard {
    Lorem lorem = LoremIpsum.getInstance();

    @DataProvider
    public Object[][] boardsProvider() {
        // name, description
        return new Object[][]{
                { lorem.getTitle(2, 4), lorem.getParagraphs(1, 1) },
        };
    }

    @DataProvider
    public Object[][] notExistentBoardIds() {
        return new Object[][]{
                {"60c13ef3ed1b5867b6773047"}
        };
    }

    @DataProvider
    public Object[][] longNames() {
        // allowed max length = 16384
        int length = 16384 + 10;
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length) {
            sb.append(lorem.getTitle(500));
        }
        return new Object[][]{
            { sb.substring(0, length) }
        };
    }

    @DataProvider
    public Object[][] invalidId() {
        //shouldn't match  ^[0-9a-fA-F]{24}$
        return new Object[][]{
                { "z0c13ef3ed1b5867b6773047" }
        };
    }
}
