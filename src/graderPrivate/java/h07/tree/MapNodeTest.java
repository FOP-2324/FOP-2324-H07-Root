package h07.tree;

import h07.FieldReference;
import h07.H07Test;
import h07.MethodReference;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static h07.ClassReference.MAP_EXPRESSION;
import static h07.ClassReference.MAP_NODE;
import static org.mockito.Mockito.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class MapNodeTest extends H07Test {

    private static Class<?> expression = null;

    public static Stream<Arguments> provideEvaluate() {

        List<Arguments> args = new ArrayList<>();
        List<String> strings = List.of("", "non empty string", "StRiNg WitH CaPitaLiZatIOn", "hello");

        List<Pair<Function<String, String>, String>> mapper = new ArrayList<>();
        mapper.add(Pair.of(s -> s, "Identity Mapper"));
        mapper.add(Pair.of(s -> s + s, "Repeat the string two times"));
        mapper.add(Pair.of(s -> s + "string", "Append \"string\" to the string"));
        mapper.add(Pair.of(s -> "string" + s, "Prepend \"string\" to the string"));

        for (Pair<Function<String, String>, String> pair : mapper) {
            //MapExpression
            Object expression = mock(MapNodeTest.expression);

            strings.forEach(s -> {
                try {
                    when(MethodReference.MAP_EXPRESSION_MAP.invoke(MapNodeTest.expression, expression, s)).thenAnswer(
                        invocation -> {
                            String argument = invocation.getArgument(0);
                            return pair.getLeft().apply(argument);
                        }
                    );
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
                args.add(Arguments.of(expression, pair.getRight(), s, pair.getLeft().apply(s)));
            });

        }

        return args.stream();
    }

    @BeforeEach
    public void getExpression() {
        if (MAP_EXPRESSION.isDefined()) {
            expression = MAP_EXPRESSION.getLink().reflection();
        } else {
            FieldReference.MAP_NODE_MAP_EXPRESSION.assertDefined();
            expression = FieldReference.MAP_NODE_MAP_EXPRESSION.getLink().staticType().reflection();
            MethodReference.MAP_EXPRESSION_MAP.assertDefined(expression, String.class);
        }
    }

    @Test
    public void testDefinition() {
        MAP_NODE.assertCorrectlyDefined();
        MethodReference.MAP_NODE_CONSTRUCTOR.assertCorrectlyDefined();
        MethodReference.MAP_NODE_SET_MAP_EXPRESSION.assertCorrectlyDefined();
        FieldReference.MAP_NODE_MAP_EXPRESSION.assertCorrectlyDefined();
        FieldReference.MAP_NODE_NODE.assertCorrectlyDefined();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "test", "Some String", "SomeStringInCamelCase"})
    public void testConstructor(String testString) throws Throwable {

        //Node
        Object node = mock(MAP_NODE.getLink().reflection());

        //MapNode
        Object mapNode = MethodReference.MAP_NODE_CONSTRUCTOR.invoke(MAP_NODE.getLink().reflection(), null, node);

        FieldReference.MAP_NODE_NODE.assertStoredValue(mapNode, node, emptyContext());
        FieldReference.MAP_NODE_MAP_EXPRESSION.assertNotNull(mapNode, emptyContext());

        //MapExpression
        Object expression = FieldReference.MAP_NODE_MAP_EXPRESSION.getLink().get(mapNode);

        String actual =
            MethodReference.MAP_EXPRESSION_MAP.invoke(MAP_EXPRESSION.getLink().reflection(), expression, testString);
        assertEquals(
            testString,
            actual,
            emptyContext(),
            r -> "Constructor of MapNode does not set mapExpression correctly"
        );
    }

    @Test
    public void testSetMapExpression() throws Throwable {
        //MapExpression
        Object expression = mock(MAP_EXPRESSION.getLink().reflection());
        //MapNode
        Object node = mock(MAP_NODE.getLink().reflection(), CALLS_REAL_METHODS);

        MethodReference.MAP_NODE_SET_MAP_EXPRESSION.invoke(MAP_NODE.getLink().reflection(), node, expression);

        FieldReference.MAP_NODE_MAP_EXPRESSION.assertStoredValue(node, expression, emptyContext());
    }

    @ParameterizedTest
    @MethodSource("provideEvaluate")
    public void testEvaluate(Object expression, String expressionDescription, String nodeEvaluate, String expected) throws Throwable {
        //Node
        Object node = mock(MAP_NODE.getLink().reflection());
        when(MethodReference.NODE_EVALUATE.invoke(MAP_NODE.getLink().reflection(), node)).thenReturn(nodeEvaluate);
        //MapNode
        Object mapNode = mock(MAP_NODE.getLink().reflection(), CALLS_REAL_METHODS);


        FieldReference.MAP_NODE_NODE.getLink().set(mapNode, node);
        FieldReference.MAP_NODE_MAP_EXPRESSION.getLink().set(mapNode, expression);

        Context context = contextBuilder()
            .add("Expression description", expressionDescription)
            .add("nodeEvaluate", nodeEvaluate)
            .build();

        String actual = MethodReference.NODE_EVALUATE.invoke(MAP_NODE.getLink().reflection(), mapNode);
        assertEquals(expected, actual, context, r -> "MapNode.evaluate() does not return expected value");
    }
}
