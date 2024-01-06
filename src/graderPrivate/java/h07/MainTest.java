package h07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import spoon.reflect.code.CtLambda;
import spoon.reflect.code.CtNewClass;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;
import static org.tudalgo.algoutils.tutor.general.match.BasicStringMatchers.identical;

@TestForSubmission
public class MainTest {

    @Test
    public void testTestNormal() throws Throwable {
        boolean isFormatter = ClassReference.TO_UPPER_FORMATTER.getLink().reflection().isInstance(invokeMethodInMain("testNormal"));

        assertTrue(isFormatter, emptyContext(), r -> "testNormal() does not return an instance of ToUpperFormatter.");
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "MapExpressionValues.json")
    public void testTestLambda(JsonParameterSet params) {
        String expected = params.getString("expected");
        String lowercase = params.getString("lowercase");

        String actual;
        try {
            actual = MethodReference.MAP_EXPRESSION_MAP.invoke(ClassReference.MAP_EXPRESSION.getLink().reflection(), invokeMethodInMain("testLambda"), lowercase);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        assertEquals(expected, actual, emptyContext(), r -> "map() returns unexpected values.");
    }

    @Test
    public void testTestLambda() {
        CtMethod<?> testLambdaCT = ((CtClass<?>) BasicTypeLink.of(Main.class).getCtElement())
            .getMethods().stream()
            .filter((m) -> m.getSimpleName().equals("testLambda"))
            .findFirst()
            .orElse(null);

        assertNotNull(testLambdaCT, emptyContext(), r -> "Could not find method Body of testLambda(). Method is probably unimplemented");

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

        assertTrue(hasLambda[0], emptyContext(), r -> "testLambda() does not use a lambda to create the returned values.");
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "MapExpressionValues.json")
    public void testTestMethodReference(JsonParameterSet params) {
        String expected = params.getString("expected");
        String lowercase = params.getString("lowercase");

        String actual;
        try {
            actual =
                MethodReference.MAP_EXPRESSION_MAP.invoke(ClassReference.MAP_EXPRESSION.getLink().reflection(), invokeMethodInMain("testMethodReference"), lowercase);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        assertEquals(expected, actual, emptyContext(), r -> "map() returns unexpected values.");
    }

    @Test
    public void testTestMethodReference() {
        CtMethod<?> testLambdaCT = ((CtClass<?>) BasicTypeLink.of(Main.class).getCtElement())
            .getMethods().stream()
            .filter((m) -> m.getSimpleName().equals("testMethodReference"))
            .findFirst()
            .orElse(null);

        assertNotNull(testLambdaCT, emptyContext(), r -> "Could not find method Body of testMethodReference(). Method is probably unimplemented.");

        String methodBody = testLambdaCT.getBody().toString();

        assertTrue(
            methodBody.contains("::"),
            emptyContext(),
            r -> "testMethodReference() does not use method-references to create the returned value."
        );
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "MapExpressionValues.json")
    public void testTestAnonymous(JsonParameterSet params) {
        String expected = params.getString("expected");
        String lowercase = params.getString("lowercase");

        String actual;
        try {
            actual =
                MethodReference.MAP_EXPRESSION_MAP.invoke(ClassReference.MAP_EXPRESSION.getLink().reflection(), invokeMethodInMain("testAnonymous"), lowercase);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        assertEquals(expected, actual, emptyContext(), r -> "map() returns unexpected values.");
    }

    @Test
    public void testTestAnonymous() {
        CtMethod<?> testLambdaCT = ((CtClass<?>) BasicTypeLink.of(Main.class).getCtElement())
            .getMethods().stream()
            .filter((m) -> m.getSimpleName().equals("testAnonymous"))
            .findFirst()
            .orElse(null);

        assertNotNull(testLambdaCT, emptyContext(), r -> "Could not find method Body of testAnonymous(). Method is probably unimplemented.");

        assertFalse(
            testLambdaCT.getBody().filterChildren(new TypeFilter<>(CtNewClass.class)).list().isEmpty(),
            emptyContext(),
            r -> "testAnonymous() does not use anonymous inner class to create the returned value."
        );
    }

    public Object invokeMethodInMain(String methodName) throws Throwable {
        MethodLink link = BasicTypeLink.of(Main.class).getMethod(identical(methodName));
        assertNotNull(link, emptyContext(), r -> "Cant find method Main.%s(). Method is probably unimplemented.".formatted(methodName));
        return link.invokeStatic();
    }
}
