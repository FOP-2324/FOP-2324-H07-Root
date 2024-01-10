package h07;

import org.mockito.Mockito;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.match.Matcher;
import org.tudalgo.algoutils.tutor.general.reflections.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static h07.ClassReference.*;
import static org.mockito.Mockito.mock;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;
import static org.tudalgo.algoutils.tutor.general.reflections.Modifier.PUBLIC;

public class MethodReference {

    public static final MethodReference VALUE_EXPRESSION_GET =
        new MethodReference(VALUE_EXPRESSION, "get", new Modifier[]{PUBLIC}, String.class, new Class[]{});
    public static final MethodReference MAP_EXPRESSION_MAP =
        new MethodReference(MAP_EXPRESSION, "map", new Modifier[]{PUBLIC}, String.class, String.class);
    public static final MethodReference CONDITION_EXPRESSION_CHECK =
        new MethodReference(CONDITION_EXPRESSION, "check", new Modifier[]{PUBLIC}, boolean.class, String.class);
    public static final MethodReference NODE_EVALUATE =
        new MethodReference(NODE, "evaluate", new Modifier[]{PUBLIC}, String.class, new Class[]{});
    public static final MethodReference CONCATENATION_NODE_CONSTRUCTOR =
        new MethodReference(CONCATENATION_NODE, new Modifier[]{PUBLIC}, NODE, NODE);
    public static final MethodReference VALUE_NODE_SET_VALUE_EXPRESSION =
        new MethodReference(VALUE_NODE, "setValueExpression", new Modifier[]{PUBLIC}, Void.class, VALUE_EXPRESSION);
    public static final MethodReference MAP_NODE_SET_MAP_EXPRESSION =
        new MethodReference(MAP_NODE, "setMapExpression", new Modifier[]{PUBLIC}, Void.class, MAP_EXPRESSION);
    public static final MethodReference MAP_NODE_CONSTRUCTOR =
        new MethodReference(MAP_NODE, new Modifier[]{PUBLIC}, NODE);
    public static final MethodReference CONDITION_NODE_CONSTRUCTOR =
        new MethodReference(CONDITION_NODE, new Modifier[]{PUBLIC}, NODE, NODE, NODE);
    public static final MethodReference CONDITION_NODE_SET_CONDITION_EXPRESSION = new MethodReference(
        CONDITION_NODE,
        "setConditionExpression",
        new Modifier[]{PUBLIC},
        Void.class,
        CONDITION_EXPRESSION
    );

    private final boolean isConstructor;
    private final String name;
    private final List<String> parameterClassNames;
    private final Modifier[] expectedModifiers;
    private final Link.Kind kind;
    private final ClassReference clazz;
    private Class<?>[] parameters;
    private MethodLink methodLink;
    private ConstructorLink constructorLink;

    public MethodReference(ClassReference clazz, Modifier[] expectedModifiers, Class<?>... parameters) {
        this.name = Link.Kind.CONSTRUCTOR.name();
        this.parameters = parameters;
        this.parameterClassNames = Arrays.stream(parameters).map(Class::getName).toList();
        this.isConstructor = true;
        this.expectedModifiers = expectedModifiers;
        this.kind = Link.Kind.CONSTRUCTOR;
        this.clazz = clazz;
        if (!clazz.isDefined()) {
            return;
        }
        try {
            constructorLink = BasicConstructorLink.of(clazz.getLink().reflection().getDeclaredConstructor(parameters));
        } catch (NoSuchMethodException ignored) {
        }
    }

    public MethodReference(ClassReference clazz, Modifier[] expectedModifiers, ClassReference... parameters) {
        this.name = Link.Kind.CONSTRUCTOR.name();
        this.parameterClassNames = Arrays.stream(parameters).map(ClassReference::getName).toList();
        this.isConstructor = true;
        this.expectedModifiers = expectedModifiers;
        this.kind = Link.Kind.CONSTRUCTOR;
        this.clazz = clazz;
        if (!clazz.isDefined()) {
            return;
        }
        for (ClassReference ref : parameters) {
            if (!ref.isDefined()) {
                return;
            }
        }
        this.parameters = Arrays.stream(parameters).map(p -> p.getLink().reflection()).toArray(Class[]::new);
        try {
            constructorLink = BasicConstructorLink.of(
                clazz.getLink().reflection().getDeclaredConstructor(
                    Arrays.stream(parameters).map(p -> p.getLink().reflection()).toArray(Class[]::new)
                )
            );
        } catch (NoSuchMethodException ignored) {
        }
    }

