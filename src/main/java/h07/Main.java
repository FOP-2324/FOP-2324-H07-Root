package h07;

import h07.expression.MapExpression;
import h07.expression.impl.ToUpperFormatter;

import java.util.Random;

/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * THe string used for testing purposes.
     */
    private final static String TEST_STRING = "FOP for president!";

    /**
     * Returns the normal map expression, more formally using the constructor to create a new instance of the class.
     *
     * @return the normal map expression using the constructor to create a new instance of the class
     */
    public static MapExpression testNormal() {
        return new ToUpperFormatter();
    }

    /**
     * Returns the lambda expression, more formally using the lambda expression to create a new instance of the class.
     *
     * @return the lambda expression using the lambda expression to create a new instance of the class
     */
    public static MapExpression testLambda() {
        return string -> string.toUpperCase();
    }

    /**
     * Returns the method reference, more formally using the method reference to create a new instance of the class.
     *
     * @return the method reference using the method reference to create a new instance of the class
     */
    public static MapExpression testMethodReference() {
        return String::toUpperCase;
    }


    /**
     * Tests the different implementations of the {@link MapExpression} interface.
     */
    private static void test_h22() {
        System.out.println("H2.2: ");
        System.out.println(testNormal().map(TEST_STRING));
        System.out.println(testLambda().map(TEST_STRING));
        System.out.println(testMethodReference().map(TEST_STRING));
        System.out.println();
    }

    /**
     * Tests the log implementations.
     *
     * @throws InterruptedException if any thread has interrupted the current thread. The
     *                              <i>interrupted status</i> of the current thread is
     *                              cleared when this exception is thrown.
     */
    private static void test_h4() throws InterruptedException {
        System.out.println("H4:");
        Log log = new NormalLog(); //new MaintenanceLog();
        log.log(1, "Hallo FoPler!");
        log.log(6, "Error: Code 6 received!");
        log.log(3, "Warnung: Diese Uebung ist hiermit beendet\nOver and out!");

        PowerPlant powerPlant = new PowerPlant(log, new Random(42), 10);

        for (int i = 0; i < 10; i++) {
            powerPlant.check(i / 100.0);
            Thread.sleep(1500);
        }
    }


    /**
     * Tests the log implementations.
     *
     * @throws InterruptedException if any thread has interrupted the current thread. The
     *                              <i>interrupted status</i> of the current thread is
     *                              cleared when this exception is thrown.
     */
    private static void test_h5() throws InterruptedException {
        System.out.println("H5:");
        Log log = new NormalLog(); //new MaintenanceLog();
        PowerPlant powerPlant = new PowerPlant(log, new Random(42), 10);

        for (int i = 0; i < 10; i++) {
            powerPlant.check(i / 100.0);
            Thread.sleep(1500);
        }
    }


    /**
     * Main entry point in executing the program.
     *
     * @param args command line arguments
     * @throws InterruptedException if any thread has interrupted the current thread. The
     *                              <i>interrupted status</i> of the current thread is
     *                              cleared when this exception is thrown.
     */
    public static void main(String[] args) throws InterruptedException {
        test_h22();
        test_h4();
        test_h5();
    }
}
