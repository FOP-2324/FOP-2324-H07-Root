package h07.tree;

import h07.FieldReference;
import h07.H07Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockSettings;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import static h07.ClassReference.*;
import static h07.FieldReference.CONDITION_NODE_CONDITION_EXPRESSION;
import static h07.MethodReference.*;
import static org.mockito.Mockito.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class ConditionNodeTest extends H07Test {

    @Test
    public void testDefinition() {
        CONDITION_NODE.assertCorrectlyDefined();
        CONDITION_NODE_CONSTRUCTOR.assertCorrectlyDefined();
        CONDITION_NODE_SET_CONDITION_EXPRESSION.assertCorrectlyDefined();
    }

    @Test
    public void testNaming() {
        CONDITION_NODE.assertNamedCorrectly();
        CONDITION_NODE_CONSTRUCTOR.assertNamedCorrectly();
        CONDITION_NODE_SET_CONDITION_EXPRESSION.assertNamedCorrectly();
        CONDITION_NODE_CONDITION_EXPRESSION.assertNamedCorrectly();
    }

    @ParameterizedTest
    @ValueSource(strings = {"expected", "false", "value", "stringy"})
    public void testConstructor(String input) throws Throwable {
        CONDITION_NODE.assertDefined();

        //Node
        Object objectiveNode = mock(NODE.getLink().reflection());
        //Node
        Object trueNode = mock(NODE.getLink().reflection());
        //Node
        Object falseNode = mock(NODE.getLink().reflection());
        //Node
        Object toTest = CONDITION_NODE_CONSTRUCTOR.invoke(
            CONDITION_NODE.getLink().reflection(),
            null,
            objectiveNode,
            trueNode,
            falseNode
        );

        FieldReference.CONDITION_NODE_OBJECTIVE_NODE.assertStoredValue(toTest, objectiveNode, emptyContext());
        FieldReference.CONDITION_NODE_TRUE_NODE.assertStoredValue(toTest, trueNode, emptyContext());
        FieldReference.CONDITION_NODE_FALSE_NODE.assertStoredValue(toTest, falseNode, emptyContext());

        boolean actual = CONDITION_EXPRESSION_CHECK.invoke(
            CONDITION_EXPRESSION.getLink().reflection(),
            CONDITION_NODE_CONDITION_EXPRESSION.getLink().get(toTest),
            input
        );

        assertFalse(
            actual,
            emptyContext(),
            r -> "The ConditionExpression set in Constructor does not return the correct values"
        );
    }

    @Test
    public void testSetConditionExpression() throws Throwable {
        CONDITION_NODE.assertDefined();
        MockSettings settings = getSettings();
        //ConditionExpression
        Object conditionExpression = mock(CONDITION_EXPRESSION.getLink().reflection(), settings);
        //ConditionNode
        Object toTest = mock(CONDITION_NODE.getLink().reflection(), CALLS_REAL_METHODS);

        CONDITION_NODE_SET_CONDITION_EXPRESSION.invoke(
            CONDITION_NODE.getLink().reflection(),
            toTest,
            conditionExpression
        );

        CONDITION_NODE_CONDITION_EXPRESSION.assertStoredValue(
            toTest,
            conditionExpression,
            emptyContext()
        );
    }

    @ParameterizedTest
    @JsonParameterSetTest("ConditionNodeTest.json")
    public void testEvaluate(JsonParameterSet params) throws Throwable {
        String positiveString = params.getString("trueNode");
        String negativeString = params.getString("falseNode");
        boolean expected = params.getBoolean("expected");

        Context context = contextBuilder()
            .add("trueNode", positiveString)
            .add("falseNode", negativeString)
            .add("objective return value", expected)
            .build();

        //Node
        Object objectiveNode = mock(NODE.getLink().reflection());
        when(NODE_EVALUATE.invoke(CONDITION_NODE.getLink().reflection(), objectiveNode)).thenReturn("nullnull");
        //Node
        Object trueNode = mock(NODE.getLink().reflection());
        when(NODE_EVALUATE.invoke(CONDITION_NODE.getLink().reflection(), trueNode)).thenReturn(positiveString);
        //Node
        Object falseNode = mock(NODE.getLink().reflection());
        when(NODE_EVALUATE.invoke(CONDITION_NODE.getLink().reflection(), falseNode)).thenReturn(negativeString);
        //ConditionExpression
        Object conditionExpression = mock(CONDITION_EXPRESSION.getLink().reflection());
        when(CONDITION_EXPRESSION_CHECK.invoke(
            CONDITION_NODE.getLink().reflection(),
            conditionExpression,
            anyString()
        )).thenReturn(expected);

        //ConditionNode
        Object toTest = mock(CONDITION_NODE.getLink().reflection(), CALLS_REAL_METHODS);

        FieldReference.CONDITION_NODE_FALSE_NODE.getLink().set(toTest, falseNode);
        FieldReference.CONDITION_NODE_TRUE_NODE.getLink().set(toTest, trueNode);
        FieldReference.CONDITION_NODE_OBJECTIVE_NODE.getLink().set(toTest, objectiveNode);
        CONDITION_NODE_CONDITION_EXPRESSION.getLink().set(toTest, conditionExpression);

        String actual = NODE_EVALUATE.invoke(CONDITION_NODE.getLink().reflection(), toTest);

        assertEquals(
            expected ? positiveString : negativeString,
            actual,
            context,
            r -> "evaluate() returns incorrect values if a new ConditionExpression has been set."
        );
    }
}