    public MethodReference(ClassReference clazz, String name, Modifier[] expectedModifiers, Class<?> returnValue,
                           Class<?>... parameters) {
        this.name = name;
        this.parameters = parameters;
        this.parameterClassNames = Arrays.stream(parameters).map(Class::getName).toList();
        this.isConstructor = false;
        this.expectedModifiers = expectedModifiers;
        this.kind = Link.Kind.METHOD;
        this.clazz = clazz;
        if (!clazz.isDefined()) {
            return;
        }
        methodLink = clazz.getLink().getMethod(Tests.stringMatcher(name));
    }

    public MethodReference(ClassReference clazz, String name, Modifier[] expectedModifiers, Class<?> returnValue,
                           ClassReference... parameters) {
        this.name = name;
        this.isConstructor = false;
        this.expectedModifiers = expectedModifiers;
        this.parameterClassNames = Arrays.stream(parameters).map(ClassReference::getName).toList();
        this.kind = Link.Kind.METHOD;
        this.clazz = clazz;
        if (!clazz.isDefined()) {
            return;
        }
        for (ClassReference ref : parameters) {
            if (!ref.isDefined()) {
                return;
            }
        }
        this.parameters = Arrays.stream(parameters).map(p -> p.getLink().reflection()).toArray(Class[]::new);
        methodLink = clazz.getLink().getMethod(Tests.stringMatcher(name));

    }

    public WithModifiers getLink() {
        return methodLink != null ? methodLink : constructorLink;
    }

    public WithModifiers getLink(Class<?> calledClass, Class<?>... parameters) {
        MethodLink actual = BasicTypeLink.of(calledClass).getMethod(Tests.stringMatcher(name));
        if (actual != null) {
            Set<Class<?>> actualParameters = actual.typeList().stream().map(TypeLink::reflection).collect(Collectors.toSet());

            boolean hasCorrectParams = Arrays.stream(parameters).allMatch(param -> actualParameters.stream().anyMatch(actualParam -> param == null || actualParam.isAssignableFrom(param)));
            if (parameters.length != actualParameters.size() || !hasCorrectParams) {
                return null;
            }
            methodLink = actual;
            return actual;
        }
        if (!clazz.isDefined()) {
            return null;
        }
        if (isConstructor) {
            ConstructorLink link = clazz.getLink().getConstructor(
                Matcher.of(
                    (c) -> {
                        List<? extends Class<?>> types = c.typeList().stream().map(TypeLink::reflection).toList();
                        if (parameters == null || types.size() != parameters.length) {
                            return false;
                        }
                        for (int i = 0; i < types.size(); i++) {
                            if (parameters[i] == null || !types.get(i).isAssignableFrom(parameters[i])) {
                                return false;
                            }
                        }
                        return true;
                    }
                )
            );
            constructorLink = link;
            return link;
        }
        return null;
    }

    public WithModifiers assertDefined(Class<?> calledClass, Class<?>... parameters) {
        Context context = contextBuilder()
            .add("expected class", clazz.getName())
            .add("expected kind", kind)
            .add("expected name", name)
            .add("expected parameter", parameterClassNames)
            .build();
        MethodLink actual = BasicTypeLink.of(calledClass).getMethod(Tests.stringMatcher(name));
        if (actual != null) {

            Set<Class<?>> actualParameters = actual.typeList().stream().map(TypeLink::reflection).collect(Collectors.toSet());

            boolean hasCorrectParams = Arrays.stream(parameters).allMatch(param -> actualParameters.stream().anyMatch(actualParam -> param == null || actualParam.isAssignableFrom(param)));

            context = contextBuilder()
                .add(context)
                .add("actual Parameters", actualParameters)
                .build();

            assertEquals(
                parameters.length,
                actualParameters.size(),
                context,
                r -> String.format("%s does not have the right number of parameters", name)
            );
            assertTrue(
                hasCorrectParams,
                context,
                r -> String.format("%s does not have the right parameters", name)
            );
            methodLink = actual;
            return actual;
        }
        if (!clazz.isDefined()) {
            fail(
                context,
                r -> String.format(
                    "Can not find %s. The Class of the method does not exist or could not be found.",
                    name
                )
            );
        }
        if (isConstructor) {
            ConstructorLink link = clazz.getLink().getConstructor(
                Matcher.of(
                    (c) -> {
                        List<? extends Class<?>> types = c.typeList().stream().map(TypeLink::reflection).toList();
                        if (parameters == null || types.size() != parameters.length) {
                            return false;
                        }
                        for (int i = 0; i < types.size(); i++) {
                            if (parameters[i] == null || !types.get(i).isAssignableFrom(parameters[i])) {
                                return false;
                            }
                        }
                        return true;
                    }
                )
            );
            if (link != null) {
                return link;
            }
            return fail(
                context,
                r -> String.format(
                    "Could not find %s in class %s. The constructor does not exist or has wrong Parameters.",
                    name,
                    clazz.getLink().name()
                )
            );
        }
        return fail(
            context,
            r -> String.format(
                "Can not find %s in class %s. The Method does not exist or could not be found.",
                name,
                calledClass.getName()
            )
        );
    }

