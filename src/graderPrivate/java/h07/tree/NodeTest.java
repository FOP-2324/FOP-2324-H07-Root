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

    @Test
    public void testNaming() {
        ClassReference.NODE.assertNamedCorrectly();
        MethodReference.NODE_EVALUATE.assertNamedCorrectly();
    }

    @Test
    public void testPackage() {
        ClassReference.NODE.assertDefinedInCorrectPackage();
    }
}
