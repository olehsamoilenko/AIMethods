package com.osamoile;

import tester.AprioryTester;
import tester.ItemSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Main {

    static void printSet(ArrayList<ItemSet> importantOneSets) {

        if (importantOneSets.size() >= 1) {
            System.out.println("Sets with " + importantOneSets.get(0).set.size() + " element(s):");
//            Collections.sort(importantOneSets,
//                    (s1,s2)->s2.number-s1.number);

            for (ItemSet itemSet : importantOneSets) {
                System.out.println(itemSet.set + String.format(" - %2d - %8.3f", itemSet.number, itemSet.support));
            }
        } else {
            System.out.println("No suitable itemsets");
        }
    }

    static void filterSet(ArrayList<ItemSet> set, float threshold) {
        for (int i = set.size() - 1; i >= 0; i--) {
            if (set.get(i).support < threshold) {
                set.remove(i);
            }
        }
    }

    public static void main(String[] args) {
        final String FNAME = "transact.csv";
        final float THRESHOLD = 0.1f;
        ArrayList<ArrayList<Integer>> transact = AprioryTester.readFromCSV(FNAME);
        int numbTransact = transact.size();
        System.out.println("Number of transactions: " + numbTransact);

        ArrayList<Integer> items = AprioryTester.formSet(1, 2, 3, 4, 5, 6, 7, 8);
        ArrayList<ItemSet> oneElementSets = new ArrayList<>();
        for (int i : items) {
            ItemSet next = new ItemSet();
            next.set = AprioryTester.formSet(i);
            next.number = AprioryTester.countSet(next.set, transact);
            next.support = next.number / (float) numbTransact;
            oneElementSets.add(next);
        }
//        printSet(oneElementSets);
        filterSet(oneElementSets, THRESHOLD);
        printSet(oneElementSets);

        ArrayList<ItemSet> twoElementSets = new ArrayList<>();
        for (ItemSet i : oneElementSets) {
            for (ItemSet j : oneElementSets) {
                if (i.set.get(0) < j.set.get(0)) {
                    ItemSet next = new ItemSet();
                    next.set = AprioryTester.formSet(i.set.get(0), j.set.get(0));
                    next.number = AprioryTester.countSet(next.set, transact);
                    next.support = next.number / (float) numbTransact;
                    twoElementSets.add(next);
                }
            }
        }
        filterSet(twoElementSets, THRESHOLD);
        printSet(twoElementSets);

        ArrayList<ItemSet> threeElementsSet = new ArrayList<>();
        ArrayList<ArrayList<Integer>> res = allCombinations(new ArrayList<Integer>(), uniqueElements(twoElementSets), 3);
        for (ArrayList<Integer> i: res) {
            ItemSet next = new ItemSet();
            next.set = i;
            next.number = AprioryTester.countSet(next.set, transact);
            next.support = next.number / (float)numbTransact;
            threeElementsSet.add(next);
        }

        filterSet(threeElementsSet, THRESHOLD);
        printSet(threeElementsSet);




//        ArrayList<Integer> items = AprioryTester.formSet(1, 2, 3, 4);
//        ArrayList<ArrayList<Integer>> res = allCombinations(new ArrayList<Integer>(), items, 2);
//        for (ArrayList<Integer> i: res) {
//            System.out.printf("[");
//            for (Integer j : i) {
//                System.out.printf(" %d,", j);
//            }
//            System.out.println("]");
//        }
    }

    static ArrayList<Integer> uniqueElements(ArrayList<ItemSet> trans) {
        Set<Integer> set = new HashSet<>();
        for (ItemSet i: trans) {
            for (int j: i.set) {
                set.add(j);
            }
        }
        return new ArrayList<>(set);
    }

    static ArrayList<ArrayList<Integer>> allCombinations(ArrayList<Integer> inProgress,
                                                         ArrayList<Integer> archive,
                                                         int byAmount) {
//        System.out.printf("[");
//        for (Integer i : inProgress) {
//            System.out.printf(" %d,", i);
//        }
//        System.out.printf("], [");
//        for (Integer i : archive) {
//            System.out.printf(" %d,", i);
//        }
//        System.out.println("]");

        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        if (inProgress.size() == byAmount) {
//            System.out.println("Done");
            res.add(inProgress);
            return res;
        }

        while (archive.size() > 0) {
            ArrayList<Integer> newInProgress = (ArrayList<Integer>) inProgress.clone();
            newInProgress.add(archive.get(0));
            archive.remove(0);
            ArrayList<Integer> newArchive = (ArrayList<Integer>) archive.clone();

            if (newInProgress.size() + newArchive.size() >= byAmount) {
                res.addAll(allCombinations(newInProgress, newArchive, byAmount));
            }
        }

        return res;
    }
}