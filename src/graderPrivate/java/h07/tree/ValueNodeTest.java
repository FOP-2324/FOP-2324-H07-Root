package h07.tree;

import h07.ClassReference;
import h07.H07Test;
import h07.MethodReference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockSettings;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static org.mockito.Mockito.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class ValueNodeTest extends H07Test {

    @Test
    public void testDefinition() {
        ClassReference.VALUE_NODE.assertCorrectlyDefined();
        MethodReference.VALUE_NODE_SET_VALUE_EXPRESSION.assertCorrectlyDefined();
    }

    @Test
    public void testEvaluateDefault() throws Throwable {

        Object node = ClassReference.VALUE_NODE.getLink().reflection().getConstructor().newInstance();

        String actual = MethodReference.NODE_EVALUATE.invoke(ValueNode.class, node);

        assertEquals("", actual, emptyContext(), r -> "evaluate() returns an incorrect Value if no new ValueExpression has been set.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "String One", "Value", ""})
    public void testSetValueExpression(String returnedValue) throws Throwable {
        MockSettings settings = getSettings();

        //ValueNode
        Object node = ClassReference.VALUE_NODE.getLink().reflection().getConstructor().newInstance();
        //ValueExpression
        Object expression = mock(ClassReference.VALUE_EXPRESSION.getLink().reflection(), settings);

        when(MethodReference.VALUE_EXPRESSION_GET.invoke(ValueNode.class, expression)).thenReturn(returnedValue);

        MethodReference.VALUE_NODE_SET_VALUE_EXPRESSION.invoke(ValueNode.class, node, expression);

        String actual = MethodReference.NODE_EVALUATE.invoke(ValueNode.class, node);

        assertEquals(returnedValue, actual, emptyContext(), r -> "evaluate() returns an incorrect Value if a new ValueExpression has been set.");
    }
}
