package com.osamoile;

import mathcomp.oletsky.randomchooser.RandomChooser;

import java.util.*;

public class RandomChooserWrapper {
    private final List<Double> probs = new ArrayList<>();

    public RandomChooserWrapper(int numberOfActions) {
        /* random generation of values associated with actions */
        Random rd = new Random();
        double[] valuesOfActions = new double[numberOfActions];
        for (int i = 0; i < valuesOfActions.length; i++) {
            valuesOfActions[i] = rd.nextDouble();
        }

        /* converting values to probabilities */
        System.out.print("Generated probabilities: [");
        double[] probsArray = RandomChooser.getProbsByValues(valuesOfActions);
        for(var prob: probsArray) {
            System.out.printf(" %.2f", prob);
            probs.add(prob);
        }
        System.out.println(" ]");
    }

    public int choose() {
        /* ArrayList to array, Java 8:
        https://www.geeksforgeeks.org/arraylist-array-conversion-java-toarray-methods/
         */
        double[] probsArray = probs.stream().mapToDouble(i -> i).toArray();
        return RandomChooser.chooseByProps(probsArray);
    }

    public boolean verifyMostProbableAction(int indexOfAction) {
        if (indexOfAction == probs.indexOf(Collections.max(probs))) {
            System.out.println("Correct, " + indexOfAction + " is the most probable action");
            return true;
        } else {
            System.out.println("Failure, incorrect action selected");
            return false;
        }
    }

    public int getNumberOfActions() {
        return probs.size();
    }
}
