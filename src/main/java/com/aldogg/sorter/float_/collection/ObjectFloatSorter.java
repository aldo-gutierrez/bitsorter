package com.aldogg.sorter.float_.collection;

import com.aldogg.sorter.Sorter;

public interface ObjectFloatSorter extends Sorter {

    default void sort(Object[] arrayObject, FloatComparator comparator) {
        sort(arrayObject, 0, arrayObject.length, comparator);
    }

    void sort(Object[] arrayObject, int start, int endP1, FloatComparator comparator);

}
