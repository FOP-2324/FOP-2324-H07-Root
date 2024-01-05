package h07;

import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.BasicFieldLink;
import org.tudalgo.algoutils.tutor.general.reflections.Modifier;

import java.util.Arrays;
import java.util.stream.Collectors;

import static h07.ClassReference.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

public class FieldReference {

    public static final FieldReference CONCATENATION_NODE_LEFT =
        new FieldReference("left", ClassReference.CONCATENATION_NODE, new Modifier[]{Modifier.PRIVATE}, NODE);
    public static final FieldReference CONCATENATION_NODE_RIGHT =
        new FieldReference("right", ClassReference.CONCATENATION_NODE, new Modifier[]{Modifier.PRIVATE}, NODE);
    public static final FieldReference VALUE_NODE_EXPRESSION = new FieldReference(
        "expression",
        ClassReference.VALUE_NODE,
        new Modifier[]{Modifier.PRIVATE},
        VALUE_EXPRESSION
    );
    public static final FieldReference MAP_NODE_NODE =
        new FieldReference("node", ClassReference.MAP_NODE, new Modifier[]{Modifier.PRIVATE}, NODE);
    public static final FieldReference MAP_NODE_MAP_EXPRESSION = new FieldReference(
        "mapExpression",
        ClassReference.MAP_NODE,
        new Modifier[]{Modifier.PRIVATE},
        MAP_EXPRESSION
    );
    public static final FieldReference CONDITION_NODE_CONDITION_EXPRESSION = new FieldReference(
        "conditionExpression",
        CONDITION_NODE,
        new Modifier[]{Modifier.PRIVATE},
        CONDITION_EXPRESSION
    );
    public static final FieldReference CONDITION_NODE_TRUE_NODE =
        new FieldReference("trueNode", CONDITION_NODE, new Modifier[]{Modifier.PRIVATE}, NODE);
    public static final FieldReference CONDITION_NODE_FALSE_NODE =
        new FieldReference("falseNode", CONDITION_NODE, new Modifier[]{Modifier.PRIVATE}, NODE);
    public static final FieldReference CONDITION_NODE_OBJECTIVE_NODE =
        new FieldReference("objectiveNode", CONDITION_NODE, new Modifier[]{Modifier.PRIVATE}, NODE);

    private final ClassReference declaringClass;
    private final String name;
    private final Modifier[] modifiers;
    private final Class<?> type;
    private BasicFieldLink link;

    public FieldReference(String name, ClassReference clazz, Modifier[] modifiers, Class<?> type) {
        this.name = name;
        this.modifiers = modifiers;
        this.type = type;

        declaringClass = clazz;
        if (!clazz.isDefined()) {
            return;
        }

        try {
            link = (BasicFieldLink) clazz.getLink().getField(Tests.stringMatcher(name));
        } catch (Exception ignored) {
        }
    }

    public FieldReference(String name, ClassReference clazz, Modifier[] modifiers, ClassReference type) {
        this.name = name;
        this.modifiers = modifiers;
        this.type = type.getLink().reflection();

        declaringClass = clazz;
        if (!clazz.isDefined()) {
            return;
        }

        try {
            link = (BasicFieldLink) clazz.getLink().getField(Tests.stringMatcher(name));
        } catch (Exception ignored) {
        }
    }

    public boolean isDefined() {
        return link != null;
    }

    public void assertDefined() {
        Context context = contextBuilder()
            .add("in class", declaringClass.getName())
            .add("name", name)
            .add("type", type.getName())
            .add("modifier", Arrays.stream(modifiers).map(Modifier::keyword).collect(Collectors.joining(", ")))
            .build();
        assertTrue(isDefined(), context, r -> "Field is not defined or could not be found.");
    }

    public void assertNamedCorrectly() {
        assertDefined();
        Context context = contextBuilder()
            .add("in class", declaringClass.getName())
            .add("expected name", name)
            .add("name", link.name())
            .build();

        assertEquals(name, link.name(), context, r -> "The name of the field does not match the expected name.");
    }

    public void assertCorrectlyDefined() {
        assertDefined();
        Context context = contextBuilder()
            .add("in class", declaringClass.getName())
            .add("expected Type", type.getName())
            .add("expected modifier", Arrays.stream(modifiers).map(Modifier::keyword).collect(Collectors.joining(", ")))
            .add("type", link.reflection().getType().getName())
            .build();

        assertEquals(
            type,
            link.staticType().reflection(),
            context,
            r -> "The static type of the field does not match the expected Type"
        );
        assertTrue(
            Arrays.stream(modifiers).allMatch(m -> m.is(link.modifiers())),
            context,
            r -> "The modifiers of the field do not match the expected modifiers"
        );
    }

    public void assertStoredValue(Object instance, Object expectedValue, Context context) {
        assertDefined();
        assertEquals(
            expectedValue,
            link.get(instance),
            context,
            r -> String.format(
                "Value stored in %s.%s does not match expected",
                link.reflection().getDeclaringClass().getName(),
                link.name()
            )
        );
    }

    public void assertNotNull(Object instance, Context context) {
        assertDefined();

        Assertions2.assertNotNull(
            link.get(instance),
            context,
            r -> String.format("Value stored in %s.%s is Null",
                link.reflection().getDeclaringClass().getName(),
                link.name()
            )
        );
    }

    public BasicFieldLink getLink() {
        assertDefined();
        return link;
    }
}
