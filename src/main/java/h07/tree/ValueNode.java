package h07.tree;

import h07.expression.ValueExpression;

/**
 * Represents a value node in a tree which is a leaf.
 */
public class ValueNode implements Node {
    /**
     * The value expression of the leaf.
     */
    private ValueExpression expression = () -> "";

    /**
     * Sets the value expression of the leaf.
     *
     * @param expression the new value expression
     */
    public void setValueExpression(ValueExpression expression) {
        this.expression = expression;
    }

    @Override
    public String evaluate() {
        return expression.get();
    }
}
