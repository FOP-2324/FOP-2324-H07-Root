package h07.tree;

import h07.expression.MapExpression;

/**
 * Representing a node which allows mapping
 */
public class MapNode implements Node {

    private final Node node;
    private MapExpression mapExpression;

    /**
     * Constructs a new {@link MapNode} with a given subtree
     * @param node subtree
     */
    public MapNode(Node node) {
        this.node = node;
        mapExpression = string -> string;
    }

    /**
     * Sets a new mapping expression
     * @param mapExpression new {@link  MapExpression}
     */
    public void setMapExpression(MapExpression mapExpression) {
        this.mapExpression = mapExpression;
    }

    @Override
    public String evaluate() {
        return mapExpression.map(node.evaluate());
    }
}
