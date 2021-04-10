package com.osamoile;

import mathcomp.oletsky.mathhelper.VectMatr;

import static mathcomp.oletsky.hierarchyanalysis.SaatiAnalyzer.calculateSaatiVectorByIntPreferences;

public class Main {

    public static void main(String[] args) {
        int[][] criateria_matr = {
                {1, 2},
                {   3}

        };
        double tau = 1.2;
        double[] criteria_weights = calculateSaatiVectorByIntPreferences(criateria_matr, tau);
        System.out.println("Criteria weights:");
        VectMatr.defaultOutputVector(criteria_weights);

        int[][] criteria_1_matr = {
                {1, 2, 3},
                {   3, 2},
                {      1}
        };
        int[][] criteria_2_matr = {
                {1, 2, 3},
                {   3, 2},
                {      1}
        };
        int[][] criteria_3_matr = {
                {2, 2, 1},
                {   1, 1},
                {      1}
        };
        double[] cr_1_est = calculateSaatiVectorByIntPreferences(criteria_1_matr, tau);
        double[] cr_2_est = calculateSaatiVectorByIntPreferences(criteria_2_matr, tau);
        double[] cr_3_est = calculateSaatiVectorByIntPreferences(criteria_3_matr, tau);
        System.out.println("Criteria 1 estimation:");
        VectMatr.defaultOutputVector(cr_1_est);
        System.out.println("Criteria 2 estimation:");
        VectMatr.defaultOutputVector(cr_2_est);
        System.out.println("Criteria 3 estimation:");
        VectMatr.defaultOutputVector(cr_3_est);

        double[] weighted = {0, 0, 0, 0};
        for (int i = 0; i < weighted.length; i++) {
            weighted[i] += cr_1_est[i] * criteria_weights[0];
            weighted[i] += cr_2_est[i] * criteria_weights[1];
            weighted[i] += cr_3_est[i] * criteria_weights[2];
        }
        System.out.println("Weighted estimation:");
        VectMatr.defaultOutputVector(weighted);

    }
}
