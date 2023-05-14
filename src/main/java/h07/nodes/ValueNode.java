package h07.nodes;

import h07.Node;
import h07.ValueExpression;

public class ValueNode implements Node {

    private ValueExpression expression = () -> "";

    public void setValueExpression(ValueExpression expression) {
        this.expression = expression;
    }

    @Override
    public String evaluate() {
        return expression.get();
    }
}
