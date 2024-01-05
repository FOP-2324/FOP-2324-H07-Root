package h07;

import h07.tree.Node;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class AdvancedLoggerTest {

    protected void withMocks(Runnable codeToRun) {
        try (MockedStatic<Log> logMock = mockStatic(Log.class)) {
            logMock.when(() -> Log.createColorExpression(anyString())).thenAnswer(invocation -> {
                    Object expression = mock(ClassReference.MAP_EXPRESSION.getLink().reflection());
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

            try (
                MockedConstruction<?> condition = mockConditionNode();
                MockedConstruction<?> concatenation = mockConcatenationNode();
                MockedConstruction<?> map = mockMapNode();
                MockedConstruction<?> value = mockValueNode();
            ) {
                codeToRun.run();
            }
        }
    }

    protected MockedConstruction<?> mockConditionNode() {
        Class<?> conditionNodeClass = ClassReference.CONDITION_NODE.getLink().reflection();
        return mockConstruction(
            conditionNodeClass,
            (mock, context) -> {

                Object objective = context.arguments().get(0);
                Object trueNode = context.arguments().get(1);
                Object falseNode = context.arguments().get(2);
                Object defaultExpression = mock(ClassReference.CONDITION_EXPRESSION.getLink().reflection());
                when(MethodReference.CONDITION_EXPRESSION_CHECK.invoke(ClassReference.CONDITION_EXPRESSION.getLink()
                    .reflection(), defaultExpression, anyString())).thenReturn(false);
                List<Object> expression = new ArrayList<>();
                expression.add(defaultExpression);

                when(MethodReference.CONDITION_NODE_SET_CONDITION_EXPRESSION.invoke(
                    conditionNodeClass,
                    mock,
                    any(ClassReference.CONDITION_EXPRESSION.getLink().reflection())
                )).thenAnswer((invocation -> {
                    expression.set(0, invocation.getArgument(0));
                    return null;
                }));
                when(MethodReference.NODE_EVALUATE.invoke(conditionNodeClass, mock)).thenAnswer(invocation -> {
                        String objectiveEval = MethodReference.NODE_EVALUATE.invoke(objective.getClass(), objective);
                        boolean conditionEval =
                            MethodReference.CONDITION_EXPRESSION_CHECK.invoke(
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
        Class<?> concatenationNodeClass = ClassReference.CONCATENATION_NODE.getLink().reflection();
        return mockConstruction(
            concatenationNodeClass,
            (mock, context) -> {

                Object left = context.arguments().get(0);
                Object right = context.arguments().get(1);

                when(MethodReference.NODE_EVALUATE.invoke(
                    concatenationNodeClass,
                    mock
                )).thenAnswer(ignored -> "" + MethodReference.NODE_EVALUATE.invoke(
                    left.getClass(),
                    left
                ) + MethodReference.NODE_EVALUATE.invoke(right.getClass(), right));
            }
        );
    }

    protected MockedConstruction<?> mockMapNode() {
        Class<?> mapNodeClass = ClassReference.MAP_NODE.getLink().reflection();
        return mockConstruction(
            mapNodeClass,
            (mock, context) -> {

                Object node = context.arguments().get(0);
                Object defaultExpression = mock(ClassReference.MAP_EXPRESSION.getLink().reflection());
                when(MethodReference.MAP_EXPRESSION_MAP.invoke(
                    ClassReference.MAP_EXPRESSION.getLink().reflection(),
                    defaultExpression,
                    anyString()
                )).thenAnswer(invocation -> invocation.getArgument(0));
                List<Object> expression = new ArrayList<>();

                expression.add(defaultExpression);
                when(MethodReference.MAP_NODE_SET_MAP_EXPRESSION.invoke(
                    mapNodeClass,
                    mock,
                    any(ClassReference.MAP_EXPRESSION.getLink().reflection())
                )).thenAnswer((invocation -> {
                    expression.set(0, invocation.getArgument(0));
                    return null;
                }));
                when(MethodReference.NODE_EVALUATE.invoke(
                    mapNodeClass,
                    mock
                )).thenAnswer(ignored -> MethodReference.MAP_EXPRESSION_MAP.invoke(
                    expression.get(0).getClass(),
                    expression.get(0),
                    (String) MethodReference.NODE_EVALUATE.invoke(Node.class, node)
                ));
            }
        );
    }

    protected MockedConstruction<?> mockValueNode() {
        Class<?> valueNodeClass = ClassReference.VALUE_NODE.getLink().reflection();
        return mockConstruction(
            valueNodeClass,
            (mock, context) -> {
                Object defaultExpression = mock(ClassReference.VALUE_EXPRESSION.getLink().reflection());
                when(MethodReference.VALUE_EXPRESSION_GET.invoke(
                    ClassReference.VALUE_EXPRESSION.getLink().reflection(),
                    defaultExpression
                )).thenReturn("");
                List<Object> expression = new ArrayList<>();
                expression.add(defaultExpression);
                when(MethodReference.VALUE_NODE_SET_VALUE_EXPRESSION.invoke(
                    valueNodeClass,
                    mock,
                    any(ClassReference.VALUE_EXPRESSION.getLink().reflection())
                )).thenAnswer((invocation -> {
                    expression.set(0, invocation.getArgument(0));
                    return null;
                }));
                when(MethodReference.NODE_EVALUATE.invoke(
                    valueNodeClass,
                    mock
                )).thenAnswer(ignored -> MethodReference.VALUE_EXPRESSION_GET.invoke(
                    expression.get(0).getClass(),
                    expression.get(0)
                ));
            }
        );
    }
}
