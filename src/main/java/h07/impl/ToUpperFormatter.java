package h07.impl;

import h07.MapExpression;

public class ToUpperFormatter implements MapExpression {
    @Override
    public String map(String string) {
        return string.toUpperCase();
    }
}
