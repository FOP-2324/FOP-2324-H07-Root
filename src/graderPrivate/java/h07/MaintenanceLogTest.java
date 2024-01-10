package h07;

import h07.tree.Node;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class MaintenanceLogTest extends AdvancedLoggerTest {

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, 0, 1, 2, 4, 5, 6, 7, Integer.MAX_VALUE})
    public void testGenerateTree_Level_unLogged(int level) {
        MaintenanceLog logger = mock(MaintenanceLog.class, CALLS_REAL_METHODS);
        logger.message = "Any\nkind\nof\nmessage";
        logger.level = level;

        withMocks(() -> {
            Object generated = logger.generateTree();

            String actual = null;
            try {
                actual = MethodReference.NODE_EVALUATE.invoke(generated.getClass(), generated);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }

            String finalActual = actual;
            assertEquals(
                "",
                actual,
                emptyContext(),
                r -> "The returned string does not have a correct formatting. The returned string was "
                    + finalActual
            );
        });
    }

    @Test
    public void testGenerateTree_Level_Logged() {
        MaintenanceLog logger = mock(MaintenanceLog.class, CALLS_REAL_METHODS);
        logger.message = "Any\nkind\nof\nmessage";
        logger.level = 3;

        withMocks(() -> {
            Object generated = logger.generateTree();

            String actual = null;
            try {
                actual = MethodReference.NODE_EVALUATE.invoke(generated.getClass(), generated);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }

            String finalActual = actual;
            assertNotEquals(
                "",
                actual,
                emptyContext(),
                r -> "The returned string does not have a correct formatting. The returned string was "
                    + finalActual
            );
        });
    }

    @Test
    public void testGenerateTree_Formatting() {
        MaintenanceLog logger = mock(MaintenanceLog.class, CALLS_REAL_METHODS);
        logger.message = "Any\nkind\nof\nmessage";
        logger.level = 3;

        withMocks(() -> {
            Object generated = logger.generateTree();

            String actual = null;
            try {
                actual = MethodReference.NODE_EVALUATE.invoke(generated.getClass(), generated);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
            String expected = logger.message + "\n";

            assertEquals(
                expected,
                actual,
                emptyContext(),
                r -> "The returned string does not have a correct formatting."
            );
        });
    }
}
