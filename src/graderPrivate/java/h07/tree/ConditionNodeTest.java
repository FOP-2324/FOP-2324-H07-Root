package h07.tree;

import h07.ClassReference;
import h07.H07Test;
import h07.MethodReference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockSettings;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import static h07.ClassReference.CONDITION_EXPRESSION;
import static h07.ClassReference.NODE;
import static h07.MethodReference.*;
import static org.mockito.Mockito.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class ConditionNodeTest extends H07Test {

    @Test
    public void testDefinition() {
        ClassReference.CONDITION_NODE.assertCorrectlyDefined();
        CONDITION_NODE_CONSTRUCTOR.assertCorrectlyDefined();
        CONDITION_NODE_SET_CONDITION_EXPRESSION.assertCorrectlyDefined();
    }

    @ParameterizedTest
    @ValueSource(strings = {"expected", "false", "value", "stringy"})
    public void testEvaluateDefault(String expected) throws Throwable {
        MockSettings settings = getSettings();

        NODE.assertDefined();
        NODE_EVALUATE.assertDefined(ConditionNode.class);
        CONDITION_NODE_CONSTRUCTOR.assertDefined(ConditionNode.class);

        //Node
        Object objectiveNode = mock(NODE.getLink().reflection(), settings);
        when(NODE_EVALUATE.invoke(ConditionNode.class, objectiveNode)).thenReturn("nullnull");
        //Node
        Object trueNode = mock(NODE.getLink().reflection(), settings);
        when(NODE_EVALUATE.invoke(ConditionNode.class, trueNode)).thenReturn("null");
        //Node
        Object falseNode = mock(NODE.getLink().reflection(), settings);
        when(NODE_EVALUATE.invoke(ConditionNode.class, falseNode)).thenReturn(expected);

        Node toTest = CONDITION_NODE_CONSTRUCTOR.invoke(null, objectiveNode, trueNode, falseNode);

        String actual = NODE_EVALUATE.invoke(ConditionNode.class, toTest);

        assertEquals(expected, actual, emptyContext(), r -> "evaluate() returns incorrect values if no new ConditionExpression has been set.");
    }

    @ParameterizedTest
    @JsonParameterSetTest("ConditionNodeTest.json")
    public void testEvaluateChangedExpression(JsonParameterSet params) throws Throwable {
        String positiveString = params.getString("trueNode");
        String negativeString = params.getString("falseNode");
        boolean expected = params.getBoolean("expected");

        Context context = contextBuilder()
            .add("trueNode", positiveString)
            .add("falseNode", negativeString)
            .add("objective return value", expected)
            .build();

        MockSettings settings = getSettings();

        //Node
        Object objectiveNode = mock(NODE.getLink().reflection(), settings);
        when(NODE_EVALUATE.invoke(ConditionNode.class, objectiveNode)).thenReturn("nullnull");
        //Node
        Object trueNode = mock(NODE.getLink().reflection(), settings);
        when(NODE_EVALUATE.invoke(ConditionNode.class, trueNode)).thenReturn(positiveString);
        //Node
        Object falseNode = mock(NODE.getLink().reflection(), settings);
        when(NODE_EVALUATE.invoke(ConditionNode.class, falseNode)).thenReturn(negativeString);

        //ConditionNode
        Object toTest = CONDITION_NODE_CONSTRUCTOR.invoke(null, objectiveNode, trueNode, falseNode);
        //ConditionExpression
        Object conditionExpression = mock(CONDITION_EXPRESSION.getLink().reflection(), settings);
        when(CONDITION_EXPRESSION_CHECK.invoke(ConditionNode.class, conditionExpression, anyString())).thenReturn(expected);
        CONDITION_NODE_SET_CONDITION_EXPRESSION.invoke(ConditionNode.class, toTest, conditionExpression);

        String actual = NODE_EVALUATE.invoke(ConditionNode.class, toTest);

        assertEquals(expected ? positiveString : negativeString, actual, context, r -> "evaluate() returns incorrect values if a new ConditionExpression has been set.");
    }
}
