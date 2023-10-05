package h07;

import org.tudalgo.algoutils.tutor.general.reflections.*;

import java.util.Arrays;

import static h07.ClassReference.*;
import static org.tudalgo.algoutils.tutor.general.reflections.Modifier.*;

public class MethodReference {

    public static final MethodReference VALUE_EXPRESSION_GET = new MethodReference(VALUE_EXPRESSION, "get", new Modifier[]{PUBLIC}, String.class);
    public static final MethodReference MAP_EXPRESSION_MAP = new MethodReference(MAP_EXPRESSION, "map", new Modifier[]{PUBLIC}, String.class, String.class);
    public static final MethodReference CONDITION_EXPRESSION_CHECK = new MethodReference(CONDITION_EXPRESSION, "check", new Modifier[]{PUBLIC}, boolean.class, String.class);
    public static final MethodReference NODE_EVALUATE = new MethodReference(NODE, "evaluate", new Modifier[]{PUBLIC}, String.class);
    public static final MethodReference CONCATENATION_NODE_CONSTRUCTOR = new MethodReference(CONCATENATION_NODE, new Modifier[]{PUBLIC}, NODE.getLink().reflection(), NODE.getLink().reflection());
    public static final MethodReference VALUE_NODE_SET_VALUE_EXPRESSION = new MethodReference(VALUE_NODE, "setValueExpression", new Modifier[]{PUBLIC}, Void.class, VALUE_EXPRESSION.getLink().reflection());
    public static final MethodReference MAP_NODE_SET_MAP_EXPRESSION = new MethodReference(MAP_NODE, "setMapExpression", new Modifier[]{PUBLIC}, Void.class, MAP_EXPRESSION.getLink().reflection());
    public static final MethodReference MAP_NODE_CONSTRUCTOR = new MethodReference(MAP_NODE, new Modifier[]{PUBLIC}, NODE.getLink().reflection());
    public static final MethodReference CONDITION_NODE_CONSTRUCTOR = new MethodReference(CONDITION_NODE, new Modifier[]{PUBLIC}, NODE.getLink().reflection(), NODE.getLink().reflection(), NODE.getLink().reflection());
    public static final MethodReference CONDITION_NODE_SET_CONDITION_EXPRESSION = new MethodReference(CONDITION_NODE, "setConditionExpression", new Modifier[]{PUBLIC}, Void.class, CONDITION_EXPRESSION.getLink().reflection());

    private MethodLink methodLink;
    private ConstructorLink constructorLink;

    public MethodReference(ClassReference clazz, Modifier[] modifiers, Class<?>... parameter){
        //TODO
        try {
            constructorLink = BasicConstructorLink.of(clazz.getLink().reflection().getDeclaredConstructor(parameter));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(clazz.getClass().getName());
            Arrays.stream(clazz.getClass().getConstructors()).forEach(c -> System.out.println(Arrays.stream(c.getParameterTypes()).map(Class::getName).toList()));
        }
    }
    public MethodReference(ClassReference clazz, String name, Modifier[] modifiers, Class<?> returnValue, Class<?>... parameter) {
        //TODO
        try {
            methodLink = BasicMethodLink.of(clazz.getLink().reflection().getDeclaredMethod(name, parameter));

//            clazz.getLink().getMethod((MethodLink m) -> m.typeList());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void isCorrectlyDefined() {
        //TODO
    }

    public <T> T invoke(Object instance, Object... parameter) throws Exception {
        if (methodLink != null){
            return methodLink.invoke(instance, parameter);
        } else {
            return constructorLink.invoke(parameter);
        }
    }
}
