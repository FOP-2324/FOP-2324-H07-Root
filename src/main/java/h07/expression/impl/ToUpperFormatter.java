package h07.expression.impl;

import h07.expression.MapExpression;

/**
 * Is a Mapping Expression which maps a {@link String} to the uppercase
 */
public class ToUpperFormatter implements MapExpression {
    @Override
    public String map(String string) {
        return string.toUpperCase();
    }
}
