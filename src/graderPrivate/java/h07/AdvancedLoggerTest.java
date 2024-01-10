package h07;

import h07.tree.Node;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.stubbing.Answer;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.WithModifiers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static h07.ClassReference.*;
import static h07.MethodReference.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.tudalgo.algoutils.tutor.general.match.BasicStringMatchers.identical;

public class AdvancedLoggerTest {

    protected void withMocks(Runnable codeToRun) {
        try (MockedStatic<Log> logMock = mockStatic(Log.class)) {

            MethodLink link = BasicTypeLink.of(Log.class).getMethod(identical("createColorExpression"));

            if (link != null) {
                logMock.when(() -> link.invokeStatic(anyString())).thenAnswer(invocation -> {
                        Object expression = mock(
                            ClassReference.MAP_EXPRESSION.getLink().reflection(),
                            CALLS_REAL_METHODS
                        );
                        when(
                            MethodReference.MAP_EXPRESSION_MAP.invoke(
                                ClassReference.MAP_EXPRESSION.getLink().reflection(),
                                expression, anyString()
                            )
                        ).thenAnswer(mapInvocation ->
                            Log.ANSI_ESCAPE + invocation.getArgument(0)
                                .toString() + mapInvocation.getArgument(0) + Log.ANSI_ESCAPE + Log.ANSI_RESET
                        );
                        return expression;
                    }
                );
            }

            try (
                MockedConstruction<?> condition = mockConditionNode();
                MockedConstruction<?> concatenation = mockConcatenationNode();
                MockedConstruction<?> map = mockMapNode();
                MockedConstruction<?> value = mockValueNode()
            ) {
                codeToRun.run();
            }
        }
    }

    protected MockedConstruction<?> mockConditionNode() {
        if (!CONDITION_NODE.isDefined()) {
            return null;
        }

        Class<?> conditionNodeClass = CONDITION_NODE.getLink().reflection();
        return mockConstruction(
            conditionNodeClass,
            (mock, context) -> {

                List<Object> expression = new ArrayList<>();
                Object objective = context.arguments().get(0);
                Object trueNode = context.arguments().get(1);
                Object falseNode = context.arguments().get(2);
                Object defaultExpression = mock(CONDITION_EXPRESSION.getLink().reflection(), ignored -> false);
                setIfDefined(FieldReference.CONDITION_NODE_CONDITION_EXPRESSION, mock, defaultExpression);
                mockIfDefined(
                    CONDITION_EXPRESSION_CHECK,
                    CONDITION_EXPRESSION.getLink().reflection(),
                    defaultExpression,
                    ignored -> false,
                    anyString()
                );
                expression.add(defaultExpression);

                mockIfDefined(
                    CONDITION_NODE_SET_CONDITION_EXPRESSION,
                    conditionNodeClass,
                    mock,
                    invocation -> {
                        expression.set(0, invocation.getArgument(0));
                        return null;
                    },
                    any(CONDITION_EXPRESSION.getLink().reflection())
                );

                mockIfDefined(
                    NODE_EVALUATE,
                    conditionNodeClass,
                    mock,
                    invocation -> {
                        String objectiveEval = MethodReference.NODE_EVALUATE.invoke(objective.getClass(), objective);
                        boolean conditionEval =
                            MethodReference.CONDITION_EXPRESSION_CHECK.invokeBestEffort(
                                expression.get(0).getClass(),
                                expression.get(0),
                                objectiveEval
                            );
                        String trueEval = MethodReference.NODE_EVALUATE.invoke(trueNode.getClass(), trueNode);
                        String falseEval = MethodReference.NODE_EVALUATE.invoke(falseNode.getClass(), falseNode);

                        return conditionEval ? trueEval : falseEval;
                    }
                );
            }
        );
    }

