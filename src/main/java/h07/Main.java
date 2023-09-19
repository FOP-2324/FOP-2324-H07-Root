package h07;

import h07.expression.MapExpression;
import h07.expression.impl.ToUpperFormatter;

import java.util.Random;

/**
 * Main entry point in executing the program.
 */
public class Main {

    private final static String TEST_STRING = "FOP for president!";

    /**
     *
     * @return normal implementation of {@link  MapExpression}
     */
    public static MapExpression testNormal(){
        return new ToUpperFormatter();
    }


    /**
     *
     * @return lambda implementation of {@link  MapExpression}
     */
    public static MapExpression testLambda(){
        return string -> string.toUpperCase();
    }


    /**
     *
     * @return shortcut for lambda of {@link  MapExpression} using method reference
     */
    public static MapExpression testMethodReference(){
        return String::toUpperCase;
    }


    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) throws InterruptedException {

        System.out.println("H2.2: ");
        System.out.println(testNormal().map(TEST_STRING));
        System.out.println(testLambda().map(TEST_STRING));
        System.out.println(testMethodReference().map(TEST_STRING));
        System.out.println();


        // Log
        System.out.println("H4.X:");
        Log log = new MaintenanceLog();
        log.log(1, "Hallo FoPler!");
        log.log(6, "Error: Code 6 received!");
        log.log(3, "Warnung: Diese Uebung ist hiermit beendet\nOver and out!");




        PowerPlant powerPlant = new PowerPlant(log, new Random(42), 10);

        for(int i = 0; i < 10; i++){
            powerPlant.check(i / 100.0);
            Thread.sleep(1500);
        }


    }
}
