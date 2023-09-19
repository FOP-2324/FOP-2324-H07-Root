package h07.tree;

/**
 * Node which combines multiple subtree
 */
public class ConcatenationNode implements Node {

    private final Node left;
    private final Node right;

    /**
     * Construct new {@link  ConcatenationNode} with two subtrees
     * @param left left tree
     * @param right right tree
     */
    public ConcatenationNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }


    @Override
    public String evaluate() {
        return left.evaluate() + right.evaluate();
    }
}
