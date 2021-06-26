package matchers;

import entities.TrelloBoard;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

public class IsBoardContainsProperties extends TypeSafeMatcher<TrelloBoard> {
    Map<String, Object> properties;
    List<String> errors = new LinkedList<>();

    public IsBoardContainsProperties(String name, String description) {
        this(new HashMap<>());
        properties.put("name", name);
        properties.put("desc", description);
    }
    public IsBoardContainsProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
    @Override
    protected boolean matchesSafely(TrelloBoard trelloBoard) {
        for (Map.Entry property : properties.entrySet()) {
            if (!hasProperty((String) property.getKey(), equalTo(property.getValue()))
                    .matches(trelloBoard)) {
                errors.add(String.format("Property \"%s\"%nExpected = \"%s\"", property.getKey(), property.getValue()));
            }
        }
        return errors.size() == 0;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("The board properties match errors:\n");
        for (String error : errors) {
            description.appendText(String.format("%s%n", error));
        }
    }

    public static Matcher<TrelloBoard> boardContainsProperties(Map<String, Object> properties) {
        return new IsBoardContainsProperties(properties);
    }

    public static Matcher<TrelloBoard> boardContainsProperties(String name, String description) {
        return new IsBoardContainsProperties(name, description);
    }
}
