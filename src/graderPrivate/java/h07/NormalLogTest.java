package h07;

import h07.tree.Node;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class NormalLogTest extends AdvancedLoggerTest {

    @ParameterizedTest
    @ValueSource(longs = {0, 1, 2, 3, 4})
    public void testGenerateTree_time(long hourOffset) {

        LocalTime time = LocalTime.now();
        time = time.minusHours(hourOffset);

        try (MockedStatic<LocalTime> timeMock = mockStatic(LocalTime.class)) {
            timeMock.when(LocalTime::now).thenReturn(time);

            NormalLog logger = mock(NormalLog.class, CALLS_REAL_METHODS);
            logger.message = "Any kind of message";

            withMocks(() -> {
                Node generated = logger.generateTree();

                String actual = generated.evaluate();
                String expected = LocalTime.now() + ": ";

                assertTrue(
                    actual.startsWith(expected),
                    emptyContext(),
                    r -> "The returned string does not begin with the date. The returned string was " + actual
                );
            });
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, Integer.MAX_VALUE, Integer.MIN_VALUE})
    public void testGenerateTree_color(int level) {

        String color = switch (level) {
            case 0, 1 -> Log.ANSI_BLUE;
            case 2, 3 -> Log.ANSI_YELLOW;
            default -> Log.ANSI_RED;
        };


        NormalLog logger = mock(NormalLog.class, CALLS_REAL_METHODS);
        logger.message = "Any kind of message";
        logger.level = level;


        withMocks(() -> {
            Node generated = logger.generateTree();
            String actual = generated.evaluate().replaceAll("\n", "");
            String expected = Log.ANSI_ESCAPE + color + logger.message + Log.ANSI_ESCAPE + Log.ANSI_RESET;

            assertEquals(
                expected,
                actual.substring(actual.length() - expected.length()),
                emptyContext(),
                r -> "The returned string does not contain the correctly colored message. The returned string was "
                    + actual
            );
        });
    }

    @Test
    public void testGenerateTree_newLine() {

        NormalLog logger = mock(NormalLog.class, CALLS_REAL_METHODS);
        logger.message = "Any\nkind\nof\nmessage";

        withMocks(() -> {
            Node generated = logger.generateTree();

            String actual = generated.evaluate();
            String expected = logger.message.replace("\n", ";");

            assertTrue(
                actual.contains(expected),
                emptyContext(),
                r -> "The returned string does not have all new lines correctly replaced. The returned string was "
                    + actual
            );
        });
    }
}
