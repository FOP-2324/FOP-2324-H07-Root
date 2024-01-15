package h07.expression;

import h07.ClassReference;
import h07.MethodReference;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

@TestForSubmission
public class MapExpressionTest {

    @Test
    public void testDefinition() {
        ClassReference.MAP_EXPRESSION.assertCorrectlyDefined();
        MethodReference.MAP_EXPRESSION_MAP.assertCorrectlyDefined();
    }

    @Test
    public void testNaming() {
        ClassReference.MAP_EXPRESSION.assertNamedCorrectly();
        MethodReference.MAP_EXPRESSION_MAP.assertNamedCorrectly();
    }

    @Test
    public void testPackage() {
        ClassReference.MAP_EXPRESSION.assertDefinedInCorrectPackage();
    }
}
