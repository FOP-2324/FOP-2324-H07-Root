package h07;

import java.util.Random;

public class Reactor {
    static final double THRESHOLD = 0.05;

    final int id;
    final PowerFunction powerFunction;


    public Reactor(int id, PowerFunction powerFunction){
        this.id = id;
        this.powerFunction = powerFunction;
    }

    @Override
    public String toString() {
        return "Reactor_" + id;
    }

    public double getPower(double t){
        return powerFunction.get(t);
    }

    boolean needMaintenance(double t){
        return (new Random((toString() + powerFunction.get(t) + t).hashCode())).nextDouble() < THRESHOLD;
    }

    public static Reactor generate(int id, Random random){
        return new Reactor(id, PowerFunction.generate(random));
    }



}
