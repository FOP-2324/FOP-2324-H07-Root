package h07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;
import org.junitpioneer.jupiter.cartesian.CartesianTest;
import org.junitpioneer.jupiter.params.IntRangeSource;
import org.mockito.MockSettings;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions4;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.BasicMethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import spoon.reflect.declaration.CtMethod;

import static h07.Log.*;
import static org.mockito.Mockito.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class LogTest extends H07Test {

    Class<?> node = null;

    @BeforeEach
    public void findNode() {
        if (ClassReference.NODE.isDefined()) {
            node = ClassReference.NODE.getLink().reflection();
        } else if (ClassReference.CONDITION_NODE.isDefined()) {
            node = ClassReference.CONDITION_NODE.getLink().reflection();
        } else if (ClassReference.CONCATENATION_NODE.isDefined()) {
            node = ClassReference.CONCATENATION_NODE.getLink().reflection();
        } else if (ClassReference.VALUE_NODE.isDefined()) {
            node = ClassReference.VALUE_NODE.getLink().reflection();
        } else if (ClassReference.MAP_NODE.isDefined()) {
            node = ClassReference.MAP_NODE.getLink().reflection();
        }
    }

    @CartesianTest
    public void testCreateColorExpression(
        @CartesianTest.Values(strings = {ANSI_BLUE, ANSI_RED, ANSI_YELLOW}) String ansiColor,
        @CartesianTest.Values(strings = {"", "non empty string", "CAPITALIZED", "MiXeD CapITalIzAtIon", "blue", "red"
            , "yellow"}) String input
    ) throws Throwable {

        Context context = contextBuilder()
            .add("ansiColor", ansiColor)
            .add("input", input)
            .build();

        MethodLink link = BasicTypeLink.of(Log.class).getMethod(Tests.stringMatcher("createColorExpression"));

        assertNotNull(
            link,
            emptyContext(),
            r -> "Could not find method Body of createColorExpression(). Method is probably unimplemented"
        );

        Object expression = link.invokeStatic(ansiColor);
        String actual = MethodReference.MAP_EXPRESSION_MAP.invoke(
            ClassReference.MAP_EXPRESSION.getLink().reflection(),
            expression,
            input
        );
        String expected = ANSI_ESCAPE + ansiColor + input + ANSI_ESCAPE + ANSI_RESET;

        assertEquals(
            expected,
            actual,
            context,
            r -> "The MapExpression returned by createColorExpression() does not format the input correctly."
        );
    }

    @Test
    public void testCreateColorExpressionRequirements() {

        BasicMethodLink link = ((BasicMethodLink) BasicTypeLink.of(Log.class)
            .getMethod(Tests.stringMatcher("createColorExpression")));

        assertNotNull(
            link,
            emptyContext(),
            r -> "Could not find method Body of createColorExpression(). Method is probably unimplemented"
        );

        CtMethod method = link.getCtElement();

        Assertions4.assertIsOneStatement(method.getBody(), emptyContext(), r -> "");
    }

    @CartesianTest
    @ValueSource(strings = {"", "non empty", "value", "string"})
    public void testFormat(
        @IntRangeSource(from = 0, to = 5) int level,
        @CartesianTest.Values(strings = {"", "non empty", "value", "string"}) String message,
        @CartesianTest.Values(strings = {"", "non empty", "value", "string"}) String expectedString
    ) throws Throwable {

        Context context = contextBuilder()
            .add("level", level)
            .add("message", message)
            .add("evaluate of node", expectedString)
            .build();

        MockSettings settings = getSettings();

        //Node
        Object rootNode = node.isInterface() ? mock(node, settings) : mock(node);
        when(MethodReference.NODE_EVALUATE.invoke(node, rootNode)).thenReturn(expectedString);

        Log logger = mock(Log.class, CALLS_REAL_METHODS);
        BasicTypeLink.of(Log.class).getField(Tests.stringMatcher("rootNode")).set(logger, rootNode);

        MethodLink link = BasicTypeLink.of(Log.class).getMethod(Tests.stringMatcher("format"));

        assertNotNull(
            link,
            emptyContext(),
            r -> "Could not find method Body of format(). Method is probably unimplemented"
        );

        String actual = link.invoke(logger, level, message);

        assertEquals(
            expectedString,
            actual,
            context,
            r -> "String returned from format does not match expected String."
        );
    }
}
