package h07.expression;

/**
 * Represents a mapping expression which maps an input string to a transformed result.
 */
public interface MapExpression {
    /**
     * Maps the input string to a transformed result.
     *
     * @param inputString the input string to be mapped.
     * @return the result of mapping the input string, typically representing a transformed or processed value.
     */
    String map(String inputString);
}
