package h07;

import org.tudalgo.algoutils.tutor.general.reflections.*;

import java.lang.reflect.Method;
import java.util.Arrays;

import static h07.ClassReference.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;
import static org.tudalgo.algoutils.tutor.general.reflections.Modifier.*;

public class MethodReference {

    public static final MethodReference VALUE_EXPRESSION_GET = new MethodReference(VALUE_EXPRESSION, "get", new Modifier[]{PUBLIC}, String.class, new Class[]{});
    public static final MethodReference MAP_EXPRESSION_MAP = new MethodReference(MAP_EXPRESSION, "map", new Modifier[]{PUBLIC}, String.class, String.class);
    public static final MethodReference CONDITION_EXPRESSION_CHECK = new MethodReference(CONDITION_EXPRESSION, "check", new Modifier[]{PUBLIC}, boolean.class, String.class);
    public static final MethodReference NODE_EVALUATE = new MethodReference(NODE, "evaluate", new Modifier[]{PUBLIC}, String.class, new Class[]{});
    public static final MethodReference CONCATENATION_NODE_CONSTRUCTOR = new MethodReference(CONCATENATION_NODE, new Modifier[]{PUBLIC}, NODE, NODE);
    public static final MethodReference VALUE_NODE_SET_VALUE_EXPRESSION = new MethodReference(VALUE_NODE, "setValueExpression", new Modifier[]{PUBLIC}, Void.class, VALUE_EXPRESSION);
    public static final MethodReference MAP_NODE_SET_MAP_EXPRESSION = new MethodReference(MAP_NODE, "setMapExpression", new Modifier[]{PUBLIC}, Void.class, MAP_EXPRESSION);
    public static final MethodReference MAP_NODE_CONSTRUCTOR = new MethodReference(MAP_NODE, new Modifier[]{PUBLIC}, NODE);
    public static final MethodReference CONDITION_NODE_CONSTRUCTOR = new MethodReference(CONDITION_NODE, new Modifier[]{PUBLIC}, NODE, NODE, NODE);
    public static final MethodReference CONDITION_NODE_SET_CONDITION_EXPRESSION = new MethodReference(CONDITION_NODE, "setConditionExpression", new Modifier[]{PUBLIC}, Void.class, CONDITION_EXPRESSION);

    private final boolean isConstructor;
    private ClassReference clazz;
    private MethodLink methodLink;
    private ConstructorLink constructorLink;
    private final String name;
    private final Class<?>[] parameters;

    public MethodReference(ClassReference clazz, Modifier[] modifiers, Class<?>... parameters){
        //TODO
        this.name = "Constructor";
        this.parameters = parameters;
        this.isConstructor = true;
        if (clazz == null){
            return;
        }
        try {
            constructorLink = BasicConstructorLink.of(clazz.getLink().reflection().getDeclaredConstructor(parameters));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(clazz.getClass().getName());
            Arrays.stream(clazz.getClass().getConstructors()).forEach(c -> System.out.println(Arrays.stream(c.getParameterTypes()).map(Class::getName).toList()));
        }
    }

    public MethodReference(ClassReference clazz, Modifier[] modifiers, ClassReference... parameters){
        //TODO
        this.name = "Constructor";
        this.parameters = Arrays.stream(parameters).map(p -> p.getLink().reflection()).toArray(Class[]::new);
        this.isConstructor = true;
        if (clazz == null){
            return;
        }
        for (ClassReference ref : parameters){
            if (!ref.isDefined()){
                return;
            }
        }
        try {
            constructorLink = BasicConstructorLink.of(
                clazz.getLink().reflection().getDeclaredConstructor(
                    Arrays.stream(parameters).map(p -> p.getLink().reflection()).toArray(Class[]::new)
                )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MethodReference(ClassReference clazz, String name, Modifier[] modifiers, Class<?> returnValue, Class<?>... parameters) {
        //TODO
        this.name = name;
        this.parameters = parameters;
        this.isConstructor = false;
        try {
//            methodLink = BasicMethodLink.of(clazz.getLink().reflection().getDeclaredMethod(name, parameter));
            methodLink = clazz.getLink().getMethod(Tests.stringMatcher(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MethodReference(ClassReference clazz, String name, Modifier[] modifiers, Class<?> returnValue, ClassReference... parameters) {
        //TODO
        this.name = name;
        this.parameters = Arrays.stream(parameters).map(p -> p.getLink().reflection()).toArray(Class[]::new);
        this.isConstructor = false;
        for (ClassReference ref : parameters){
            if (!ref.isDefined()){
                return;
            }
        }
        try {
            methodLink = clazz.getLink().getMethod(Tests.stringMatcher(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Link assertDefined(Class<?> calledClass){
        if (constructorLink != null){
            return constructorLink;
        }
        if (methodLink != null){
            return methodLink;
        }
        MethodLink actual = BasicTypeLink.of(calledClass).getMethod(Tests.stringMatcher(name));
        if (actual != null){
            return actual;
        }
        if (clazz == null || !clazz.isDefined()){
            fail(emptyContext(), r -> String.format("Can not find %s. The Class of the method does not exist or could not be found.", name));
        }
        return fail(emptyContext(), r -> String.format("Can not find %s in class %s. The Method does not exist or could not be found.", name, clazz.getLink().name()));
    }

    public void assertCorrectlyDefined() {
        //TODO
    }

    public <T> T invoke(Class<?> calledClass, Object instance, Object... parameter) throws Throwable {
        Link link = assertDefined(calledClass);
        if (link instanceof MethodLink l){
            return l.invoke(instance, parameter);
        } else if (link instanceof ConstructorLink l) {
            return l.invoke(parameter);
        } else {
            return null;
        }
    }
}
