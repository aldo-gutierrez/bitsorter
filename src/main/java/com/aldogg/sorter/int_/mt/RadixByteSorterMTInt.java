package com.aldogg.sorter.int_.mt;

import com.aldogg.sorter.SortingNetworks;
import com.aldogg.sorter.int_.IntBitMaskSorter;
import com.aldogg.sorter.int_.IntBitMaskSorterMT;
import com.aldogg.sorter.int_.st.RadixByteSorterInt;

/**
 * If first byte contains enough bits for the number of threads to sort then splits the threads for that byte
 * otherwise uses two bytes, so the maximum number of threads in parallel is 512 one byte + one bit 2^9
 * In each thread it does RadixByteSort by the remaining bytes, 1, 2 or 3 bytes
 */
public class RadixByteSorterMTInt extends IntBitMaskSorterMT {

    @Override
    public void sort(int[] array, int start, int endP1, int[] kList, Object multiThreadParams) {
        //TODO
    }

    @Override
    public IntBitMaskSorter getSTIntSorter() {
        RadixByteSorterInt sorter = new RadixByteSorterInt();
        sorter.setUnsigned(isUnsigned());
        sorter.setSNFunctions(isUnsigned() ? SortingNetworks.unsignedSNFunctions : SortingNetworks.signedSNFunctions);
        return sorter;
    }

}