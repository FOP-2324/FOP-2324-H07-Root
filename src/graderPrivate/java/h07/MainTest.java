package h07;

import h07.expression.MapExpression;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import spoon.reflect.code.CtLambda;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class MainTest {

    @Test
    public void testTestNormal() {

        boolean isFormatter = ClassReference.TO_UPPER_FORMATTER.getLink().reflection().isInstance(Main.testNormal());

        assertTrue(isFormatter, emptyContext(), r -> "testNormal() liefert keine Instanz von ToUpperFormatter zurück.");
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "MapExpressionValues.json")
    public void testTestLambda(JsonParameterSet params) {
        String expected = params.getString("expected");
        String lowercase = params.getString("lowercase");

        String actual;
        try {
            actual = MethodReference.MAP_EXPRESSION_MAP.invoke(MapExpression.class, Main.testLambda(), lowercase);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        assertEquals(expected, actual, emptyContext(), r -> "Die Methode map liefert die falschen Werte zurück.");
    }

    @Test
    public void testTestLambda() {
        CtMethod<?> testLambdaCT = ((CtClass<?>) BasicTypeLink.of(Main.class).getCtElement())
            .getMethods().stream()
            .filter((m) -> m.getSimpleName().equals("testLambda"))
            .findFirst()
            .orElse(null);

        boolean[] hasLambda = new boolean[1];

        Queue<CtElement> elements = new ArrayDeque<>();
        elements.add(testLambdaCT.getBody());

        while (elements.size() > 0) {

            CtElement element = elements.poll();
            if (element instanceof CtLambda<?>) {
                hasLambda[0] = true;
                break;
            }
            elements.addAll(element.getDirectChildren());
        }

        assertTrue(hasLambda[0], emptyContext(), r -> "testLambda() verwendet kein Lambda für die Rückgabe.");
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "MapExpressionValues.json")
    public void testTestMethodReference(JsonParameterSet params) {
        String expected = params.getString("expected");
        String lowercase = params.getString("lowercase");

        String actual;
        try {
            actual =
                MethodReference.MAP_EXPRESSION_MAP.invoke(MapExpression.class, Main.testMethodReference(), lowercase);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        assertEquals(expected, actual, emptyContext(), r -> "Die Methode map liefert die falschen Werte zurück.");
    }

    @Test
    public void testTestMethodReference() {
        CtMethod<?> testLambdaCT = ((CtClass<?>) BasicTypeLink.of(Main.class).getCtElement())
            .getMethods().stream()
            .filter((m) -> m.getSimpleName().equals("testMethodReference"))
            .findFirst()
            .orElse(null);

        String methodBody = testLambdaCT.getBody().toString();

        assertTrue(
            methodBody.contains("::"),
            emptyContext(),
            r -> "testMethodReference() verwendet keine Methoden-Referenz für die Rückgabe."
        );
    }
}
