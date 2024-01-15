package h07;

import org.junit.jupiter.engine.descriptor.ClassTestDescriptor;
import org.junit.jupiter.engine.descriptor.TestInstanceLifecycleUtils;
import org.junit.platform.commons.util.ReflectionUtils;
import org.tudalgo.algoutils.reflect.ClassTester;
import org.tudalgo.algoutils.reflect.TestUtils;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.BasicPackageLink;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.Link;
import org.tudalgo.algoutils.tutor.general.reflections.Modifier;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;


public class ClassReference {

    public static final List<String> PREDEFINED_PACKAGES = List.of("h07", "h07.expression", "h07.expression.impl",  "h07.tree");

    public static final ClassReference VALUE_EXPRESSION =
        new ClassReference("h07.expression", "ValueExpression", Link.Kind.INTERFACE);
    public static final ClassReference MAP_EXPRESSION =
        new ClassReference("h07.expression", "MapExpression", Link.Kind.INTERFACE);
    public static final ClassReference CONDITION_EXPRESSION =
        new ClassReference("h07.expression", "ConditionExpression", Link.Kind.INTERFACE);
    public static final ClassReference TO_UPPER_FORMATTER = new ClassReference(
        "h07.expression.impl",
        "ToUpperFormatter",
        Link.Kind.CLASS,
        new BasicTypeLink[]{MAP_EXPRESSION.getLink()},
        Modifier.PUBLIC, Modifier.NON_FINAL
    );
    public static final ClassReference NODE = new ClassReference("h07.tree", "Node", Link.Kind.INTERFACE);
    public static final ClassReference CONCATENATION_NODE = new ClassReference(
        "h07.tree",
        "ConcatenationNode",
        Link.Kind.CLASS,
        new BasicTypeLink[]{NODE.getLink()},
        Modifier.PUBLIC, Modifier.NON_FINAL
    );
    public static final ClassReference VALUE_NODE = new ClassReference(
        "h07.tree",
        "ValueNode",
        Link.Kind.CLASS,
        new BasicTypeLink[]{NODE.getLink()},
        Modifier.PUBLIC, Modifier.NON_FINAL
    );
    public static final ClassReference MAP_NODE = new ClassReference(
        "h07.tree",
        "MapNode",
        Link.Kind.CLASS,
        new BasicTypeLink[]{NODE.getLink()},
        Modifier.PUBLIC, Modifier.NON_FINAL
    );
    public static final ClassReference CONDITION_NODE = new ClassReference(
        "h07.tree",
        "ConditionNode",
        Link.Kind.CLASS,
        new BasicTypeLink[]{NODE.getLink()},
        Modifier.PUBLIC, Modifier.NON_FINAL
    );
    private final String pack;
    private final String name;
    private final Link.Kind kind;
    private final BasicTypeLink[] superClasses;
    private final Modifier[] modifiers;
    private BasicTypeLink link;

    public ClassReference(String pack, String name, Link.Kind kind, BasicTypeLink... superClasses) {
        this(pack, name, kind, superClasses, new Modifier[0]);
    }

    public ClassReference(String pack, String name, Link.Kind kind, BasicTypeLink[] superClasses,
                          Modifier... modifiers) {
        this.pack = pack;
        this.name = name;
        this.kind = kind;
        this.superClasses = superClasses;
        this.modifiers = modifiers;

        try {

            for (String packageName : PREDEFINED_PACKAGES) {
                link = (BasicTypeLink) BasicPackageLink.of(packageName).getType(Tests.stringMatcher(name));
                if (link != null && !link.reflection().getName().endsWith("Test")) {
                    return;
                }
            }
            link = null;

//            link = (BasicTypeLink) BasicPackageLink.of(pack).getType(Tests.stringMatcher(name));
//            if (link != null && link.reflection().getName().endsWith("Test")) {
//                link = null;
//            }
        } catch (Exception ignored) {
        }
    }

    public String getName() {
        return name;
    }

    public boolean isDefined() {
        return link != null;
    }

    public void assertDefined() {
        assertTrue(
            isDefined(),
            emptyContext(),
            r -> String.format("Could not find Type %s. Type is not defined or could not be found.", name)
        );
    }

    public void assertCorrectlyDefined() {
        assertDefined();
        Context context = contextBuilder()
            .add("expected package", pack)
            .add("expected kind", kind)
            .add("expected name", name)
            .add("expected modifier", Arrays.stream(modifiers).map(Modifier::keyword).collect(Collectors.joining(", ")))
            .add("package", link.reflection().getPackage().getName())
            .add("kind", link.kind())
            .add("name", link.name())
            .build();

        assertNotNull(link, context, r -> "Could not find class %s.".formatted(name));
        assertEquals(kind, link.kind(), context, r -> "Kind does not match expected kind.");
        assertTrue(
            Arrays.stream(modifiers).allMatch(m -> m.is(link.modifiers())),
            context,
            r -> "The modifiers of the type do not match the expected modifiers."
        );
    }

    public void assertNamedCorrectly() {
        if (!isDefined()) {
            return;
        }
        Context context = contextBuilder()
            .add("expected name", name)
            .add("name", link.name())
            .build();

        assertNotNull(link, context, r -> "Could not find class %s.".formatted(name));
        assertEquals(name, link.name(), context, r -> "The name of the Type does not match the expected name.");
    }

    public void assertDefinedInCorrectPackage() {
        assertDefined();
        Context context = contextBuilder()
            .add("expected package", pack)
            .add("expected kind", kind)
            .add("expected name", name)
            .add("expected modifier", Arrays.stream(modifiers).map(Modifier::keyword).collect(Collectors.joining(", ")))
            .add("package", link.reflection().getPackage().getName())
            .add("kind", link.kind())
            .add("name", link.name())
            .build();

        assertNotNull(link, context, r -> "Could not find class %s.".formatted(name));
        assertEquals(
            pack,
            link.reflection().getPackage().getName(),
            context,
            r -> "Package name does not match expected package name."
        );
    }

    public BasicTypeLink getLink() {
        assertDefined();
        return link;
    }
}
