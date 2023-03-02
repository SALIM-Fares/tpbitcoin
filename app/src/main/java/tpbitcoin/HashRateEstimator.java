package tpbitcoin;

import org.bitcoinj.core.Sha256Hash;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class HashRateEstimator {
    private final int duration; //duration of each experience, in milliseconds
    private final int numberOfTries; // number of experience

    /** Create a new object for estimating the number of SHA256 hashs the host can perform per second
    ** @param duration: (milli-seconds) duration of each experiment
     * @param numberOfTries : number of experiments to run
     */
    public HashRateEstimator(int duration, int numberOfTries) {
        this.duration = duration;
        this.numberOfTries = numberOfTries;
    }

    /**
     * @return : return the hashrate (hash/second)
     */
    public double estimate(){
        byte[] bytes;
        MessageDigest md = Sha256Hash.newDigest();

        double totalTime = 0.0;
        double hashRate;
        byte[] shaArray = new byte[256];
        for(int index = 0; index < numberOfTries;index++){
            double startTime = System.currentTimeMillis();
            md.update(shaArray);
            byte[] shaDigest = md.digest();
            totalTime += startTime - System.currentTimeMillis();
        }
        hashRate = totalTime / (numberOfTries*1000);
        return hashRate;
    }

}
