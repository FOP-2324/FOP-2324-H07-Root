package h07.tree;

import h07.ClassReference;
import h07.MethodReference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockMakers;
import org.mockito.MockSettings;
import org.mockito.invocation.InvocationOnMock;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.extension.TestCycleResolver;

import java.lang.reflect.InvocationTargetException;
import static org.mockito.Mockito.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class ValueNodeTest {

    @Test
    public void testDefinition() {
        ClassReference.VALUE_NODE.isCorrectlyDefined();
        MethodReference.VALUE_NODE_SET_VALUE_EXPRESSION.isCorrectlyDefined();
    }

    @Test
    public void testEvaluateDefault() throws Exception {

        Object node = ClassReference.VALUE_NODE.getLink().reflection().getConstructor().newInstance();

        String actual = MethodReference.NODE_EVALUATE.invoke(node);

        assertEquals("", actual, emptyContext(), r -> "evaluate() liefert den falschen Wert zurück, bevor eine neue ValueExpression gesetzt wird.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "String One", "Value", ""})
    public void testSetValueExpression(String returnedValue) throws Exception {

        Thread.currentThread().setContextClassLoader((ClassLoader) TestCycleResolver.getTestCycle().getClassLoader());
        MockSettings settings = withSettings().mockMaker(MockMakers.PROXY);

        //ValueNode
        Object node = ClassReference.VALUE_NODE.getLink().reflection().getConstructor().newInstance();
        //ValueExpression
        Object expression = mock(ClassReference.VALUE_EXPRESSION.getLink().reflection(), settings);

        when(MethodReference.VALUE_EXPRESSION_GET.invoke(expression)).thenReturn(returnedValue);

        MethodReference.VALUE_NODE_SET_VALUE_EXPRESSION.invoke(node, expression);

        String actual = MethodReference.NODE_EVALUATE.invoke(node);

        assertEquals(returnedValue, actual, emptyContext(), r -> "evaluate() liefert den falschen Wert zurück, nachdem die ValueExpression neu gesetzt wurde.");
    }
}
