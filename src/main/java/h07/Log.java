package h07;

import h07.expression.MapExpression;
import h07.tree.*;

import java.time.LocalTime;

public class Log {
    private static final char ANSI_ESCAPE = 0x1b;
    private static final String ANSI_RESET = "[0m";
    private static final String ANSI_BLUE = "[34m";
    private static final String ANSI_YELLOW = "[33m";
    private static final String ANSI_RED = "[31m";

    public static MapExpression createColorExpression(String ansiColor){
        return string -> ANSI_ESCAPE + ansiColor + string + ANSI_ESCAPE + ANSI_RESET;
    }


    private Node rootNode;
    String message;
    int level;

    public Log(){
        rootNode = generateTree();
    }

    private Node generateTree(){
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
        return concatenationNode1;
    }

    private String format(int level, String message){
        this.message = message;
        this.level = level;
        return rootNode.evaluate();
    }

    public void log(int level, String message){
        System.out.println(format(level, message));
    }
}
