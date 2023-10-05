package h07.tree;

import h07.ClassReference;
import h07.MethodReference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.MockMakers;
import org.mockito.MockSettings;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.extension.TestCycleResolver;
import org.sourcegrade.jagr.launcher.env.Jagr;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import static org.mockito.Mockito.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class ConcatenationNodeTest {

    @Test
    public void testDefinition() {
        ClassReference.CONCATENATION_NODE.isCorrectlyDefined();
        MethodReference.CONCATENATION_NODE_CONSTRUCTOR.isCorrectlyDefined();
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "ConcatenationNodeTest.json")
    public void testEvaluate(JsonParameterSet params) throws Exception {
        String expected = params.getString("expected");
        String leftNodeEvaluate = params.getString("left");
        String rightNodeEvaluate = params.getString("right");

        Context context = contextBuilder()
            .add("left Node", leftNodeEvaluate)
            .add("right Node", rightNodeEvaluate)
            .build();

        Thread.currentThread().setContextClassLoader((ClassLoader) TestCycleResolver.getTestCycle().getClassLoader());
        MockSettings settings = withSettings().mockMaker(MockMakers.PROXY);

        //Node
        Object left = mock(ClassReference.NODE.getLink().reflection(), settings);
        when(MethodReference.NODE_EVALUATE.invoke(left)).thenReturn(leftNodeEvaluate);

        //Node
        Object right = mock(ClassReference.NODE.getLink().reflection(), settings);
        when(MethodReference.NODE_EVALUATE.invoke(right)).thenReturn(rightNodeEvaluate);

        //ConditionNode
        Object node = MethodReference.CONCATENATION_NODE_CONSTRUCTOR.invoke(null, left, right);

        String actual = MethodReference.NODE_EVALUATE.invoke(node);

        verify(left, atLeastOnce());
        MethodReference.NODE_EVALUATE.invoke(left);
        verify(right, atLeastOnce());
        MethodReference.NODE_EVALUATE.invoke(right);

        assertEquals(expected, actual, context, r -> "evaluate() von ConcatenationNode gibt einen falschen Wert zurück.");
    }
}
