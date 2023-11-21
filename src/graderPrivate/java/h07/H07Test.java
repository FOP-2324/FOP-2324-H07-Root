package h07;

import org.mockito.MockMakers;
import org.mockito.MockSettings;
import org.sourcegrade.jagr.api.testing.TestCycle;
import org.sourcegrade.jagr.api.testing.extension.TestCycleResolver;

import static org.mockito.Mockito.withSettings;

public class H07Test {

    public MockSettings getSettings() {

        TestCycle cycle = TestCycleResolver.getTestCycle();

        if (cycle != null) {
            Thread.currentThread()
                .setContextClassLoader((ClassLoader) TestCycleResolver.getTestCycle().getClassLoader());
            return withSettings().mockMaker(MockMakers.PROXY);
        }

        return withSettings();
    }
}
