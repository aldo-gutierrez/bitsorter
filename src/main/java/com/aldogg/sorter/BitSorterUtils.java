package com.aldogg.sorter;

import com.aldogg.sorter.int_.IntSorterUtils;

import java.util.*;

public class BitSorterUtils {


    public static int getKeySN(final int element, final Section[] sections) {
        int result = 0;
        for (int i = 0; i < sections.length; i++) {
            Section section = sections[i];
            int mask = MaskInfoInt.getMaskRangeBits(section.start, section.shift);
            int bits = (element & mask) >> section.shift;
            result = result << section.bits | bits;
        }
        return result;
    }

    public static Section[] getMaskAsSections(final int[] bList, final int bListStart, final int bListEnd) {
        LinkedHashMap<Integer, Integer> sectionsMap = new LinkedHashMap<>();
        int currentSection = -1;
        for (int i = bListStart; i <= bListEnd; i++) {
            int bIndex = bList[i];
            if (i == bListStart) {
                sectionsMap.put(bIndex, 1);
                currentSection = bIndex;
            } else {
                if (bList[i - 1] - bIndex == 1) {
                    sectionsMap.put(currentSection, sectionsMap.get(currentSection) + 1);
                } else {
                    sectionsMap.put(bIndex, 1);
                    currentSection = bIndex;
                }
            }
        }
        Section[] sections = new Section[sectionsMap.size()];
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : sectionsMap.entrySet()) {
            Section section = new Section();
            section.start = entry.getKey();
            section.bits = entry.getValue();
            section.shift = section.start - section.bits + 1;
            sections[i] = section;
            i++;
        }
        return sections;
    }

    public static Section[] getProcessedSections(int[] bListParam, int bListStart, int bListEnd, int maxBitsDigit) {
        int[] bList = Arrays.copyOfRange(bListParam, bListStart, bListEnd + 1);
        IntSorterUtils.reverse(bList, 0, bList.length);
        List<Section> sections = new ArrayList<>();
        Section section = new Section();
        section.shift = bList[0];
        section.bits = 1;
        int b = 1;
        while (b < bList.length) {
            int bitIndex = bList[b];
            if (bitIndex <= section.shift + maxBitsDigit - 1) {
                section.bits = (bitIndex - section.shift + 1);
            } else {
                sections.add(section);
                section = new Section();
                section.shift = bitIndex;
                section.bits = 1;
            }
            b++;
        }
        sections.add(section);
        for (Section sectionX : sections) {
            sectionX.start = sectionX.shift + sectionX.bits - 1;
        }
        return sections.toArray(new Section[]{});
    }

    public static int logBase2(int n) // returns 0 for bits=0
    {
        int log = 0;
        if ((n & 0xffff0000) != 0) {
            n >>>= 16;
            log = 16;
        }
        if (n >= 256) {
            n >>>= 8;
            log += 8;
        }
        if (n >= 16) {
            n >>>= 4;
            log += 4;
        }
        if (n >= 4) {
            n >>>= 2;
            log += 2;
        }
        return log + (n >>> 1);
    }
}
