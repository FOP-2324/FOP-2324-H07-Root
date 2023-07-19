package h07.tree;

/**
 * Representing a node of a expression tree
 */
public interface Node {

    /**
     *
     * @return the evaluated expression of this node which contains the sub tree
     */
    String evaluate();
}
