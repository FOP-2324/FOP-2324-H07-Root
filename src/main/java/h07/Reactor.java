package h07;

import java.util.Random;

public class Reactor {
    static final double THRESHOLD = 0.05;

    final int id;
    Random random;
    final PowerFunction powerFunction;


    public Reactor(int id, Random random, PowerFunction powerFunction){
        this.id = id;
        this.random = random;
        this.powerFunction = powerFunction;
    }

    @Override
    public String toString() {
        return "Reactor_" + id;
    }

    public double getPower(double t){
        return powerFunction.get(t);
    }

    boolean needMaintenance(){
        return random.nextDouble() < THRESHOLD;
    }

    public static Reactor generate(int id, Random random){
        return new Reactor(id, new Random(random.nextLong(Long.MAX_VALUE)), PowerFunction.generate(random));
    }



}
