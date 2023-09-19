package h07;

import java.util.Random;

public class PowerPlant {

    final Log log;
    final Random random;

    final Reactor[] reactors;

    public PowerPlant(Log log, Random random, int numOfReactors){
        this.log = log;
        this.random = random;

        reactors = new Reactor[numOfReactors];
        for(int i = 0; i < numOfReactors; i++){
            reactors[i] = Reactor.generate(i, random);
        }

    }

    public PowerPlant(Log log, int numOfReactors){
        this(log, new Random(), numOfReactors);
    }

    public void check(double t){
        for(Reactor reactor : reactors){

            log.log(0, reactor.toString() + ": Power = " + reactor.getPower(t));

            if(reactor.getPower(t) > 0.75){
                log.log(6, reactor.toString() + ": Overpowerd!");
            }

            if(reactor.needMaintenance(t)){
                log.log(3, reactor.toString() + ": Needs maintenance!");
            }




        }
    }

}
