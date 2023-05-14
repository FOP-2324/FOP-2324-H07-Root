package h07;

import h07.impl.ToUpperFormatter;

import java.util.Objects;

/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {
        String testString = "FOP for president!";

        MapExpression upperFormatterClass = new ToUpperFormatter();
        System.out.println(upperFormatterClass.map(testString));


        MapExpression upperFormatterLambda = string -> string.toUpperCase();
        System.out.println(upperFormatterLambda.map(testString));


        MapExpression upperFormatterReference = String::toUpperCase;
        System.out.println(upperFormatterReference.map(testString));



        // Log
        Log log = new Log();
        log.log(1, "Hallo FoPler!");
        log.log(6, "Error: Code 6 received!");
        log.log(3, "Warnung: Diese Uebung ist hiermit beendet\nOver and out!");
    }
}
