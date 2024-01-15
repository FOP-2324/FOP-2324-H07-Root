package h07.expression.impl;

import h07.ClassReference;
import h07.MethodReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;
import org.tudalgo.algoutils.tutor.general.match.Matcher;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class ToUpperFormatterTest {

    private Object formatterInstance;
    private CtMethod<?> EXERCISE_METHOD;

    @BeforeEach
    public void setup() throws Throwable {

        BasicTypeLink formatter = ClassReference.TO_UPPER_FORMATTER.getLink();

        formatterInstance = ClassReference.TO_UPPER_FORMATTER.getLink().getConstructor(Matcher.always()).invoke();

        EXERCISE_METHOD = ((CtClass<?>) formatter.getCtElement())
            .getMethods().stream()
            .filter((m) -> m.getSimpleName().equals("map"))
            .findFirst()
            .orElse(null);
    }

    @Test
    public void testDefinition() {
        ClassReference.TO_UPPER_FORMATTER.assertCorrectlyDefined();
    }

    @Test
    public void testNaming() {
        ClassReference.TO_UPPER_FORMATTER.assertNamedCorrectly();
    }

    @Test
    public void testPackage() {
        ClassReference.TO_UPPER_FORMATTER.assertDefinedInCorrectPackage();
    }

    @Test
    public void testMap() {

        assertNotNull(EXERCISE_METHOD, emptyContext(), r -> "Could not find method map().");

        String source = EXERCISE_METHOD.getOriginalSourceFragment().toString();

        if (!source.contains("toUpperCase()")) {
            fail(emptyContext(), r -> "Could not find call to toUpperCase()!");
        }

    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "MapExpressionValues.json")
    public void testMap(JsonParameterSet params) {
        String expected = params.getString("expected");
        String lowercase = params.getString("lowercase");

        String actual;
        try {
            assertNotNull(EXERCISE_METHOD, emptyContext(), r -> "Could not find method map().");
            actual = MethodReference.MAP_EXPRESSION_MAP.invoke(ClassReference.TO_UPPER_FORMATTER.getLink().reflection(),
                formatterInstance,
                lowercase
            );
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        assertEquals(expected, actual, emptyContext(), r -> "Map does not returns correct values.");
    }
}
