package h07.tree;

import h07.ClassReference;
import h07.MethodReference;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

@TestForSubmission
public class MapNodeTest {

    @Test
    public void testDefinition() {
        ClassReference.MAP_NODE.assertCorrectlyDefined();
        MethodReference.MAP_NODE_CONSTRUCTOR.assertCorrectlyDefined();
        MethodReference.MAP_NODE_SET_MAP_EXPRESSION.assertCorrectlyDefined();
    }

    //TODO
}
