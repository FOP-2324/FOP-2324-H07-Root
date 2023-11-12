package h07.tree;

import h07.ClassReference;
import h07.MethodReference;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

@TestForSubmission
public class NodeTest {

    @Test
    public void testDefinition() {
        ClassReference.NODE.assertCorrectlyDefined();
        MethodReference.NODE_EVALUATE.assertCorrectlyDefined();
    }
}
