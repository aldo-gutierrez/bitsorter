package com.aldogg.collection;

import java.util.Arrays;

public class JavaSorterObject implements ObjectSorter {
    @Override
    public void sort(Object[] list, IntComparator comparator) {
        Arrays.sort(list, comparator);
    }
}