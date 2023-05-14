package h07.nodes;

import h07.Node;

public class ConcatenationNode implements Node {

    private final Node left;
    private final Node right;

    public ConcatenationNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }


    @Override
    public String evaluate() {
        return left.evaluate() + right.evaluate();
    }
}
