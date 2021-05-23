package com.osamoile;

import tester.AprioryTester;
import tester.ItemSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        final String FNAME = "transact.csv";
        final float THRESHOLD = 0.1f;
        int MAX_GROUP_SIZE = 4;
        ArrayList<ArrayList<Integer>> transact = AprioryTester.readFromCSV(FNAME);
        ArrayList<Integer> items = AprioryTester.formSet(1, 2, 3, 4, 5, 6, 7, 8);
        ArrayList<ItemSet> nElementsSets = null;

        int numbTransact = transact.size();
        System.out.println("Number of transactions: " + numbTransact);

        for (int n = 1; n <= MAX_GROUP_SIZE; n++) {
            ArrayList<ArrayList<Integer>> res;
            if (nElementsSets == null) {
                res = allCombinations(new ArrayList<>(), items, n);
            } else {
                res = allCombinations(new ArrayList<>(), uniqueElements(nElementsSets), n);
            }

            nElementsSets = new ArrayList<>();
            for (ArrayList<Integer> i: res) {
                ItemSet next = new ItemSet();
                next.set = i;
                next.number = AprioryTester.countSet(next.set, transact);
                next.support = next.number / (float)numbTransact;
                nElementsSets.add(next);
            }

//            printSet(nElementsSets);
            filterSet(nElementsSets, THRESHOLD);
            printSet(nElementsSets);
        }
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

    static void printSet(ArrayList<ItemSet> importantOneSets) {
        if (importantOneSets.size() >= 1) {
            System.out.println("Sets with " + importantOneSets.get(0).set.size() + " element(s):");
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
}