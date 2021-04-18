package tester;

import java.util.ArrayList;

public class ItemSet {
    public ArrayList<Integer> set;
    public int number;
    public double support;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemSet itemSet = (ItemSet) o;

        return set.equals(itemSet.set);
    }

    @Override
    public int hashCode() {
        return set.hashCode();
    }

}
