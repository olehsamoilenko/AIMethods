package com.osamoile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        RandomChooserWrapper randomChooser = new RandomChooserWrapper(5);
        var linearReward = new LinearReward(randomChooser);

        Double[] res;
        System.out.println("\nTiny alpha:");
        res = linearReward.run(0.03, 1000000);
        printVector(res);
        randomChooser.verifyMostProbableAction(mostProbableAction(res));

        System.out.println("\nHuge alpha:");
        res = linearReward.run(0.4, 1000000);
        printVector(res);
        randomChooser.verifyMostProbableAction(mostProbableAction(res));
    }

    private static void printVector(Double[] vector) {
        System.out.print("Result vector:           [");
        for (var i: vector) {
            System.out.printf(" %.2f", i);
        }
        System.out.println(" ]");
    }

    private static int mostProbableAction(Double[] vector) {
        List<Double> tmp = new ArrayList<Double>(Arrays.asList(vector));
        return tmp.indexOf(Collections.max(tmp));
    }
}

