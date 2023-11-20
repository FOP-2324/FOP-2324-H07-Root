package h07;

import java.util.Random;

/**
 * Represents a reactor in a power plant.
 */
public class Reactor {

    /**
     * The threshold for maintenance.
     */
    static final double THRESHOLD = 0.05;

    /**
     * The id of the reactor.
     */
    final int id;

    /**
     * The power function of the reactor.
     */
    final PowerFunction powerFunction;


    /**
     * Creates a new reactor.
     *
     * @param id            the ID of the reactor
     * @param powerFunction the power function of the reactor
     */
    public Reactor(int id, PowerFunction powerFunction) {
        this.id = id;
        this.powerFunction = powerFunction;
    }

    /**
     * Creates a new reactor with a random power function.
     *
     * @param id     the ID of the reactor
     * @param random the random generator
     * @return the created reactor
     */
    public static Reactor generate(int id, Random random) {
        return new Reactor(id, PowerFunction.generate(random));
    }

    /**
     * Returns the power of this reactor at time t.
     *
     * @param t the time variable
     * @return the power output of the reactor
     */
    public double getPower(double t) {
        return powerFunction.get(t);
    }

    /**
     * Returns {@code true} if the reactor needs maintenance at time t.
     *
     * @param t the time Variable
     * @return the maintenance status of reactor
     */
    boolean needMaintenance(double t) {
        return (new Random((toString() + powerFunction.get(t) + t).hashCode())).nextDouble() < THRESHOLD;
    }

    @Override
    public String toString() {
        return "Reactor_" + id;
    }
}
