package com.osamoile;

import java.util.Arrays;

public class LinearReward {
    private RandomChooserWrapper randomChooser;
    private Double[] probs;

    LinearReward(RandomChooserWrapper randomChooser) {
        this.randomChooser = randomChooser;
        this.probs = new Double[randomChooser.getNumberOfActions()];
        Arrays.fill(this.probs, 1.0 / randomChooser.getNumberOfActions());
    }

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
