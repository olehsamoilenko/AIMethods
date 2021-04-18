package tester;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

class Apriori {

}

public class AprioryTester {
    public static void main(String[] args) {

        //BasicSet
        ArrayList<Integer> items =
                formSet(1, 2, 3, 4, 5, 6, 7, 8);
        int numbItems = items.size();

        //Transactions dataset
        final String FNAME = "transact.csv";
        ArrayList<ArrayList<Integer>> transact =
                readFromCSV(FNAME);

        int numbTransact = transact.size();

        for (ArrayList<Integer> l : transact) {
            System.out.println(l);
        }

        System.out.println("Number of transactions - "+numbTransact);
        int maxLen =0;

        for (ArrayList<Integer> l : transact) {
            if (l.size()>maxLen) maxLen=l.size();
        }
        System.out.println("Max length of transaction is "+maxLen);

        System.out.println("-------------------");

        double treshold=0.2;

        //Important one element collections
        ArrayList<ItemSet> oneElementSets =
                new ArrayList<>();
        ArrayList<ItemSet> importantOneSets =
                new ArrayList<>();

        //One-element itemsets
        for (int i : items) {
            ItemSet next = new ItemSet();
            next.set=formSet(i);
            next.number=countSet(next.set,transact);
            next.support=next.number/(numbTransact+0.);
            oneElementSets.add(next);
        }

        for (ItemSet cand:oneElementSets) {
            if (cand.support>treshold) {
                importantOneSets.add(cand);
            }
        }
        System.out.println("Important one element sets: ");

        if (importantOneSets.size()>=1) {
            Collections.sort(importantOneSets,
                    (s1,s2)->s2.number-s1.number);

            for (ItemSet itemSet:importantOneSets) {
                System.out.println(
                        itemSet.set+
                                " - "+ itemSet.number+
                                " - "+
                                String.format("%8.3f",itemSet.support)
                );
            }
        }
        else {
            System.out.println("No suitable itemsets");
        }

        System.out.println("---------------------");

        ArrayList<ArrayList<ItemSet>> table =
                new ArrayList<>();
        table.add(importantOneSets);
        ArrayList<ItemSet> nextSet = new ArrayList<>();
        table.add(nextSet);
        for (ItemSet set1:table.get(0))
            for (ItemSet set2:importantOneSets) {
                int n1=set1.set.get(set1.set.size()-1);
                int n2=set2.set.get(0);
                ItemSet newSet = null;
                if (n1<n2) {
                    newSet=new ItemSet();
                    newSet.set=formSet(n1,n2);
                    newSet.number=countSet(newSet.set,transact);
                    newSet.support=newSet.number/(numbTransact+0.);
                    if (newSet.support>treshold)
                        table.get(1).add(newSet);
                }
            }

        //Forming final set of one and two elements

        ArrayList<ItemSet> finalSet = new ArrayList<>();
        finalSet.addAll(table.get(0));

        finalSet.addAll(table.get(1));

        System.out.println("Important one and two element sets: ");

        if (finalSet.size()>=1) {
            Collections.sort(finalSet,
                    (s1,s2)->s2.number-s1.number);

            for (ItemSet itemSet:finalSet) {
                System.out.println(
                        itemSet.set+
                                " - "+ itemSet.number+
                                " - "+
                                String.format("%8.3f",itemSet.support)
                );
            }
        }
        else {
            System.out.println("No suitable itemsets");
        }

        System.out.println("---------------------");


    }


    public static ArrayList<Integer> formSet(int... elems) {
        ArrayList<Integer> res = new ArrayList<>();
        for (int el : elems) {
            res.add(el);
        }
        return res;
    }

    static int count(int item, ArrayList<ArrayList<Integer>> items) {
        int cnt = 0;
        for (ArrayList<Integer> list : items) {
            if (list.contains(item)) cnt++;
        }
        return cnt;
    }

    public static int countSet(ArrayList<Integer> checkList,
                        ArrayList<ArrayList<Integer>> items) {
        int cnt = 0;
        for (ArrayList<Integer> list : items) {
            if (list.containsAll(checkList)) cnt++;
        }
        return cnt;
    }

    public static ArrayList<ArrayList<Integer>> readFromCSV(String fName) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        try(BufferedReader br =new BufferedReader(
                new FileReader(fName)

        )) {
            String line = null;

            while ((line = br.readLine()) != null) {
                String[] arr=line.split(";");
                ArrayList<Integer> sarr = new ArrayList<>();
                for (String s:arr) {
                    int ii = Integer.parseInt(s);
                    sarr.add(ii);
                }
                list.add(sarr);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}

