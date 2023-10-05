package h07;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.reflections.BasicPackageLink;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import org.tudalgo.algoutils.tutor.general.reflections.Link;
import org.tudalgo.algoutils.tutor.general.reflections.Modifier;

import java.util.Arrays;
import java.util.stream.Collectors;


public class ClassReference {

    public static final ClassReference VALUE_EXPRESSION = new ClassReference("h07.expression", "ValueExpression", Link.Kind.INTERFACE);
    public static final ClassReference MAP_EXPRESSION = new ClassReference("h07.expression", "MapExpression", Link.Kind.INTERFACE);
    public static final ClassReference CONDITION_EXPRESSION = new ClassReference("h07.expression", "ConditionExpression", Link.Kind.INTERFACE);
    public static final ClassReference TO_UPPER_FORMATTER = new ClassReference("h07.expression.impl", "ToUpperFormatter", Link.Kind.CLASS, new BasicTypeLink[]{MAP_EXPRESSION.getLink()}, Modifier.PUBLIC);
    public static final ClassReference NODE = new ClassReference("h07.tree", "Node", Link.Kind.INTERFACE);
    public static final ClassReference CONCATENATION_NODE = new ClassReference("h07.tree", "ConcatenationNode", Link.Kind.CLASS, new BasicTypeLink[]{NODE.getLink()}, Modifier.PUBLIC);
    public static final ClassReference VALUE_NODE = new ClassReference("h07.tree", "ValueNode", Link.Kind.CLASS, new BasicTypeLink[]{NODE.getLink()}, Modifier.PUBLIC);
    public static final ClassReference MAP_NODE = new ClassReference("h07.tree", "MapNode", Link.Kind.CLASS, new BasicTypeLink[]{NODE.getLink()}, Modifier.PUBLIC);
    public static final ClassReference CONDITION_NODE = new ClassReference("h07.tree", "ConditionNode", Link.Kind.CLASS, new BasicTypeLink[]{NODE.getLink()}, Modifier.PUBLIC);


    private BasicTypeLink link;
    private String pack;
    private String name;
    private Link.Kind kind;
    private BasicTypeLink[] superClasses;
    private Modifier[] modifiers;

    public ClassReference(String pack, String name, Link.Kind kind, BasicTypeLink... superClasses) {
        this(pack, name, kind, superClasses, new Modifier[0]);
    }

    public ClassReference(String pack, String name, Link.Kind kind, BasicTypeLink[] superClasses, Modifier... modifiers) {
        this.pack = pack;
        this.name = name;
        this.kind = kind;
        this.superClasses = superClasses;
        this.modifiers = modifiers;

        //TODO
        try {
            link = BasicTypeLink.of(Class.forName(pack + "." + name));
//            BasicPackageLink.of(pack).getType(Tester.stringMatcher(name));
        } catch (Exception e){}
    }

    public void isCorrectlyDefined() {
        Context context = contextBuilder()
            .add("expected package", pack)
            .add("expected kind", kind)
            .add("expected name", name)
            .add("expected modifier", Arrays.stream(modifiers).map(Modifier::keyword).collect(Collectors.joining(", ")))
            .add("package", link.reflection().getPackage().getName())
            .add("kind", link.kind())
            .add("name", link.name())
            .build();

        assertEquals(pack, link.reflection().getPackage().getName(), context, r -> "Package Name stimmt nicht mit dem Erwarteten überein");
        assertEquals(kind, link.kind(), context, r -> "");
        assertEquals(name, link.name(), context, r -> "Name der Klasse stimmt nicht mit dem erwarteten Namen überein.");
        assertTrue(Arrays.stream(modifiers).allMatch(m -> m.is(link.modifiers())), context, r -> "Die Modifier stimmen nicht mit den Erwartungen überein.");
    }

    public BasicTypeLink getLink() {
        return link;
    }
}
