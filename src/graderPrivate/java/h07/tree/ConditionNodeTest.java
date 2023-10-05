package h07.tree;

import h07.ClassReference;
import h07.MethodReference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.MockMakers;
import org.mockito.MockSettings;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.extension.TestCycleResolver;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import static org.mockito.Mockito.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class ConditionNodeTest {

    @Test
    public void testDefinition() {
        ClassReference.CONDITION_NODE.isCorrectlyDefined();
        MethodReference.CONDITION_NODE_CONSTRUCTOR.isCorrectlyDefined();
        MethodReference.CONDITION_NODE_SET_CONDITION_EXPRESSION.isCorrectlyDefined();
    }

    @Test
    public void testEvaluateDefault() throws Exception {
        //TODO
        String expected = "expected string";

        Thread.currentThread().setContextClassLoader((ClassLoader) TestCycleResolver.getTestCycle().getClassLoader());
        MockSettings settings = withSettings().mockMaker(MockMakers.PROXY);

        //Node
        Object objectiveNode = mock(ClassReference.NODE.getLink().reflection(), settings);
        when(MethodReference.NODE_EVALUATE.invoke(objectiveNode)).thenReturn("nullnull");
        //Node
        Object trueNode = mock(ClassReference.NODE.getLink().reflection(), settings);
        when(MethodReference.NODE_EVALUATE.invoke(trueNode)).thenReturn("null");
        //Node
        Object falseNode = mock(ClassReference.NODE.getLink().reflection(), settings);
        when(MethodReference.NODE_EVALUATE.invoke(falseNode)).thenReturn(expected);

        Node toTest = MethodReference.CONDITION_NODE_CONSTRUCTOR.invoke(null, objectiveNode, trueNode, falseNode);

        String actual = MethodReference.NODE_EVALUATE.invoke(toTest);

        assertEquals(expected, actual, emptyContext(), r -> "Evaluate liefert nicht den erwarteten Wert zurück, wenn keine neue ConditionExpression gesetzt wurde.");
    }

    @ParameterizedTest
    public void testEvaluateChangedExpression() throws Exception {
        String positiveString = "";
        String negativeString = "";
        boolean expected = false;

        Context context = contextBuilder()
            .add("trueNode", positiveString)
            .add("falseNode", negativeString)
            .add("objective return value", expected)
            .build();

        Thread.currentThread().setContextClassLoader((ClassLoader) TestCycleResolver.getTestCycle().getClassLoader());
        MockSettings settings = withSettings().mockMaker(MockMakers.PROXY);

        //Node
        Object objectiveNode = mock(ClassReference.NODE.getLink().reflection(), settings);
        when(MethodReference.NODE_EVALUATE.invoke(objectiveNode)).thenReturn("nullnull");
        //Node
        Object trueNode = mock(ClassReference.NODE.getLink().reflection(), settings);
        when(MethodReference.NODE_EVALUATE.invoke(trueNode)).thenReturn(positiveString);
        //Node
        Object falseNode = mock(ClassReference.NODE.getLink().reflection(), settings);
        when(MethodReference.NODE_EVALUATE.invoke(falseNode)).thenReturn(negativeString);

        //Node
        Object toTest = MethodReference.CONDITION_NODE_CONSTRUCTOR.invoke(null, objectiveNode, trueNode, falseNode);
        //ConditionExpression
        Object conditionExpression = mock(ClassReference.CONDITION_EXPRESSION.getLink().reflection(), settings);
        when(MethodReference.CONDITION_EXPRESSION_CHECK.invoke(conditionExpression)).thenReturn(expected);
        MethodReference.CONDITION_NODE_SET_CONDITION_EXPRESSION.invoke(toTest, conditionExpression);

        String actual = MethodReference.NODE_EVALUATE.invoke(toTest);

        assertEquals(expected ? positiveString : negativeString, actual, context, r -> "Evaluate liefert nicht den erwarteten Wert zurück, wenn eine neue ConditionExpression gesetzt wurde.");
    }
}
