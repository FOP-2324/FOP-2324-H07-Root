package h07.tree;

import h07.H07Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockMakers;
import org.mockito.MockSettings;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static h07.ClassReference.VALUE_EXPRESSION;
import static h07.ClassReference.VALUE_NODE;
import static h07.FieldReference.VALUE_NODE_EXPRESSION;
import static h07.MethodReference.*;
import static org.mockito.Mockito.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class ValueNodeTest extends H07Test {

    private static Class<?> expression = null;

    @BeforeEach
    public void getExpression() {
        if (VALUE_EXPRESSION.isDefined()) {
            expression = VALUE_EXPRESSION.getLink().reflection();
        } else {
            VALUE_NODE_EXPRESSION.assertDefined();
            expression = VALUE_NODE_EXPRESSION.getLink().staticType().reflection();
            VALUE_EXPRESSION_GET.assertDefined(expression);
        }
    }

    @Test
    public void testDefinition() {
        VALUE_NODE.assertCorrectlyDefined();
        VALUE_NODE_SET_VALUE_EXPRESSION.assertCorrectlyDefined();
        VALUE_NODE_EXPRESSION.assertCorrectlyDefined();
    }

    @Test
    public void testNaming() {
        VALUE_NODE.assertNamedCorrectly();
        VALUE_NODE_SET_VALUE_EXPRESSION.assertNamedCorrectly();
        VALUE_NODE_EXPRESSION.assertNamedCorrectly();
    }

    @Test
    public void testPackage() {
        VALUE_NODE.assertDefinedInCorrectPackage();
    }

    @Test
    public void testEvaluateDefault() throws Throwable {

        Object node = VALUE_NODE.getLink().reflection().getConstructor().newInstance();

        Object defaultExpression = VALUE_NODE_EXPRESSION.getLink().get(node);

        assertNotNull(defaultExpression, emptyContext(), r -> "ValueNode does not create a default ValueExpression");

        String actual = NODE_EVALUATE.invoke(VALUE_NODE.getLink().reflection(), node);

        assertEquals(
            "",
            actual,
            emptyContext(),
            r -> "evaluate() returns an incorrect Value if no new ValueExpression has been set."
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "testString", "non empty string", "CaPitaLiZeD"})
    public void testEvaluate(String testString) throws Throwable {
        //ValueNode
        Object node = mock(
            VALUE_NODE.getLink().reflection(),
            withSettings().mockMaker(MockMakers.INLINE).defaultAnswer(CALLS_REAL_METHODS)
        );

        //ValueExpression
        Object expression = mock(ValueNodeTest.expression, CALLS_REAL_METHODS);
        when(VALUE_EXPRESSION_GET.invokeBestEffort(ValueNodeTest.expression, expression)).thenReturn(testString);

        VALUE_NODE_EXPRESSION.getLink().set(node, expression);

        String actual = NODE_EVALUATE.invoke(VALUE_NODE.getLink().reflection(), node);

        assertEquals(
            testString,
            actual,
            emptyContext(),
            r -> "evaluate() returns an incorrect Value if ValueExpression has been set."
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "String One", "Value", ""})
    public void testSetValueExpression(String returnedValue) throws Throwable {
        MockSettings settings = getSettings();

        //ValueNode
        Object node = mock(VALUE_NODE.getLink().reflection(), CALLS_REAL_METHODS);

        //ValueExpression
        Object expression = mock(ValueNodeTest.expression, settings);
        when(VALUE_EXPRESSION_GET.invokeBestEffort(VALUE_EXPRESSION.getLink().reflection(), expression)).thenReturn(returnedValue);

        VALUE_NODE_SET_VALUE_EXPRESSION.invoke(VALUE_NODE.getLink().reflection(), node, expression);

        VALUE_NODE_EXPRESSION.assertStoredValue(node, expression, emptyContext());
    }
}
