package h07.tree;

import h07.expression.MapExpression;

/**
 * Represents a mapping node in a tree. A mapping node allows mapping of a node's value to another value.
 */
public class MapNode implements Node {
    /**
     * The (sub)true to be mapped.
     */
    private final Node node;

    /**
     * The mapping expression to be applied to the node's value.
     */
    private MapExpression mapExpression;

    /**
     * Constructs a new {@link MapNode} with a given (sub)tree and no mapping.
     *
     * @param node the (sub)tree to be mapped
     */
    public MapNode(Node node) {
        this.node = node;
        mapExpression = string -> string;
    }

    /**
     * Sets the mapping expression to be applied to the node's value.
     *
     * @param mapExpression the mapping expression to be applied to the node's value
     */
    public void setMapExpression(MapExpression mapExpression) {
        this.mapExpression = mapExpression;
    }

    @Override
    public String evaluate() {
        return mapExpression.map(node.evaluate());
    }
}
