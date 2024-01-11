package com.aldogg.sorter.long_;

import com.aldogg.sorter.FieldSorterOptions;
import com.aldogg.sorter.shared.Sorter;

import java.util.List;
import java.util.ListIterator;

public interface SorterLong extends Sorter {

    default void sort(long[] array) {
        sort(array, 0, array.length, new FieldSorterOptions() {
        });
    }

    void sort(long[] array, int start, int endP1, FieldSorterOptions options);

    default void sort(List<Long> list) {
        sort(list, 0, list.size());
    }

    default void sort(List<Long> list, int start, int endP1) {
        int n = endP1 - start;
        long[] a = new long[n];
        int j = 0;
        List<Long> subList = start == 0 && endP1 == list.size() ? list : list.subList(start, endP1);
        for (Long value : subList) {
            a[j] = value;
            j++;
        }
        sort(a, 0, n, new FieldSorterOptions() {
        });
        j = 0;
        ListIterator<Long> iterator = subList.listIterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.set(a[j]);
            j++;
        }
    }

}