package h07.nodes;

import h07.MapExpression;
import h07.Node;

public class MapNode implements Node {

    private final Node node;
    private MapExpression mapExpression;

    public MapNode(Node node) {
        this.node = node;
        mapExpression = string -> string;
    }
    public void setMapExpression(MapExpression mapExpression) {
        this.mapExpression = mapExpression;
    }

    @Override
    public String evaluate() {
        return mapExpression.map(node.evaluate());
    }
}
