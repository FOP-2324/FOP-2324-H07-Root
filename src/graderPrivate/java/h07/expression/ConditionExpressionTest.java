package h07.expression;

import h07.ClassReference;
import h07.MethodReference;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

@TestForSubmission
public class ConditionExpressionTest {

    @Test
    public void testDefinition() {
        ClassReference.CONDITION_EXPRESSION.assertCorrectlyDefined();
        MethodReference.CONDITION_EXPRESSION_CHECK.assertCorrectlyDefined();
    }
}
