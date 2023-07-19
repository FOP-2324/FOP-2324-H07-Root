package h07.expression;

/**
 * Representing a Mapping between {@link  String} and {@link Boolean}
 */
public interface ConditionExpression {

    /**
     * Evaluate a input sequence
     * @param string input {@link String}
     * @return the resulting transition
     */
    boolean check(String string);
}
