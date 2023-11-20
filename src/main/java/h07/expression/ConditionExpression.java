package h07.expression;

/**
 * Represents a condition expression which checks a string to a condition.
 */
public interface ConditionExpression {
    /**
     * Checks the string to a condition.
     *
     * @param string string to check
     * @return {@code true} if the string satisfies the condition, {@code false} otherwise
     */
    boolean check(String string);
}
