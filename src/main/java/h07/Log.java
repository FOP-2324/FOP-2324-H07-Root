package h07;

import h07.expression.MapExpression;
import h07.tree.Node;

/**
 * Defines a logging engine which can be used to print messages to console using ANSI escape codes.
 */
public abstract class Log {
    /**
     * The ASCII escape character used to initiate ANSI escape sequences in console output.
     */
    protected static final char ANSI_ESCAPE = 0x1b;

    /**
     * ANSI escape code for resetting all formatting styles.
     */
    protected static final String ANSI_RESET = "[0m";

    /**
     * ANSI escape code for setting text color to blue.
     */
    protected static final String ANSI_BLUE = "[34m";

    /**
     * ANSI escape code for setting text color to yellow.
     */
    protected static final String ANSI_YELLOW = "[33m";

    /**
     * ANSI escape code for setting text color to red.
     */
    protected static final String ANSI_RED = "[31m";

    /**
     * The root node (tree) of the expression tree used to format log messages.
     */
    private final Node rootNode;

    /**
     * The message to be formatted.
     */
    String message;

    /**
     * The level of the message to be formatted.
     */
    int level;


    /**
     * Creates a new {@link Log} instance.
     */
    public Log() {
        rootNode = generateTree();
    }

    /**
     * Creates a mapping expression which will map a value to the given ANSI color.
     *
     * @param ansiColor the ANSI color to map to
     * @return a mapping expression which will map a value to the given ANSI color
     */
    public static MapExpression createColorExpression(String ansiColor) {
        return string -> ANSI_ESCAPE + ansiColor + string + ANSI_ESCAPE + ANSI_RESET;
    }

    /**
     * Generates the expression tree used to format log messages.
     *
     * @return the root node (tree) of the expression tree
     */
    protected abstract Node generateTree();

    /**
     * Use the expression tree for formatting
     *
     * @param level   level of message
     * @param message message
     * @return a formatted {@link  String}
     */

    /**
     * Formats a log message using the expression tree.
     *
     * @param level   the level of the message
     * @param message the message to be formatted
     * @return the formatted message
     */
    private String format(int level, String message) {
        this.message = message;
        this.level = level;
        return rootNode.evaluate();
    }

    /**
     * Prints the given log message to console.
     *
     * @param level   the level of the message
     * @param message the message to be printed
     */
    public void log(int level, String message) {
        System.out.print(format(level, message));
    }
}
