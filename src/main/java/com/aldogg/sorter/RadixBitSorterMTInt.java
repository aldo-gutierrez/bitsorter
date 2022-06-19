package com.aldogg.sorter;

import com.aldogg.parallel.SorterRunner;
import com.aldogg.sorter.intType.IntSorterUtils;

import java.util.ArrayList;
import java.util.List;

import static com.aldogg.sorter.BitSorterUtils.*;
import static com.aldogg.sorter.intType.IntSorterUtils.sortShortK;

public class RadixBitSorterMTInt extends RadixBitSorterInt {

    protected final BitSorterMTParams params = BitSorterMTParams.getMTParams();

    @Override
    public void sort(int[] array, int start, int end) {
        int n = end - start;
        if (n < 2) {
            return;
        }
        if (n <= params.getDataSizeForThreads() || params.getMaxThreads() <= 1) {
            RadixBitSorterInt radixBitSorterInt = new RadixBitSorterInt();
            radixBitSorterInt.setUnsigned(isUnsigned());
            radixBitSorterInt.sort(array);
            return;
        }
        int ordered = isUnsigned() ? listIsOrderedUnSigned(array, start, end) : listIsOrderedSigned(array, start, end);
        if (ordered == AnalysisResult.DESCENDING) {
            IntSorterUtils.reverse(array, start, end);
        }
        if (ordered != AnalysisResult.UNORDERED) return;

        int[] maskParts = getMaskBit(array, start, end);
        int mask = maskParts[0] & maskParts[1];
        int[] kList = getMaskAsArray(mask);
        if (kList.length == 0) {
            return;
        }
        sort(array, start, end, kList);
    }

    @Override
    public void sort(int[] array, int start, int end, int[] kList) {
        int maxThreadsBits = params.getMaxThreadsBits();
        if (kList[0] == 31) { //there are negative numbers and positive numbers
            int sortMask = 1 << kList[0];
            int finalLeft = isUnsigned()
                    ? IntSorterUtils.partitionNotStable(array, start, end, sortMask)
                    : IntSorterUtils.partitionReverseNotStable(array, start, end, sortMask);
            int size1 = finalLeft - start;
            int size2 = end - finalLeft;
            SorterRunner.runTwoRunnable(
                    size1 > 1 ? () -> { //sort negative numbers
                        int[] maskParts1 = getMaskBit(array, start, finalLeft);
                        int mask1 = maskParts1[0] & maskParts1[1];
                        int[] kList1 = getMaskAsArray(mask1);
                        sort(array, start, finalLeft, kList1, 0, maxThreadsBits - 1);
                    } : null, size1,
                    size2 > 1 ? () -> { //sort positive numbers
                        int[] maskParts2 = getMaskBit(array, finalLeft, end);
                        int mask2 = maskParts2[0] & maskParts2[1];
                        int[] kList2 = getMaskAsArray(mask2);
                        sort(array, finalLeft, end, kList2, 0, maxThreadsBits - 1);
                    } : null, size2, params.getDataSizeForThreads(), 0, null);

        } else {
            sort(array, start, end, kList, 0, maxThreadsBits);
        }
    }

    public void sort(final int[] array, final int start, final int end, int[] kList, int kIndex, int paramsMaxThreadBits) {
        int kDiff = kList.length - kIndex;
        if (kDiff <= params.getShortKBits()) {
            if (kDiff < 1) {
                return;
            }
            sortShortK(array, start, end, kList, kIndex);
            return;
        }

        int n = end - start;
        int[] aux2 = new int[n];

        int threadBits = 0;
        int sortMask1 = 0;
        int maxThreadBits = Math.min(Math.max(paramsMaxThreadBits, 0), kList.length) - 1;
        for (int i = maxThreadBits; i >= 0; i--) {
            int kListI = kList[i];
            int sortMaskI = 1 << kListI;
            sortMask1 = sortMask1 | sortMaskI;
            threadBits++;
        }
        partitionStableNonConsecutiveBitsAndRadixSort(array, start, end, sortMask1, threadBits, kList, aux2);
    }

    protected void partitionStableNonConsecutiveBitsAndRadixSort(final int[] list, final int start, final int end, int sortMask, int threadBits, int[] kList, final int[] aux) {
        int maxProcessNumber = 1 << threadBits;
        int remainingBits = kList.length - threadBits;

        int[] kListAux = getMaskAsArray(sortMask);
        Section[] sections = getMaskAsSections(kListAux);

        int[] leftX = new int[maxProcessNumber];
        int[] count = new int[maxProcessNumber];


        if (sections.length == 1) {
            Section section = sections[0];
            if (section.isSectionAtEnd()) {
                for (int i = start; i < end; i++) {
                    int element = list[i];
                    int elementMaskedShifted = element & section.sortMask;
                    count[elementMaskedShifted]++;
                }
            } else {
                for (int i = start; i < end; i++) {
                    int element = list[i];
                    int elementMaskedShifted = (element & section.sortMask) >> section.shiftRight;
                    count[elementMaskedShifted]++;
                }
            }
            for (int i = 1; i < maxProcessNumber; i++) {
                leftX[i] = leftX[i - 1] + count[i - 1];
            }
            if (section.isSectionAtEnd()) {
                for (int i = start; i < end; i++) {
                    int element = list[i];
                    int elementMaskedShifted = element & section.sortMask;
                    aux[leftX[elementMaskedShifted]] = element;
                    leftX[elementMaskedShifted]++;
                }
            } else {
                for (int i = start; i < end; i++) {
                    int element = list[i];
                    int elementMaskedShifted = (element & section.sortMask) >> section.shiftRight;
                    aux[leftX[elementMaskedShifted]] = element;
                    leftX[elementMaskedShifted]++;
                }
            }
        } else {
            for (int i = start; i < end; i++) {
                int element = list[i];
                int elementMaskedShifted = getKeySN(element, sections);
                count[elementMaskedShifted]++;
            }
            for (int i = 1; i < maxProcessNumber; i++) {
                leftX[i] = leftX[i - 1] + count[i - 1];
            }
            for (int i = start; i < end; i++) {
                int element = list[i];
                int elementMaskedShifted = getKeySN(element, sections);
                aux[leftX[elementMaskedShifted]] = element;
                leftX[elementMaskedShifted]++;
            }
        }


        System.arraycopy(aux, 0, list, start, end - start);
        if (remainingBits > 0) {
            List<Runnable> runInThreadList = new ArrayList<>();
            List<Thread> threadList = new ArrayList<>();
            for (int i = 0; i < maxProcessNumber; i++) {
                int finalI = i;
                int lengthT = count[finalI];
                if (lengthT > 1) {
                    Runnable r = () -> {
                        int endT = leftX[finalI];
                        if (remainingBits <= params.getShortKBits()) {
                            sortShortK(list, start + endT - lengthT, start + endT, kList, threadBits);
                        } else {
                            int[] auxT = new int[lengthT];
                            RadixBitSorterInt.radixSort(list, start + endT - lengthT, start + endT, kList, kList.length - 1, threadBits, auxT);
                        }
                    };
                    runInThreadList.add(r);
                }
            }

            for (int i = 0; i < runInThreadList.size(); i++) {
                Runnable r = runInThreadList.get(i);
                if (i == runInThreadList.size() - 1) {
                    r.run();
                } else {
                    Thread t = new Thread(r);
                    t.start();
                    threadList.add(t);
                }
            }


            for (int t = 0; t < threadList.size(); t++) {
                try {
                    threadList.get(t).join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
