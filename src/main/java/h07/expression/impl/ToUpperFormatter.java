package h07.expression.impl;

import h07.expression.MapExpression;


/**
 * Represents a map expression that converts a string to upper case.
 */
public class ToUpperFormatter implements MapExpression {
    @Override
    public String map(String string) {
        return string.toUpperCase();
    }
}
