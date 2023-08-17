package h07;

import h07.tree.ConditionNode;
import h07.tree.Node;
import h07.tree.ValueNode;

public class MaintenanceLog extends Log {

    @Override
    protected Node generateTree() {

        ValueNode levelNode = new ValueNode();
        levelNode.setValueExpression(() -> String.valueOf(level));

        ValueNode messageNode = new ValueNode();
        messageNode.setValueExpression(() -> message + "\n");

        ValueNode emptyMessageNode = new ValueNode();
        emptyMessageNode.setValueExpression(() -> "");


        ConditionNode conditionNode = new ConditionNode(levelNode, messageNode, emptyMessageNode);
        conditionNode.setConditionExpression(string -> string.equals("3"));

        return conditionNode;
    }
}
