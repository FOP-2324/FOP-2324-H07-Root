package h07;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.params.ParameterizedTest;
import org.junitpioneer.jupiter.cartesian.CartesianTest;
import org.junitpioneer.jupiter.params.IntRangeSource;
import org.mockito.MockedStatic;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

@TestForSubmission
public class PowerPlantTest {

    public static Log createLogger(List<Pair<Integer, String>> loggedMessages) {
        Log logger = mock(Log.class);

        doAnswer(
            (invoke) -> loggedMessages.add(new ImmutablePair<>(invoke.getArgument(0), invoke.getArgument(1)))
        )
            .when(logger)
            .log(anyInt(), anyString());

        return logger;
    }

    public static Reactor generateReactor(int id, boolean needMaintenance, double power) {
        Reactor reactor = mock(Reactor.class);
        when(reactor.toString()).thenReturn("Reactor_" + id);
        when(reactor.getPower(anyDouble())).thenReturn(power);
        when(reactor.needMaintenance(anyDouble())).thenReturn(needMaintenance);
        return reactor;
    }

    public static PowerPlant generatePowerPlant(Log logger, List<Reactor> reactors) {
        try (MockedStatic<Reactor> reactorMock = mockStatic(Reactor.class, CALLS_REAL_METHODS)) {
            Stack<Reactor> reactorsStack = new Stack<>();
            reactorsStack.addAll(reactors);
            reactorMock.when(() -> Reactor.generate(anyInt(), any())).thenAnswer((invoke) -> reactorsStack.pop());

            return new PowerPlant(logger, reactors.size());
        }
    }

    @CartesianTest
    public void testCheck_SingleReactor(
        @CartesianTest.Values(booleans = {true, false}) boolean overpowered,
        @CartesianTest.Values(booleans = {true, false}) boolean needsMaintenance
    ) {

        List<Pair<Integer, String>> loggedMessages = new ArrayList<>();

        double reactorPower = overpowered ? 1 : 0;

        Reactor reactor = generateReactor(1, needsMaintenance, reactorPower);
        PowerPlant plant = generatePowerPlant(
            createLogger(loggedMessages),
            List.of(
                reactor
            )
        );

        plant.check(0);

        int expectedLines = 1 + (overpowered ? 1 : 0) + (needsMaintenance ? 1 : 0);

        Context context = contextBuilder()
            .add("power", reactorPower)
            .add("needs maintenence", needsMaintenance)
            .build();

        assertEquals(
            expectedLines,
            loggedMessages.size(),
            context,
            r -> "The number of logged messages does not match the expected number."
        );

        int currentLogIndex = 0;

        int finalCurrentLogIndex = currentLogIndex;
        assertEquals(
            reactor + ": Power = " + reactorPower,
            loggedMessages.get(currentLogIndex).getRight(),
            context,
            r -> "The logged message at Position %d does not match the expected.".formatted(finalCurrentLogIndex)
        );
        assertEquals(
            0,
            loggedMessages.get(currentLogIndex++).getLeft(),
            context,
            r -> "The logged message at Position %d does have the expected level.".formatted(finalCurrentLogIndex)
        );

        if (overpowered) {
            int finalCurrentLogIndex1 = currentLogIndex;
            assertEquals(
                reactor + ": Overpowerd!",
                loggedMessages.get(currentLogIndex).getRight(),
                context,
                r -> "The logged message at Position %d does not match the expected.".formatted(finalCurrentLogIndex1)
            );
            assertEquals(
                6,
                loggedMessages.get(currentLogIndex++).getLeft(),
                context,
                r -> "The logged message at Position %d does have the expected level.".formatted(finalCurrentLogIndex1)
            );
        }
        if (needsMaintenance) {
            int finalCurrentLogIndex2 = currentLogIndex;
            assertEquals(
                reactor + ": Needs maintenance!",
                loggedMessages.get(currentLogIndex).getRight(),
                context,
                r -> "The logged message at Position %d does not match the expected.".formatted(finalCurrentLogIndex2)
            );
            assertEquals(
                3,
                loggedMessages.get(currentLogIndex).getLeft(),
                context,
                r -> "The logged message at Position %d does have the expected level.".formatted(finalCurrentLogIndex2)
            );
        }
    }

