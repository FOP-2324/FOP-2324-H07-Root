package h07;

import java.util.Random;

public class PowerFunction {

    final double[] coeff0;
    final double[] coeff1;


    public PowerFunction(double[] coeff0, double[] coeff1){
        assert coeff0.length == coeff1.length;

        this.coeff0 = coeff0;
        this.coeff1 = coeff1;
    }

    public double get(double t){
        int N = coeff0.length;

        double sum = 0;
        for(int n = 0; n < N; n++){
            sum += Math.cos(coeff0[n] * t + coeff1[n]);
        }

        return (sum / (2 * N)) + 0.5;
    }

    public static PowerFunction generate(Random random, int N){
        double[] coeff0 = new double[N];
        double[] coeff1 = new double[N];

        for(int n = 0; n < N; n++){
            coeff0[n] = 10 * random.nextDouble();
            coeff1[n] = 10 * random.nextDouble();
        }

        return new PowerFunction(coeff0, coeff1);
    }

    public static PowerFunction generate(Random random){
        return generate(random, 4);
    }


}
