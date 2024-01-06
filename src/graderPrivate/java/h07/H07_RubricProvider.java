package h07;

import h07.expression.ConditionExpressionTest;
import h07.expression.MapExpressionTest;
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
                        "H1.1: ValueExpression ist vollständig korrekt definiert.",
                        JUnitTestRef.ofMethod(() -> ValueExpressionTest.class.getDeclaredMethod("testDefinition"))
                    ),
                    criterion(
                        "H1.2: MapExpression ist vollständig korrekt definiert.",
                        JUnitTestRef.ofMethod(() -> MapExpressionTest.class.getDeclaredMethod("testDefinition"))
                    ),
                    criterion(
                        "H1.3: ConditionExpression ist vollständig korrekt definiert.",
                        JUnitTestRef.ofMethod(() -> ConditionExpressionTest.class.getDeclaredMethod("testDefinition"))
                    ),
                    criterion("H1: Alle Typen und Methoden wurden korrekt benannt.",
                            JUnitTestRef.and(
                                JUnitTestRef.ofMethod(() -> ConditionExpressionTest.class.getDeclaredMethod("testNaming")),
                                JUnitTestRef.ofMethod(() -> MapExpressionTest.class.getDeclaredMethod("testNaming")),
                                JUnitTestRef.ofMethod(() -> ValueExpressionTest.class.getDeclaredMethod("testNaming"))
                            ),
                        -1
                        )
                )
                .minPoints(0)
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
                                    JUnitTestRef.ofMethod(() -> ToUpperFormatterTest.class.getDeclaredMethod("testMap"
                                    )),
                                    JUnitTestRef.ofMethod(() -> ToUpperFormatterTest.class.getDeclaredMethod(
                                        "testMap",
                                        JsonParameterSet.class
                                    ))
                                )
                            ),
                            criterion(
                                "H2.1: ToUpperFormatter ist vollständig korrekt definiert",
                                JUnitTestRef.and(
                                    JUnitTestRef.ofMethod(() -> ToUpperFormatterTest.class.getDeclaredMethod(
                                        "testDefinition")),
                                    JUnitTestRef.ofMethod(() -> ToUpperFormatterTest.class.getDeclaredMethod(
                                        "testNaming"))
                                    ),
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
                                "H2.2: Anonyme Klasse wird korrekt getestet",
                                JUnitTestRef.and(
                                    JUnitTestRef.ofMethod(() -> MainTest.class.getDeclaredMethod("testTestAnonymous")),
                                    JUnitTestRef.ofMethod(() -> MainTest.class.getDeclaredMethod("testTestAnonymous", JsonParameterSet.class))
                                )
                            ),
                            criterion(
                                "H2.2: testLambda() und testMethodReference() sind korrekt implementiert",
                                JUnitTestRef.and(
                                    JUnitTestRef.ofMethod(() -> MainTest.class.getDeclaredMethod("testTestLambda")),
                                    JUnitTestRef.ofMethod(() -> MainTest.class.getDeclaredMethod(
                                        "testTestLambda",
                                        JsonParameterSet.class
                                    )),
                                    JUnitTestRef.ofMethod(() -> MainTest.class.getDeclaredMethod(
                                        "testTestMethodReference")),
                                    JUnitTestRef.ofMethod(() -> MainTest.class.getDeclaredMethod(
                                        "testTestMethodReference",
                                        JsonParameterSet.class
                                    ))
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
                                "H3.2: Konstruktor ist korrekt implementiert",
                                JUnitTestRef.ofMethod(() -> ConcatenationNodeTest.class.getDeclaredMethod(
                                    "testConstructor"))
                            ),
                            criterion(
                                "H3.2: evaluate() ist korrekt implementiert",
                                JUnitTestRef.ofMethod(() -> ConcatenationNodeTest.class.getDeclaredMethod(
                                    "testEvaluate",
                                    JsonParameterSet.class
                                ))
                            ),
                            criterion(
                                "H3.2: ConcatenationNode ist korrekt definiert",
                                JUnitTestRef.ofMethod(() -> ConcatenationNodeTest.class.getDeclaredMethod(
                                    "testDefinition")),
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
                                JUnitTestRef.ofMethod(() -> ValueNodeTest.class.getDeclaredMethod(
                                    "testEvaluateDefault"))
                            ),
                            criterion(
                                "H3.3: setValueExpression() ist korrekt implementiert",
                                JUnitTestRef.ofMethod(() -> ValueNodeTest.class.getDeclaredMethod(
                                    "testEvaluate", String.class))
                            ),
                            criterion(
                                "H3.3 evaluate() ist korrekt implementiert",
                                JUnitTestRef.ofMethod(() -> ValueNodeTest.class.getDeclaredMethod(
                                    "testSetValueExpression",
                                    String.class
                                ))
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
                                JUnitTestRef.ofMethod(() -> MapNodeTest.class.getDeclaredMethod(
                                    "testConstructor",
                                    String.class
                                ))
                            ),
                            criterion(
                                "H3.4: evaluate() ist korrekt implementiert",
                                JUnitTestRef.ofMethod(() -> MapNodeTest.class.getDeclaredMethod(
                                    "testEvaluate",
                                    Object.class,
                                    String.class,
                                    String.class,
                                    String.class
                                ))
                            ),
                            criterion(
                                "H3.4: setMapExpression() ist korrekt implementiert",
                                JUnitTestRef.ofMethod(() -> MapNodeTest.class.getDeclaredMethod("testSetMapExpression"))
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
                                JUnitTestRef.ofMethod(() -> ConditionNodeTest.class.getDeclaredMethod(
                                    "testConstructor",
                                    String.class
                                ))
                            ),
                            criterion(
                                "H3.5: evaluate() ist korrekt implementiert",
                                JUnitTestRef.ofMethod(() -> ConditionNodeTest.class.getDeclaredMethod(
                                    "testEvaluate",
                                    JsonParameterSet.class
                                ))
                            ),
                            criterion(
                                "H3.5: setConditionExpression() sind korrekt implementiert",
                                JUnitTestRef.ofMethod(() -> ConditionNodeTest.class.getDeclaredMethod(
                                    "testConstructor",
                                    String.class
                                ))
                            ),
                            criterion(
                                "H3.5: ConditionNode ist korrekt definiert",
                                JUnitTestRef.ofMethod(() -> ConditionNodeTest.class.getDeclaredMethod("testDefinition"
                                )),
                                -1
                            )
                        )
                        .minPoints(0)
                        .build(),
                    criterion("H3: Alle Typen und Methoden wurden korrekt benannt.",
                        JUnitTestRef.and(
                            JUnitTestRef.ofMethod(() -> NodeTest.class.getDeclaredMethod("testNaming")),
                            JUnitTestRef.ofMethod(() -> ConcatenationNodeTest.class.getDeclaredMethod("testNaming")),
                            JUnitTestRef.ofMethod(() -> ValueNodeTest.class.getDeclaredMethod("testNaming")),
                            JUnitTestRef.ofMethod(() -> MapNodeTest.class.getDeclaredMethod("testNaming")),
                            JUnitTestRef.ofMethod(() -> ConditionNodeTest.class.getDeclaredMethod("testNaming"))
                        ),
                        -1
                    )
                )
                .build(),
            Criterion.builder()
                .shortDescription("H4: Logging Engine")
                .addChildCriteria(
                    Criterion.builder()
                        .shortDescription("H4.1: Ansi Formatierung")
                        .addChildCriteria(
                            criterion(
                                "H4.1: Ansi wird korrekt in den String eingebettet (Farbe und Escape-Sequenz)",
                                JUnitTestRef.ofMethod(() -> LogTest.class.getDeclaredMethod(
                                    "testCreateColorExpression",
                                    String.class,
                                    String.class
                                ))
                            ),
                            criterion(
                                "H4.1: Die verbildliche Anforderung wird eingehalten.",
                                JUnitTestRef.ofMethod(() -> LogTest.class.getDeclaredMethod(
                                    "testCreateColorExpressionRequirements")),
                                -1
                            )
                        )
                        .minPoints(0)
                        .build(),
                    Criterion.builder()
                        .shortDescription("H4.2: Formatierung aufrufen")
                        .addChildCriteria(
                            criterion(
                                "H4.2: format() ruft evaluate korrekt auf und setzt Variablen korrekt",
                                JUnitTestRef.ofMethod(() -> LogTest.class.getDeclaredMethod(
                                    "testFormat",
                                    int.class,
                                    String.class,
                                    String.class
                                ))
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H4.3: Formatierung realisieren 1")
                        .addChildCriteria(
                            criterion(
                                "H4.3: Die von der zurück gelieferte Node formatierte Text beginnt mit der aktuellen " +
                                        "Zeit. Darauf folgt ein Doppelpunkt und ein Leerzeichen",
                                JUnitTestRef.ofMethod(() -> NormalLogTest.class.getDeclaredMethod(
                                    "testGenerateTree_time", long.class))
                            ),
                            criterion(
                                "H4.3: Die von der zurück gelieferte Node formatierte Text besitzt eine korrekte " +
                                        "Färbung.",
                                JUnitTestRef.ofMethod(() -> NormalLogTest.class.getDeclaredMethod(
                                    "testGenerateTree_color",
                                    int.class
                                )),
                                2
                            ),
                            criterion(
                                "H4.3: Die von der zurück gelieferte Node formatierte Text besitzt \";\" statt Zeilen" +
                                        " umbrüchen. ",
                                JUnitTestRef.ofMethod(() -> NormalLogTest.class.getDeclaredMethod(
                                    "testGenerateTree_newLine"))
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H4.4: Formatierung realisieren 2")
                        .addChildCriteria(
                            criterion(
                                "H4.4: Nachrichten mit level 3 werden angezeigt.",
                                JUnitTestRef.ofMethod(() -> MaintenanceLogTest.class.getDeclaredMethod(
                                    "testGenerateTree_Level_Logged"))
                            ),
                            criterion(
                                "H4.4: Nachrichten mit anderen leveln als 3 werden nicht angezeigt.",
                                JUnitTestRef.ofMethod(() -> MaintenanceLogTest.class.getDeclaredMethod(
                                    "testGenerateTree_Level_unLogged",
                                    int.class
                                ))
                            ),
                            criterion(
                                "H4.4: Die Formatierung der Nachricht ist korrekt.",
                                JUnitTestRef.ofMethod(() -> MaintenanceLogTest.class.getDeclaredMethod(
                                    "testGenerateTree_Formatting")),
                                2
                            )
                        )
                        .build()
                )
                .build(),
            Criterion.builder()
                .shortDescription("H5: PowerPlant")
                .addChildCriteria(
                    criterion(
                        "H5: Aktuelle Leistung wird korrekt ausgegeben",
                        JUnitTestRef.ofMethod(() -> PowerPlantTest.class.getDeclaredMethod(
                            "testCheck_power",
                            int.class
                        ))
                    ),
                    criterion(
                        "H5: Overpowerd-Alarm wird korrekt ausgegeben",
                        JUnitTestRef.ofMethod(() -> PowerPlantTest.class.getDeclaredMethod(
                            "testCheck_overPower",
                            int.class
                        ))
                    ),
                    criterion(
                        "H5: Maintenance-Message wird korrekt ausgegeben",
                        JUnitTestRef.ofMethod(() -> PowerPlantTest.class.getDeclaredMethod(
                            "testCheck_maintenance",
                            int.class
                        ))
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

