package h07.tree;

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
