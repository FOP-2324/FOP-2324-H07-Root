package h07;

import h07.expression.MapExpression;
import h07.tree.*;

import java.time.LocalTime;

/**
 * Logging Engine
 */
public class Log {
    private static final char ANSI_ESCAPE = 0x1b;
    private static final String ANSI_RESET = "[0m";
    private static final String ANSI_BLUE = "[34m";
    private static final String ANSI_YELLOW = "[33m";
    private static final String ANSI_RED = "[31m";


    /**
     * Creates a lambda expression given a color code
     * @param ansiColor ANSI Color Code
     * @return a {@link MapExpression} which can be used to color a {@link String} in the color
     */
    public static MapExpression createColorExpression(String ansiColor){
        return string -> ANSI_ESCAPE + ansiColor + string + ANSI_ESCAPE + ANSI_RESET;
    }


    private final Node rootNode;
    String message;
    int level;

    /**
     * Constructs a new log
     */
    public Log(){
        rootNode = generateTree();
    }

    /**
     * Used to generate the formatter tree of the log
     * @return the root of zhe expression tree
     */
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


    /**
     * Use the expression tree for formatting
     * @param level level of message
     * @param message message
     * @return a formatted {@link  String}
     */
    private String format(int level, String message){
        this.message = message;
        this.level = level;
        return rootNode.evaluate();
    }

    /**
     * Prints a log message to console
     * @param level level of message
     * @param message message
     */
    public void log(int level, String message){
        System.out.println(format(level, message));
    }
}
