package h07.tree;

/**
 * Represents a node which can be used to concatenate two (sub)trees..
 */
public class ConcatenationNode implements Node {

    /**
     * The left node of the concatenation.
     */
    private final Node left;

    /**
     * The right node of the concatenation.
     */
    private final Node right;

    /**
     * Constructs a new {@link ConcatenationNode} with its (sub)trees.
     *
     * @param left  the left node of the concatenation
     * @param right the right node of the concatenation
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
