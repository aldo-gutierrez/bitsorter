package com.aldogg.sorter.int_.collection;

import com.aldogg.sorter.Sorter;

import java.util.List;

public interface ObjectIntSorter extends Sorter {

    default void sort(Object[] arrayObject, IntComparator comparator) {
        sort(arrayObject, 0, arrayObject.length, comparator);
    }

    void sort(Object[] arrayObject, int start, int end, IntComparator comparator);


    default void sort(List<Object> list, IntComparator comparator) {
        sort(list, 0, list.size(), comparator);
    }

    default void sort(List<Object> list, int start, int end, IntComparator comparator) {
        int n = end - start;
        Object[] a = new Object[n];
        int j = 0;
        for (int i = start; i < end; i++, j++) {
            a[j] = list.get(i);
        }
        sort(a, 0, n, comparator);
        j = 0;
        for (int i = start; i < end; i++, j++) {
            list.set(i, a[j]);
        }
    }

}
