package com.capton.enb;

/**
 * Created by capton on 2017/9/28.
 */
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class ValueComparator implements Comparator<Integer> {

    Map<Integer, Integer> base;

    public ValueComparator(Map<Integer, Integer> base) {
        this.base = base;
    }

    public static <K, V extends Comparable<V>> Map<K, V> sortByValues(
            final Map<K, V> map) {
        Comparator<K> valueComparator = new Comparator<K>() {
            public int compare(K k1, K k2) {
                int compare = map.get(k2).compareTo(map.get(k1));
                if (compare == 0)
                    return 1;
                else
                    return compare;
            }
        };
        Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }


    @Override
    public int compare(Integer o1, Integer o2) {
        if (base.get(o1).doubleValue() >= base.get(o2).doubleValue()) {
            return -1;
        } else {
            return 1;
        }
    }
}