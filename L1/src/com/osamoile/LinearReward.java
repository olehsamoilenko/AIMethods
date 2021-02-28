package com.osamoile;

import java.util.Arrays;

/**
 * Class contains the functionality of 'Linear Reward-Inaction' algorithm
 */
public class LinearReward {
    private RandomChooserWrapper randomChooser;
    private Double[] probs;

    /**
     * Constructor for LinearReward
     * @param randomChooser RandomChooserWrapper object, stored inside of LinearReward object. That allows to run
     *                      algorithm is different configurations without regeneration of action probabilities
     */
    LinearReward(RandomChooserWrapper randomChooser) {
        this.randomChooser = randomChooser;
        this.probs = new Double[randomChooser.getNumberOfActions()];
        Arrays.fill(this.probs, 1.0 / randomChooser.getNumberOfActions());
    }

    /**
     * Method runs the algorithm
     * @param alpha learning speed of the algorithm, [0, 1]
     * @param iterations number of iteration to run, -1 for absolute completion
     * @return vector of probabilities associated with actions by index. After absolute completion contains zeroes and
     * single 1 telling which action is the most probable
     */
    public Double[] run(double alpha, int iterations) {
        boolean ready = false;
        long counter = 0;

        while (!ready) {
            /* success of choise-th action */
            int choice = randomChooser.choose();
            for (int j = 0; j < probs.length; j++) {
                if (j == choice) {
                    probs[choice] = probs[choice] + alpha * (1 - probs[choice]);
                    if (probs[choice] >= 1.0) {
                        ready = true;
                    }
                } else {
                    probs[j] = probs[j] - alpha * probs[j];
                }
            }

            if (iterations > 0 && ++counter == iterations) {
                break;
            }
        }

        System.out.println("Ran " + iterations + " iterations with alpha = " + alpha);
        return probs;
    }
}