    @ParameterizedTest
    @IntRangeSource(from = 0, to = 6)
    public void testCheck_power(int reactorCount) {
        List<Pair<Integer, String>> loggedMessages = new ArrayList<>();

        PowerPlant plant = generatePowerPlant(
            createLogger(loggedMessages),
            IntStream.range(0, reactorCount).mapToObj(
                id -> generateReactor(id, false, id * 0.1)
            ).toList()
        );

        Context context = contextBuilder()
            .add("number of reactors", reactorCount)
            .build();

        plant.check(0);

        assertEquals(
            (long) reactorCount,
            loggedMessages.stream().filter(message -> message.getRight().contains("Power = ")).count(),
            context,
            r -> "An incorrect number of power logs is created."
        );

        for (int i = 0; i < reactorCount; i++) {
            int finalI = i;
            long loggedCorrectMessage = loggedMessages.stream().filter(
                logged ->
                    logged.getLeft() == 0
                        && logged.getRight().equals("Reactor_" + finalI + ": Power = " + finalI * 0.1)
            ).count();
            assertEquals(
                1L,
                loggedCorrectMessage,
                context,
                r -> "Reactor %s did not create a correct number of Power logs.".formatted(finalI)
            );
        }
    }

    @ParameterizedTest
    @IntRangeSource(from = 0, to = 6)
    public void testCheck_overPower(int reactorCount) {
        List<Pair<Integer, String>> loggedMessages = new ArrayList<>();

        PowerPlant plant = generatePowerPlant(
            createLogger(loggedMessages),
            IntStream.range(0, reactorCount).mapToObj(
                id -> generateReactor(id, false, id % 2)
            ).toList()
        );

        Context context = contextBuilder()
            .add("number of reactors", reactorCount)
            .add("power", "id % 2")
            .build();

        plant.check(0);

        assertEquals(
            (long) reactorCount / 2,
            loggedMessages.stream().filter(message -> message.getRight().contains("Overpowerd")).count(),
            context,
            r -> "An incorrect number of overpower logs is created."
        );

        for (int i = 0; i < reactorCount; i++) {
            int finalI = i;
            long loggedCorrectMessage = loggedMessages.stream().filter(
                logged ->
                    logged.getLeft() == 6
                        && logged.getRight().equals("Reactor_" + finalI + ": Overpowerd!")
            ).count();
            assertEquals(
                (long) i % 2,
                loggedCorrectMessage,
                context,
                r -> "Reactor %s did not create a correct number of Overpowerd logs.".formatted(finalI)
            );
        }
    }

    @ParameterizedTest
    @IntRangeSource(from = 1, to = 6)
    public void testCheck_maintenance(int reactorCount) {
        List<Pair<Integer, String>> loggedMessages = new ArrayList<>();

        PowerPlant plant = generatePowerPlant(
            createLogger(loggedMessages),
            IntStream.range(0, reactorCount).mapToObj(
                id -> generateReactor(id, id % 2 == 0, id * 0.1)
            ).toList()
        );

        Context context = contextBuilder()
            .add("number of reactors", reactorCount)
            .add("maintenance", "id % 2 == 0")
            .build();

        plant.check(0);

        assertEquals(
            (long) (reactorCount + 1) / 2,
            loggedMessages.stream().filter(message -> message.getRight().contains("maintenance")).count(),
            context,
            r -> "An incorrect number of maintenance logs is created."
        );

        for (int i = 0; i < reactorCount; i++) {
            int finalI = i;
            long loggedCorrectMessage = loggedMessages.stream().filter(
                logged ->
                    logged.getLeft() == 3
                        && logged.getRight().equals("Reactor_" + finalI + ": Needs maintenance!")
            ).count();
            assertEquals(
                (long) (i + 1) % 2,
                loggedCorrectMessage,
                context,
                r -> "Reactor %s did not create a correct number of maintenance logs.".formatted(finalI)
            );
        }
    }
}
