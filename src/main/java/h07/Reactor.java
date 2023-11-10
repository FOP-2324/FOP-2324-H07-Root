package h07;

import java.util.Random;

/**
 * Representing a Rector of PowerPlant
 */
public class Reactor {
    static final double THRESHOLD = 0.05;

    final int id;
    final PowerFunction powerFunction;


    /**
     * Creates a Reactor
     * @param id Id of Reactor
     * @param powerFunction PowerFunction of this Reactor
     */
    public Reactor(int id, PowerFunction powerFunction){
        this.id = id;
        this.powerFunction = powerFunction;
    }

    @Override
    public String toString() {
        return "Reactor_" + id;
    }

    /**
     * Getter for power output of reactor at time t
     * @param t Time Variable
     * @return Power Output of reactor
     */
    public double getPower(double t){
        return powerFunction.get(t);
    }

    /**
     * Getter for maintenance status of reactor at time t
     * @param t Time Variable
     * @return Maintenance status of reactor
     */
    boolean needMaintenance(double t){
        return (new Random((toString() + powerFunction.get(t) + t).hashCode())).nextDouble() < THRESHOLD;
    }

    /**
     * Create a Reactor
     * @param id ID of reactor
     * @param random Random Generator
     * @return the created Reactor
     */
    public static Reactor generate(int id, Random random){
        return new Reactor(id, PowerFunction.generate(random));
    }
}
