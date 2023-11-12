package h07.tree;

import h07.ClassReference;
import h07.H07Test;
import h07.MethodReference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.MockMakers;
import org.mockito.MockSettings;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.extension.TestCycleResolver;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import static org.mockito.Mockito.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class ConcatenationNodeTest extends H07Test {

    @Test
    public void testDefinition() {
        ClassReference.CONCATENATION_NODE.assertCorrectlyDefined();
        MethodReference.CONCATENATION_NODE_CONSTRUCTOR.assertCorrectlyDefined();
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "ConcatenationNodeTest.json")
    public void testEvaluate(JsonParameterSet params) throws Throwable {
        String expected = params.getString("expected");
        String leftNodeEvaluate = params.getString("left");
        String rightNodeEvaluate = params.getString("right");

        Context context = contextBuilder()
            .add("left Node", leftNodeEvaluate)
            .add("right Node", rightNodeEvaluate)
            .build();

        MockSettings settings = getSettings();

        //Node
        Object left = mock(ClassReference.NODE.getLink().reflection(), settings);
        when(MethodReference.NODE_EVALUATE.invoke(ConcatenationNode.class, left)).thenReturn(leftNodeEvaluate);

        //Node
        Object right = mock(ClassReference.NODE.getLink().reflection(), settings);
        when(MethodReference.NODE_EVALUATE.invoke(ConcatenationNode.class, right)).thenReturn(rightNodeEvaluate);

        //ConditionNode
        Object node = MethodReference.CONCATENATION_NODE_CONSTRUCTOR.invoke(null, left, right);

        String actual = MethodReference.NODE_EVALUATE.invoke(ConcatenationNode.class, node);

        verify(left, atLeastOnce());
        MethodReference.NODE_EVALUATE.invoke(ConcatenationNode.class, left);
        verify(right, atLeastOnce());
        MethodReference.NODE_EVALUATE.invoke(ConcatenationNode.class, right);

        assertEquals(expected, actual, context, r -> "The method evaluate() of class ConcatenationNode returns incorrect values.");
    }
}
