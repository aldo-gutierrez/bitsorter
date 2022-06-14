package com.aldogg.sorter.sorters;


import com.aldogg.sorter.intType.IntSorter;

import java.util.Arrays;

public class JavaSorterInt implements IntSorter {
    @Override
    public void sort(int[] array) {
        Arrays.sort(array);
    }

    @Override
    public void sort(int[] array, int start, int end) {
        Arrays.sort(array, start, end);
    }

    @Override
    public void setUnsigned(boolean unsigned) {
        throw new UnsupportedOperationException();
    }
}
