package h07.expression;

import h07.ClassReference;
import h07.MethodReference;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

@TestForSubmission
public class ValueExpressionTest {

    @Test
    public void testDefinition() {
        ClassReference.VALUE_EXPRESSION.assertCorrectlyDefined();
        MethodReference.VALUE_EXPRESSION_GET.assertCorrectlyDefined();
    }
}
