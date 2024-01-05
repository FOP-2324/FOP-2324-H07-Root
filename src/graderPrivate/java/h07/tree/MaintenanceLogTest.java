package h07.tree;

import h07.AdvancedLoggerTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

@TestForSubmission
public class MaintenanceLogTest extends AdvancedLoggerTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, Integer.MAX_VALUE, Integer.MIN_VALUE})
    public void testGenerateTree_levelFilter(int level){

    }
}
