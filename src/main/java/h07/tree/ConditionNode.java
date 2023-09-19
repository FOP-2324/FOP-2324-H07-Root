package h07.tree;

import h07.expression.ConditionExpression;

/**
 * Node which represent branching under condition
 */
public class ConditionNode implements Node {

    private ConditionExpression conditionExpression = string -> false;
    private final Node trueNode;
    private final Node falseNode;

    private final Node objectiveNode;

    /**
     * Constructs a new {@link ConditionNode}
     * @param objectiveNodeNode Node which is evaluated using condition
     * @param trueNode subtree, if executions is true
     * @param falseNode subtree, if execution is false
     */
    public ConditionNode(Node objectiveNodeNode, Node trueNode, Node falseNode) {
        this.objectiveNode = objectiveNodeNode;
        this.trueNode = trueNode;
        this.falseNode = falseNode;
    }

    /**
     * Set new Condition
     * @param conditionExpression new {@link ConditionExpression}
     */
    public void setConditionExpression(ConditionExpression conditionExpression) {
        this.conditionExpression = conditionExpression;
    }

    @Override
    public String evaluate() {
        return conditionExpression.check(objectiveNode.evaluate()) ? trueNode.evaluate() : falseNode.evaluate();
    }
}
