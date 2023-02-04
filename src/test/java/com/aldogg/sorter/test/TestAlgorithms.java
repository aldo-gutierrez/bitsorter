package com.aldogg.sorter.test;

import com.aldogg.sorter.Named;
import com.aldogg.sorter.Sorter;
import com.aldogg.sorter.generators.GeneratorParams;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class TestAlgorithms<T extends Named> {

    private final T[] algorithms;
    private final HashMap<String, Long> totalElapsed;
    private final HashMap<String, Long> count;

    public TestAlgorithms(T[] algorithms) {
        this.algorithms = algorithms;
        this.totalElapsed = new HashMap<>();
        this.count = new HashMap<>();
        for (T sorter : algorithms) {
            totalElapsed.put(sorter.getName(), 0L);
            count.put(sorter.getName(), 0L);
        }
    }

    public T[] getAlgorithms2() {
        return algorithms;
    }


    public void set(String name, long elapsedTime) {
        totalElapsed.put(name, totalElapsed.get(name) + elapsedTime);
        count.put(name, count.get(name) + 1);
    }

    public long getAVG(String name) {
        return totalElapsed.get(name) / count.get(name);
    }

    public List<Object[]> getWinners() {
        String sorterWinner = "";
        long sorterWinnerTime = 0;
        String sorter2ndWinner = "";
        long sorter2ndWinnerTime = 0;
        Named[] sorters = getAlgorithms2();
        for (int i = 0; i < sorters.length; i++) {
            Named sorter = sorters[i];
            String name = sorter.getName();
            if (i == 0) {
                sorterWinner = name;
                sorterWinnerTime = getAVG(name);
            }
            if (i == 1) {
                if (getAVG(name) < sorterWinnerTime) {
                    sorter2ndWinner = sorterWinner;
                    sorter2ndWinnerTime = sorterWinnerTime;
                    sorterWinner = name;
                    sorterWinnerTime = getAVG(name);
                } else {
                    sorter2ndWinner = name;
                    sorter2ndWinnerTime = getAVG(name);
                }
            } else {
                if (getAVG(name) < sorterWinnerTime) {
                    sorter2ndWinner = sorterWinner;
                    sorter2ndWinnerTime = sorterWinnerTime;
                    sorterWinner = name;
                    sorterWinnerTime = getAVG(name);
                } else if (getAVG(name) < sorter2ndWinnerTime) {
                    sorter2ndWinner = name;
                    sorter2ndWinnerTime = getAVG(name);
                }
            }
        }
        List<Object[]> result = new ArrayList<>();
        result.add(new Object[]{sorterWinner, sorterWinnerTime});
        result.add(new Object[]{sorter2ndWinner, sorter2ndWinnerTime});
        return result;
    }

    protected void printTestSpeed(GeneratorParams params, Writer writer) throws IOException {
        int size = params.size;
        int limitLow = params.limitLow;
        long limitHigh = params.limitHigh;
        Named[] sorters = algorithms;
        for (int i = 0; i < sorters.length; i++) {
            Named sorter = sorters[i];
            if (writer != null)
                writer.write(size + ",\"" + limitLow + ":" + limitHigh + "\",\"" + sorter.getName() + "\"," + getAVG(sorter.getName()) / 1000000 + "\n");
            if (writer != null) writer.flush();
        }
        System.out.printf("%,13d %18s %25s", size, limitLow + ":" + limitHigh, params.function.toString());
        for (int i = 0; i < sorters.length; i++) {
            Named sorter = sorters[i];
            System.out.printf("%21s %,13d ", sorter.getName(), getAVG(sorter.getName()));
        }
        List<Object[]> winners = getWinners();
        System.out.printf("%21s %,13d ", winners.get(0)[0], (Long) winners.get(0)[1]);
        System.out.printf("%21s %,13d ", winners.get(1)[0], (Long) winners.get(1)[1]);
        System.out.println();
    }

}
