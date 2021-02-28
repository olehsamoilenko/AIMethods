package com.osamoile;

import mathcomp.oletsky.randomchooser.RandomChooser;

import java.util.*;

/**
 * Class is a wrapper for RandomChooser.
 *
 * The main difference against the RandomChooser is encapsulated probabilities of actions, because in a real experiment
 * they are unknown, cannot be read, analyzed and especially edited.
 * @see RandomChooser
 */
public class RandomChooserWrapper {
    private final List<Double> probs = new ArrayList<>();

    /**
     * Constructor for RandomChooserWrapper
     *
     * @param numberOfActions number of actions in experiment. A probability for every acton is generated internally
     *                        inside of the RandomChooserWrapper
     */
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

    /**
     * Method is a wrapper for chooseByProps, probabilities are not needed as argument, they are encapsulated inside of
     * the RandomChooserWrapper
     *
     * @return number (or index) of chosen action
     */
    public int choose() {
        /* ArrayList to array, Java 8:
        https://www.geeksforgeeks.org/arraylist-array-conversion-java-toarray-methods/
         */
        double[] probsArray = probs.stream().mapToDouble(i -> i).toArray();
        return RandomChooser.chooseByProps(probsArray);
    }

    /**
     * Method is needed to verify calculated action as far as real probabilities are not accessible.
     * @param indexOfAction number (or index) of calculated action
     * @return true on action is correct, false otherwise
     */
    public boolean verifyMostProbableAction(int indexOfAction) {
        if (indexOfAction == probs.indexOf(Collections.max(probs))) {
            System.out.println("Correct, " + indexOfAction + " is the most probable action");
            return true;
        } else {
            System.out.println("Failure, incorrect action selected");
            return false;
        }
    }

    /**
     * @return amount of actions were passed to constructor
     */
    public int getNumberOfActions() {
        return probs.size();
    }
}
