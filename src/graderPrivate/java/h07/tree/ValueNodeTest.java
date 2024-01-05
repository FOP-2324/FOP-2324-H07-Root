package h07.tree;

import h07.H07Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockMakers;
import org.mockito.MockSettings;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.Arrays;
import java.util.stream.Collectors;

import static h07.ClassReference.VALUE_EXPRESSION;
import static h07.ClassReference.VALUE_NODE;
import static h07.FieldReference.VALUE_NODE_EXPRESSION;
import static h07.MethodReference.*;
import static org.mockito.Mockito.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;

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
    public void testEvaluateDefault() throws Throwable {

        Object node = VALUE_NODE.getLink().reflection().getConstructor().newInstance();

        String actual = NODE_EVALUATE.invoke(ValueNode.class, node);

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
        Object node = mock(VALUE_NODE.getLink().reflection(), withSettings().mockMaker(MockMakers.INLINE).defaultAnswer(CALLS_REAL_METHODS));

        //ValueExpression
        Object expression = mock(ValueNodeTest.expression);
        when(VALUE_EXPRESSION_GET.invoke(ValueNodeTest.expression, expression)).thenReturn(testString);

        VALUE_NODE_EXPRESSION.getLink().set(node, expression);

        String actual = NODE_EVALUATE.invoke(ValueNode.class, node);

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
        when(VALUE_EXPRESSION_GET.invoke(ValueNode.class, expression)).thenReturn(returnedValue);

        VALUE_NODE_SET_VALUE_EXPRESSION.invoke(ValueNode.class, node, expression);

        VALUE_NODE_EXPRESSION.assertStoredValue(node, expression, emptyContext());
    }
}
