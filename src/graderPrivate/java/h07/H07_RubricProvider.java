package h07;

import h07.expression.ConditionExpressionTest;
import h07.expression.MapExpressionTest;
import h07.expression.ValueExpression;
import h07.expression.ValueExpressionTest;
import h07.expression.impl.ToUpperFormatterTest;
import h07.tree.*;
import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;

import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.criterion;

public class H07_RubricProvider implements RubricProvider {

    public static final Rubric RUBRIC = Rubric.builder()
        .title("H07")
        .addChildCriteria(
            Criterion.builder()
                .shortDescription("H1: Interfaces definieren")
                .addChildCriteria(
                    criterion(
                        "H1.1: ValueExpression ist vollständig korrekt definiert",
                        JUnitTestRef.ofMethod(() -> ValueExpressionTest.class.getDeclaredMethod("testDefinition"))
                    ),
                    criterion(
                        "H1.2: MapExpression ist vollständig korrekt definiert",
                        JUnitTestRef.ofMethod(() -> MapExpressionTest.class.getDeclaredMethod("testDefinition"))
                    ),
                    criterion(
                        "H1.3: ConditionExpression ist vollständig korrekt definiert",
                        JUnitTestRef.ofMethod(() -> ConditionExpressionTest.class.getDeclaredMethod("testDefinition"))
                    )
                )
                .build(),
            Criterion.builder()
                .shortDescription("H2: Interface implementieren")
                .addChildCriteria(
                    Criterion.builder()
                        .shortDescription("H2.1: ToUpperFormatter implementieren")
                        .addChildCriteria(
                            criterion(
                                "H2.1: toUpperCase() wird korrekt verwendet",
                                JUnitTestRef.and(
                                    JUnitTestRef.ofMethod(() -> ToUpperFormatterTest.class.getDeclaredMethod("testMap")),
                                    JUnitTestRef.ofMethod(() -> ToUpperFormatterTest.class.getDeclaredMethod("testMap", JsonParameterSet.class))
                                )
                            ),
                            criterion(
                                "H2.1: ToUpperFormatter ist vollständig korrekt definiert",
                                JUnitTestRef.ofMethod(() -> ToUpperFormatterTest.class.getDeclaredMethod("testDefinition")),
                                -1
                            )
                        )
                        .minPoints(0)
                        .build(),
                    Criterion.builder()
                        .shortDescription("H2.2: Testen")
                        .addChildCriteria(
                            criterion(
                                "H2.2: testNormal() ist korrekt implementiert",
                                JUnitTestRef.ofMethod(() -> MainTest.class.getDeclaredMethod("testTestNormal"))
                            ),
                            criterion(
                                "H2.2: testLambda() und testMethodReference() sind korrekt implementiert",
                                JUnitTestRef.and(
                                    //TODO
                                    JUnitTestRef.ofMethod(() -> MainTest.class.getDeclaredMethod("testTestLambda")),
                                    JUnitTestRef.ofMethod(() -> MainTest.class.getDeclaredMethod("testTestLambda", JsonParameterSet.class))
                                )
                            )
                        )
                        .build()
                )
                .build(),
            Criterion.builder()
                .shortDescription("H3: Ausdrucksbaum")
                .addChildCriteria(
                    criterion(
                        "H3.1: Node wurde korrekt definiert",
                        JUnitTestRef.ofMethod(() -> NodeTest.class.getDeclaredMethod("testDefinition"))
                    ),
                    Criterion.builder()
                        .shortDescription("H3.2: ConcatenationNode ist korrekt implementiert.")
                        .addChildCriteria(
                            criterion(
                                "H3.2: Konstruktor und evaluate() sind korrekt implementiert",
                                JUnitTestRef.ofMethod(() -> ConcatenationNodeTest.class.getDeclaredMethod("testEvaluate", JsonParameterSet.class))
                            ),
                            criterion(
                                "H3.2: ConcatenationNode ist korrekt definiert",
                                JUnitTestRef.ofMethod(() -> ConcatenationNodeTest.class.getDeclaredMethod("testDefinition")),
                                -1
                            )
                        )
                        .minPoints(0)
                        .build(),
                    Criterion.builder()
                        .shortDescription("H3.3: ValueNode ist korrekt implementiert.")
                        .addChildCriteria(
                            criterion(
                                "H3.3: Konstruktor ist korrekt implementiert",
                                JUnitTestRef.and(
                                    //TODO
                                )
                            ),
                            criterion(
                                "H3.3: setValueExpression() und evaluate() sind korrekt implementiert",
                                JUnitTestRef.and(
                                    JUnitTestRef.ofMethod(() -> ValueNodeTest.class.getDeclaredMethod("testEvaluateDefault")),
                                    JUnitTestRef.ofMethod(() -> ValueNodeTest.class.getDeclaredMethod("testSetValueExpression", String.class))
                                )
                            ),
                            criterion(
                                "H3.3: ValueNode ist korrekt definiert",
                                JUnitTestRef.ofMethod(() -> ValueNodeTest.class.getDeclaredMethod("testDefinition")),
                                -1
                            )
                        )
                        .minPoints(0)
                        .build(),
                    Criterion.builder()
                        .shortDescription("H3.4: MapNode ist korrekt implementiert.")
                        .addChildCriteria(
                            criterion(
                                "H3.4: Konstruktor ist korrekt implementiert",
                                JUnitTestRef.and(
                                    //TODO
                                )
                            ),
                            criterion(
                                "H3.4: setMapExpression() und evaluate() sind korrekt implementiert",
                                JUnitTestRef.and(
                                    //TODO
                                )
                            ),
                            criterion(
                                "H3.4: MapNode ist korrekt definiert",
                                JUnitTestRef.ofMethod(() -> MapNodeTest.class.getDeclaredMethod("testDefinition")),
                                -1
                            )
                        )
                        .minPoints(0)
                        .build(),
                    Criterion.builder()
                        .shortDescription("H3.5: ConditionNode ist korrekt implementiert.")
                        .addChildCriteria(
                            criterion(
                                "H3.5: Konstruktor ist korrekt implementiert",
                                JUnitTestRef.and(
                                    //TODO
                                )
                            ),
                            criterion(
                                "H3.5: setConditionExpression() und evaluate() sind korrekt implementiert",
                                JUnitTestRef.and(
                                    //TODO
                                    JUnitTestRef.ofMethod(() -> ConditionNodeTest.class.getDeclaredMethod("testEvaluateDefault", String.class))
                                )
                            ),
                            criterion(
                                "H3.5: ConditionNode ist korrekt definiert",
                                JUnitTestRef.ofMethod(() -> ConditionNodeTest.class.getDeclaredMethod("testDefinition")),
                                -1
                            )
                        )
                        .minPoints(0)
                        .build()
                )
                .build(),
            Criterion.builder()
                .shortDescription("H4: Logging Engine")
                .addChildCriteria(
                    criterion(
                        "H4.1: Ansi wird korrekt in den String eingebettet (Farbe und Escape-Sequenz)",
                        JUnitTestRef.and(
                            //TODO
                        )
                    ),
                    criterion(
                        "H4.2: format ruft evaluate korrekt auf",
                        JUnitTestRef.and(
                            //TODO
                        )
                    ),
                    Criterion.builder()
                        .shortDescription("H4.3: Formatierung realisieren 1")
                        .addChildCriteria(
                            criterion(
                                "H4.3: TODO",
                                JUnitTestRef.and(
                                )
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H4.4: Formatierung realisieren 2")
                        .addChildCriteria(
                            criterion(
                                "H4.4: TODO",
                                JUnitTestRef.and(
                                )
                            )
                        )
                        .build()
                )
                .build(),
            Criterion.builder()
                .shortDescription("H5: PowerPlant")
                .addChildCriteria(
                    criterion(
                        "H5: TODO",
                        JUnitTestRef.and(
                        )
                    )
                )
                .build()
        )
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}

