package h07.tree;

import h07.expression.ConditionExpression;

public class ConditionNode implements Node {

    private ConditionExpression conditionExpression = string -> false;
    private final Node trueNode;
    private final Node falseNode;

    private final Node objectiveNode;

    public ConditionNode(Node objectiveNodeNode, Node trueNode, Node falseNode) {
        this.objectiveNode = objectiveNodeNode;
        this.trueNode = trueNode;
        this.falseNode = falseNode;
    }

    public void setConditionExpression(ConditionExpression conditionExpression) {
        this.conditionExpression = conditionExpression;
    }

    @Override
    public String evaluate() {
        return conditionExpression.check(objectiveNode.evaluate()) ? trueNode.evaluate() : falseNode.evaluate();
    }
}