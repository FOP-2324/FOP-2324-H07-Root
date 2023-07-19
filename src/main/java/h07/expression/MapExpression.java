package h07.expression;

/**
 * Representing an expression which transforms between {@link  String} and {@link String}
 */
public interface MapExpression {

    /**
     * Maps one String to another
     * @param string input {@link String}
     * @return transformed {@link  String}
     */
    String map(String string);
}
