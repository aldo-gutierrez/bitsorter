package com.aldogg.sorter;

import java.util.*;

public class BitSorterUtils {

    public static int[] getMaskBit(final int[] array, final int start, final int end) {
        int mask = 0x00000000;
        int inv_mask = 0x00000000;
        for (int i = start; i < end; i++) {
            int ei = array[i];
            mask = mask | ei;
            inv_mask = inv_mask | (~ei);
        }
        return new int[]{mask, inv_mask};
    }

    public static int[] getMaskAsArray(final int mask) {
        List<Integer> list = new ArrayList<>();
        for (int i = 31; i >= 0; i--) {
            if (((mask >> i) & 1) == 1) {
                list.add(i);
            }
        }
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    public static int getMaskLastBits(final int[] kList, final int kIndex) {
        int mask = 0;
        for (int i = kIndex; i < kList.length; i++) {
            int k = kList[i];
            mask = mask | 1 << k;
        }
        return mask;
    }

    public static int getMaskRangeBits(final int kIndexStart, final int kIndexEnd) {
        int mask = 0;
        for (int k = kIndexStart; k >= kIndexEnd; k--) {
            mask = mask | 1 << k;
        }
        return mask;
    }

    public static int getKeySN(final int element, final Section[] sections) {
        int result = 0;
        for (int i = 0; i < sections.length; i++) {
            Section section = sections[i];
            int length = section.length;
            int sortMask = section.sortMask;
            int shiftRight = section.shiftRight;
            int bits = (element & sortMask) >> shiftRight;
            result = result << length | bits;
        }
        return result;
    }

    public static Section[] getMaskAsSections(final int[] kList, int kStart, int kEnd) {
        LinkedHashMap<Integer, Integer> sections = new LinkedHashMap<>();
        int currentSection = -1;
        for (int i = kStart; i <= kEnd; i++) {
            int k = kList[i];
            if (i == kStart) {
                sections.put(k, 1);
                currentSection = k;
            } else {
                if (kList[i - 1] - k == 1) {
                    sections.put(currentSection, sections.get(currentSection) + 1);
                } else {
                    sections.put(k, 1);
                    currentSection = k;
                }
            }
        }
        Section[] sectionsAsInts = new Section[sections.size()];
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : sections.entrySet()) {
            sectionsAsInts[i] = new Section();
            sectionsAsInts[i].k = entry.getKey();
            sectionsAsInts[i].length = entry.getValue();
            int aux = entry.getKey() - entry.getValue() + 1;
            sectionsAsInts[i].sortMask = getMaskRangeBits(entry.getKey(), aux);
            sectionsAsInts[i].shiftRight = aux;
            i++;
        }
        return sectionsAsInts;
    }


    public static Section[] splitSection(Section section) {
        if (section.length <= BitSorterMTParams.MAX_BITS_RADIX_SORT) {
            return new Section[]{section};
        } else {
            List<Section> sections = new ArrayList<>();
            int sectionQuantity;
            if (section.length % BitSorterMTParams.MAX_BITS_RADIX_SORT == 0) {
                sectionQuantity = section.length / BitSorterMTParams.MAX_BITS_RADIX_SORT;
            } else {
                sectionQuantity = (section.length / BitSorterMTParams.MAX_BITS_RADIX_SORT) + 1;
            }
            int sectionSize = section.length / sectionQuantity;
            int sizeAux = 0;
            for (int i = 0; i < sectionQuantity; i++) {
                Section sectionAux = new Section();
                sectionAux.length = (i < sectionQuantity - 1) ? sectionSize : section.length - (sectionSize * (sectionQuantity - 1));
                sectionAux.k = section.k - sizeAux;
                sizeAux+=sectionAux.length;
                sections.add(sectionAux);
            }
            for (int i= sectionQuantity-1; i >=0; i--) {
                Section sectionAux = sections.get(i);
                sectionAux.shiftRight = (i == sectionQuantity - 1) ? section.shiftRight : sections.get(i+1).shiftRight + sections.get(i+1).length;
                sectionAux.sortMask = getMaskRangeBits(sectionAux.k, sectionAux.k - sectionAux.length + 1);
            }
            return sections.toArray(new Section[]{});
        }
    }


    public static int listIsOrderedSigned(final int[] array, final int start, final int end) {
        int i1 = array[start];
        int i = start + 1;
        while (i < end) {
            int i2 = array[i];
            if (i2 != i1) {
                break;
            }
            i1 = i2;
            i++;
        }
        if (i == end) {
            return AnalysisResult.ALL_EQUAL;
        }

        //ascending
        i1 = array[i];
        if (array[i-1] < i1) {
            i++;
            for (; i < end; i++)  {
                int i2 = array[i];
                if (i1 > i2) {
                    break;
                }
                i1 = i2;
            }
            if (i == end) {
                return AnalysisResult.ASCENDING;
            }
        }
        //descending
        else {
            i++;
            for (; i < end; i++)  {
                int i2 = array[i];
                if (i1 < i2) {
                    break;
                }
                i1 = i2;
            }
            if (i == end) {
                return AnalysisResult.DESCENDING;
            }
        }
        return AnalysisResult.UNORDERED;
    }

    public static int listIsOrderedUnSigned(int[] array, int start, int end) {
        int i1 = array[start];
        int i = start + 1;
        while (i < end) {
            int i2 = array[i];
            if (i2 != i1) {
                break;
            }
            i1 = i2;
            i++;
        }
        if (i == end) {
            return AnalysisResult.ALL_EQUAL;
        }

        //ascending
        i1 = array[i];
        if (array[i-1] < i1) {
            i++;
            for (; i < end; i++)  {
                int i2 = array[i];
                if (i1 + 0x80000000 > i2 + 0x80000000) {
                    break;
                }
                i1 = i2;
            }
            if (i == end) {
                return AnalysisResult.ASCENDING;
            }
        }
        //descending
        else {
            i++;
            for (; i < end; i++)  {
                int i2 = array[i];
                if (i1 + 0x80000000 < i2 + 0x80000000) {
                    break;
                }
                i1 = i2;
            }
            if (i == end) {
                return AnalysisResult.DESCENDING;
            }
        }
        return AnalysisResult.UNORDERED;
    }

}
