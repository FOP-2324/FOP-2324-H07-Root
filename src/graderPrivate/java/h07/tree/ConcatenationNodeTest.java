package h07.tree;

import h07.FieldReference;
import h07.H07Test;
import h07.MethodReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import static h07.ClassReference.CONCATENATION_NODE;
import static h07.ClassReference.NODE;
import static org.mockito.Mockito.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class ConcatenationNodeTest extends H07Test {

    @BeforeEach
    public void isDefined() {
        CONCATENATION_NODE.assertDefined();
    }

    @Test
    public void testDefinition() {
        CONCATENATION_NODE.assertCorrectlyDefined();
        MethodReference.CONCATENATION_NODE_CONSTRUCTOR.assertCorrectlyDefined();
        FieldReference.CONCATENATION_NODE_LEFT.assertCorrectlyDefined();
        FieldReference.CONCATENATION_NODE_RIGHT.assertCorrectlyDefined();
    }

    @Test
    public void testNaming() {
        CONCATENATION_NODE.assertNamedCorrectly();
        MethodReference.CONCATENATION_NODE_CONSTRUCTOR.assertNamedCorrectly();
    }

    @Test
    public void testConstructor() throws Throwable {

        //Node
        Object left = mock(NODE.getLink().reflection());

        //Node
        Object right = mock(NODE.getLink().reflection());

        //ConditionNode
        Object node = MethodReference.CONCATENATION_NODE_CONSTRUCTOR.invoke(
            CONCATENATION_NODE.getLink().reflection(),
            null,
            left,
            right
        );

        FieldReference.CONCATENATION_NODE_LEFT.assertStoredValue(node, left, emptyContext());
        FieldReference.CONCATENATION_NODE_RIGHT.assertStoredValue(node, right, emptyContext());
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


        //Node
        Object left = mock(NODE.getLink().reflection());
        when(MethodReference.NODE_EVALUATE.invoke(CONCATENATION_NODE.getLink().reflection(), left)).thenReturn(
            leftNodeEvaluate);

        //Node
        Object right = mock(NODE.getLink().reflection());
        when(MethodReference.NODE_EVALUATE.invoke(CONCATENATION_NODE.getLink().reflection(), right)).thenReturn(
            rightNodeEvaluate);

        //ConditionNode
        Object node = mock(CONCATENATION_NODE.getLink().reflection(), CALLS_REAL_METHODS);
        FieldReference.CONCATENATION_NODE_LEFT.getLink().set(node, left);
        FieldReference.CONCATENATION_NODE_RIGHT.getLink().set(node, right);

        String actual = MethodReference.NODE_EVALUATE.invoke(CONCATENATION_NODE.getLink().reflection(), node);

        verify(left, atLeastOnce());
        MethodReference.NODE_EVALUATE.invoke(CONCATENATION_NODE.getLink().reflection(), left);
        verify(right, atLeastOnce());
        MethodReference.NODE_EVALUATE.invoke(CONCATENATION_NODE.getLink().reflection(), right);

        assertEquals(
            expected,
            actual,
            context,
            r -> "The method evaluate() of class ConcatenationNode returns incorrect values."
        );
    }
}
