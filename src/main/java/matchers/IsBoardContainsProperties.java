package matchers;

import entities.TrelloBoard;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsBoardContainsProperties extends TypeSafeMatcher<TrelloBoard> {
    private String name;
    private String description;

    public IsBoardContainsProperties(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    protected boolean matchesSafely(TrelloBoard trelloBoard) {
        return trelloBoard.getName().equals(name)
                && trelloBoard.getDesc().equals(description);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(
                String.format("The board contains properties: name = '%s', description = '%s'",
                        name,
                        this.description));
    }

    public static Matcher<TrelloBoard> boardContainsProperties(String name, String description) {
        return new IsBoardContainsProperties(name, description);
    }
}