    protected MockedConstruction<?> mockConcatenationNode() {
        if (!CONCATENATION_NODE.isDefined()) {
            return null;
        }

        Class<?> concatenationNodeClass = CONCATENATION_NODE.getLink().reflection();
        return mockConstruction(
            concatenationNodeClass,
            (mock, context) -> {

                Object left = context.arguments().get(0);
                Object right = context.arguments().get(1);

                mockIfDefined(
                    NODE_EVALUATE,
                    concatenationNodeClass,
                    mock,
                    ignored -> "" + MethodReference.NODE_EVALUATE.invoke(
                        left.getClass(),
                        left
                    ) + MethodReference.NODE_EVALUATE.invoke(right.getClass(), right)
                );
            }
        );
    }

    protected MockedConstruction<?> mockMapNode() {
        if (!MAP_NODE.isDefined() || !MAP_EXPRESSION.isDefined()) {
            return null;
        }

        Class<?> mapNodeClass = MAP_NODE.getLink().reflection();
        return mockConstruction(
            mapNodeClass,
            (mock, context) -> {
                List<Object> expression = new ArrayList<>();
                Object node = context.arguments().get(0);
                Object defaultExpression = mock(MAP_EXPRESSION.getLink().reflection(), ignored -> "");
                setIfDefined(FieldReference.MAP_NODE_MAP_EXPRESSION, mock, defaultExpression);
                mockIfDefined(
                    MAP_EXPRESSION_MAP,
                    MAP_EXPRESSION.getLink().reflection(),
                    defaultExpression,
                    invocation -> invocation.getArgument(0),
                    anyString()
                );

                expression.add(defaultExpression);
                mockIfDefined(
                    MAP_NODE_SET_MAP_EXPRESSION,
                    mapNodeClass,
                    mock,
                    invocation -> {
                        expression.set(0, invocation.getArgument(0));
                        return null;
                    },
                    any(MAP_EXPRESSION.getLink().reflection())
                );

                mockIfDefined(
                    NODE_EVALUATE,
                    mapNodeClass,
                    mock,
                    ignored -> MAP_EXPRESSION_MAP.invokeBestEffort(
                        expression.get(0).getClass(),
                        expression.get(0),
                        (String) NODE_EVALUATE.invoke(Node.class, node)
                    )
                );
            }
        );
    }

    protected MockedConstruction<?> mockValueNode() {
        if (!VALUE_NODE.isDefined()) {
            return null;
        }

        Class<?> valueNodeClass = VALUE_NODE.getLink().reflection();
        return mockConstruction(
            valueNodeClass,
            (mock, context) -> {
                List<Object> expression = new ArrayList<>();
                Object defaultExpression = mock(VALUE_EXPRESSION.getLink().reflection(), ignored -> "");
                setIfDefined(FieldReference.VALUE_NODE_EXPRESSION, mock, defaultExpression);
                mockIfDefined(
                    VALUE_EXPRESSION_GET,
                    VALUE_EXPRESSION.getLink().reflection(),
                    defaultExpression,
                    ignored -> ""
                );

                expression.add(defaultExpression);
                mockIfDefined(
                    VALUE_NODE_SET_VALUE_EXPRESSION,
                    valueNodeClass,
                    mock,
                    invocation -> {
                        expression.set(0, invocation.getArgument(0));
                        return null;
                    },
                    any(VALUE_EXPRESSION.getLink().reflection())
                );
                mockIfDefined(
                    NODE_EVALUATE,
                    valueNodeClass,
                    mock,
                    ignored -> VALUE_EXPRESSION_GET.invokeBestEffort(expression.get(0).getClass(), expression.get(0))
                );
            }
        );
    }

    public void mockIfDefined(MethodReference method, Class<?> definingClass, Object mock, Answer<?> answer,
                              Object... parameter) throws Throwable {
        WithModifiers link = method.getLink(
            definingClass,
            Arrays.stream(parameter).map(o -> o == null ? null : o.getClass()).toArray(Class[]::new)
        );
        if (link == null) {
            return;
        }

        when(method.invoke(definingClass, mock, parameter)).thenAnswer(answer);
    }

    public void setIfDefined(FieldReference field, Object object, Object value) {
        if (!field.isDefined()) {
            return;
        }
        field.getLink().set(object, value);
    }
}
