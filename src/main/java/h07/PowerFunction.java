package h07;

import java.util.Random;

/**
 * Represents a pseudo random behaviour of {@link Reactor}.
 */
public class PowerFunction {
    /**
     * The first coefficient of the power function
     */
    final double[] coeff0;

    /**
     * The second coefficient of the power function
     */
    final double[] coeff1;

    /**
     * Construct new power function with its coefficients
     *
     * @param coeff0 the first coefficient
     * @param coeff1 the second coefficient
     */
    public PowerFunction(double[] coeff0, double[] coeff1) {
        assert coeff0.length == coeff1.length;

        this.coeff0 = coeff0;
        this.coeff1 = coeff1;
    }

    /**
     * Get Value
     *
     * @param t Time Variable
     * @return the pseudo random value
     */

    /**
     * Returns the value of the power function at time t
     *
     * @param t the time variable
     * @return the value of the power function at time t
     */
    public double get(double t) {
        int N = coeff0.length;

        double sum = 0;
        for (int n = 0; n < N; n++) {
            sum += Math.cos(coeff0[n] * t + coeff1[n]);
        }

        return (sum / (2 * N)) + 0.5;
    }

    /**
     * Generates a random power function with given size.
     *
     * @param random the random generator
     * @param N      the size of the power function
     * @return the created random power function
     */
    public static PowerFunction generate(Random random, int N) {
        double[] coeff0 = new double[N];
        double[] coeff1 = new double[N];

        for (int n = 0; n < N; n++) {
            coeff0[n] = 10 * random.nextDouble();
            coeff1[n] = 10 * random.nextDouble();
        }

        return new PowerFunction(coeff0, coeff1);
    }

    /**
     * Generates a random power function with size 4.
     *
     * @param random the random generator
     * @return the created random power function
     */
    public static PowerFunction generate(Random random) {
        return generate(random, 4);
    }
}
