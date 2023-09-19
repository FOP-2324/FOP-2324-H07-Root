package h07.tree;

import h07.expression.ValueExpression;

/**
 * Representing a leaf
 */
public class ValueNode implements Node {

    private ValueExpression expression = () -> "";

    /**
     * Set a new expression for the leaf
     * @param expression new {@link ValueExpression}
     */
    public void setValueExpression(ValueExpression expression) {
        this.expression = expression;
    }

    @Override
    public String evaluate() {
        return expression.get();
    }
}