    public void assertCorrectlyDefined() {
        assertDefined(clazz.getLink().reflection(), parameters);

        WithModifiers link = assertDefined(clazz.getLink().reflection(), parameters);
        String name =
            link.kind() == Link.Kind.METHOD ? ((MethodLink) link).reflection().getName() : Link.Kind.CONSTRUCTOR.name();
        Modifier[] actualModifiers =
            Arrays.stream(Modifier.values()).filter(m -> m.is(link.modifiers())).toArray(Modifier[]::new);
        Context context = contextBuilder()
            .add("expected class", clazz.getLink().name())
            .add("expected kind", kind)
            .add("expected name", name)
            .add(
                "expected modifier",
                Arrays.stream(expectedModifiers).map(Modifier::keyword).collect(Collectors.joining(", "))
            )
            .add("class", ((Executable) link.reflection()).getDeclaringClass().getName())
            .add("kind", link.kind())
            .add("name", name)
            .add("modifier", Arrays.stream(actualModifiers).map(Modifier::keyword).collect(Collectors.joining(", ")))
            .build();

        assertNotNull(link, context, r -> "Could not find method %s.".formatted(name));
        assertEquals(
            clazz.getLink().reflection(),
            ((Executable) link.reflection()).getDeclaringClass(),
            context,
            r -> "Declaring Class does not match expected Class."
        );
        assertEquals(link.kind(), link.kind(), context, r -> "Kind does not match expected kind.");

        assertTrue(
            Set.of(actualModifiers).containsAll(Set.of(expectedModifiers)),
            context,
            r -> "The modifiers of the Method do not match the expected modifiers."
        );
    }

    public void assertNamedCorrectly() {
        WithModifiers link = getLink(clazz.getLink().reflection(), parameters);
        if (link == null) {
            return;
        }

        String name =
            link.kind() == Link.Kind.METHOD ? ((MethodLink) link).reflection().getName() : Link.Kind.CONSTRUCTOR.name();
        Context context = contextBuilder()
            .add("expected name", name)
            .add("name", name)
            .build();

        assertEquals(this.name, name, context, r -> "The name of the Method does not match the expected name.");
    }

    public <T> T invoke(Class<?> calledClass, Object instance, Object... parameter) throws Throwable {
        Link link = assertDefined(
            calledClass,
            Arrays.stream(parameter).map(o -> o != null ? o.getClass() : null).toArray(Class[]::new)
        );
        if (instance == null && !(link instanceof ConstructorLink)) {
            throw new IllegalArgumentException("Could not invoke %s.%s() as object that the function should have been called on is null.".formatted(clazz.getName(), name));
        }
        if (link instanceof MethodLink l) {
            return l.invoke(instance, parameter);
        } else if (link instanceof ConstructorLink l) {
            return l.invoke(parameter);
        } else {
            return null;
        }
    }

    public <T> T invokeBestEffort(Class<?> calledClass, Object instance, Object... parameter) throws Throwable {
        WithModifiers link = getLink();

        if (link == null) {
            Context context = contextBuilder()
                .add("expected class", clazz.getName())
                .add("expected kind", kind)
                .add("expected name", name)
                .add("expected parameter", parameterClassNames)
                .build();
            fail(
                context,
                r -> String.format(
                    "Can not find %s in class %s. The Method does not exist or could not be found.",
                    name,
                    calledClass.getName()
                )
            );
        }

        Class<?>[] actualParameter = ((Executable) link.reflection()).getParameterTypes();

        try {
            return invoke(calledClass, instance, parameter);
        } catch (Throwable ignored) {
            ignored.printStackTrace();
        }
        if (actualParameter.length == 0) {
            if (link instanceof MethodLink l) {
                return l.invoke(instance);
            } else if (link instanceof ConstructorLink l) {
                return l.invoke();
            } else {
                return null;
            }
        }

        Object[] mocks = Arrays.stream(actualParameter)
            .map(p -> {
                if (p.equals(String.class)) {
                    return "";
                }
                return mock(p);
            })
            .toArray();
        for (int i = 0; i < parameter.length && i < mocks.length; i++) {
            if (mocks[i].getClass().isAssignableFrom(parameter[i].getClass())) {
                mocks[i] = parameter[i];
            }
        }

        if (link instanceof MethodLink l) {
            return l.invoke(instance, mocks);
        } else if (link instanceof ConstructorLink l) {
            return l.invoke(mocks);
        } else {
            return null;
        }
    }
}
