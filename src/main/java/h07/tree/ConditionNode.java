package h07.tree;

import h07.expression.ConditionExpression;

/**
 * Represents a node which can be used to evaluate a condition. Depending on the result of the condition, the
 * {@link #evaluate()} method will return the result of the {@code true} or {@code false} (sub)tree.
 */
public class ConditionNode implements Node {
    /**
     * Condition which is used to evaluate the {@link #objectiveNode}.
     */
    private ConditionExpression conditionExpression = string -> false;

    /**
     * The (sub)tree which is evaluated when the condition is {@code true}.
     */
    private final Node trueNode;

    /**
     * The (sub)tree which is evaluated when the condition is {@code false}.
     */
    private final Node falseNode;

    /**
     * The (sub)tree which will be evaluated by using the {@link #conditionExpression}.
     */
    private final Node objectiveNode;

    /**
     * Constructs a new {@link ConditionNode} with its (sub)tree and evaluation (sub)trees.
     *
     * @param objectiveNodeNode the node which will be evaluated by using the {@link #conditionExpression}
     * @param trueNode          the (sub)tree which is evaluated when the condition is {@code true}
     * @param falseNode         the (sub)tree which is evaluated when the condition is {@code false}
     */
    public ConditionNode(Node objectiveNodeNode, Node trueNode, Node falseNode) {
        this.objectiveNode = objectiveNodeNode;
        this.trueNode = trueNode;
        this.falseNode = falseNode;
    }

    /**
     * Sets the {@link #conditionExpression} which will be used to evaluate the {@link #objectiveNode}.
     *
     * @param conditionExpression the new {@link #conditionExpression}
     */
    public void setConditionExpression(ConditionExpression conditionExpression) {
        this.conditionExpression = conditionExpression;
    }

    @Override
    public String evaluate() {
        return conditionExpression.check(objectiveNode.evaluate()) ? trueNode.evaluate() : falseNode.evaluate();
    }
}
