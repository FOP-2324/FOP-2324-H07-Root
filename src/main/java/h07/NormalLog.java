package h07;

import h07.tree.*;

import java.time.LocalTime;

public class NormalLog extends Log{

    @Override
    protected Node generateTree(){
        ValueNode levelNode = new ValueNode();
        levelNode.setValueExpression(() -> String.valueOf(level));

        ValueNode messageNode = new ValueNode();
        messageNode.setValueExpression(() -> message);

        MapNode newlineNode = new MapNode(messageNode);
        newlineNode.setMapExpression(string -> string.replace("\n", ";"));

        MapNode blueNode = new MapNode(newlineNode);
        blueNode.setMapExpression(createColorExpression(ANSI_BLUE));

        MapNode yellowNode = new MapNode(newlineNode);
        yellowNode.setMapExpression(createColorExpression(ANSI_YELLOW));

        MapNode redNode = new MapNode(newlineNode);
        redNode.setMapExpression(createColorExpression(ANSI_RED));

        ConditionNode conditionNode0 = new ConditionNode(levelNode, yellowNode, redNode);
        conditionNode0.setConditionExpression(string -> string.equals("2") || string.equals("3"));

        ConditionNode conditionNode1 = new ConditionNode(levelNode, blueNode, conditionNode0);
        conditionNode1.setConditionExpression(string -> string.equals("0") || string.equals("1"));

        ValueNode seperatorNode = new ValueNode();
        seperatorNode.setValueExpression(() -> ": ");

        ConcatenationNode concatenationNode0 = new ConcatenationNode(seperatorNode, conditionNode1);

        ValueNode timeNode = new ValueNode();
        timeNode.setValueExpression(() -> LocalTime.now().toString());

        ConcatenationNode concatenationNode1 = new ConcatenationNode(timeNode, concatenationNode0);

        ValueNode newLineNode = new ValueNode();
        newLineNode.setValueExpression(() -> "\n");

        ConcatenationNode concatenationNode2 = new ConcatenationNode(concatenationNode1, newLineNode);

        return concatenationNode2;
    }

}
