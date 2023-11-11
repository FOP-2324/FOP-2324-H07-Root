package h07;

import java.util.Random;

/**
 * Function representing a pseudo random behaviour of Reactor
 */
public class PowerFunction {

    final double[] coeff0;
    final double[] coeff1;

    /**
     * Construct new Power Function
     * @param coeff0 Coefficient Index 0
     * @param coeff1 Coefficient Index 1
     */
    public PowerFunction(double[] coeff0, double[] coeff1){
        assert coeff0.length == coeff1.length;

        this.coeff0 = coeff0;
        this.coeff1 = coeff1;
    }

    /**
     * Get Value
     * @param t Time Variable
     * @return the pseudo random value
     */
    public double get(double t){
        int N = coeff0.length;

        double sum = 0;
        for(int n = 0; n < N; n++){
            sum += Math.cos(coeff0[n] * t + coeff1[n]);
        }

        return (sum / (2 * N)) + 0.5;
    }

    /**
     * Generate random PowerFunction
     * @param random Random Generator
     * @param N Size
     * @return the created PowerFunction
     */
    public static PowerFunction generate(Random random, int N){
        double[] coeff0 = new double[N];
        double[] coeff1 = new double[N];

        for(int n = 0; n < N; n++){
            coeff0[n] = 10 * random.nextDouble();
            coeff1[n] = 10 * random.nextDouble();
        }

        return new PowerFunction(coeff0, coeff1);
    }

    /**
     * Generate random PowerFunction with size 4
     * @param random Random Generator
     * @return the created PowerFunction
     */
    public static PowerFunction generate(Random random){
        return generate(random, 4);
    }


}
