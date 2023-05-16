package com.aldogg.sorter.int_.st;

import com.aldogg.sorter.int_.IntBitMaskSorter;
import com.aldogg.sorter.int_.IntSorter;

import static com.aldogg.sorter.BitSorterUtils.logBase2;


/*
Algorithm Selector Sorter
It chooses the best algorithm to use depending on N and r (range = 2^m)
SorterTest2.speedTestPositiveIntSTBase2 generates this logic
 */
public class AGSelectorSorterInt extends IntBitMaskSorter {
    static final Class[][] sorterClasses = new Class[][]{
            {RadixByteSorterInt.class, QuickBitSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, RadixByteSorterInt.class, QuickBitSorterInt.class, RadixBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, QuickBitSorterInt.class, RadixByteSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, RadixBitSorterInt.class, QuickBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixByteSorterInt.class, QuickBitSorterInt.class, RadixBitSorterInt.class, QuickBitSorterInt.class, RadixByteSorterInt.class, QuickBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class,},
            {RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class,},
            {RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class,},
            {RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class,},
            {RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class,},
            {RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class,},
            {RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class,},
            {RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class,},
            {RadixBitSorterInt.class, QuickBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class,},
            {RadixBitSorterInt.class, QuickBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, QuickBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class,},
            {RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class,},
            {RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class,},
            {RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class,},
            {RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class,},
            {RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class,},
            {RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class,},
            {RadixBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class,},
            {RadixBitSorterInt.class, QuickBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class,},
            {RadixBitSorterInt.class, QuickBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, QuickBitSorterInt.class,},
            {RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class,},
            {RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, QuickBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class,},
            {RadixBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class,},
            {RadixBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class,},
            {RadixBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class,},
            {RadixBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class,},
            {RadixBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class,},
            {RadixBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class,},
            {RadixBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, QuickBitSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class, RadixByteSorterInt.class,},

    };

    @Override
    public void sort(int[] array, int start, int endP1, int[] bList, Object multiThreadParams) {
        int n = endP1 - start;
        int bLength = bList.length;
        int bLengthM1 = bLength - 1; //Log2(K)
        int log2Nm1 = logBase2(n) - 1; //Log2(N)
        if (n < 2) {
            return;
        }
        if (log2Nm1 > 27) {
            log2Nm1 = 27;
        }
        if (bLengthM1 > 27) {
            bLengthM1 = 27;
        }
        IntSorter sorter;
        try {
            sorter = ((Class<IntSorter>) sorterClasses[bLengthM1][log2Nm1]).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        sorter.setUnsigned(unsigned);
        sorter.sort(array, start, endP1, bList, null);
    }

}
