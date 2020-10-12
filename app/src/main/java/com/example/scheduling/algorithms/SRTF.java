package com.example.scheduling.algorithms;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.scheduling.model.Input;
import com.example.scheduling.model.Output;
import com.example.scheduling.util.MergeSort;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SRTF {

    private List<Integer> cpuQueue;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Output[] getOutput(final Input[] input) {
        int len = input.length;
        Input[] inputCopy = new Input[len];
        for (int i=0;i<len;i++) {
            inputCopy[i] = new Input(input[i]);
        }
        int[] sort = MergeSort.sort(inputCopy);
        int last = inputCopy[sort[0]].getaTime();
        cpuQueue = new LinkedList<>();
        LinkedList<Integer> readyQueue = new LinkedList<>();
        cpuQueue.add(last);
        int count = 0;
        int i = 0;
        Output[] out = new Output[len];
        while (count < len) {
            for (; i < len; i++) {
                if (inputCopy[sort[i]].getaTime() <= last && inputCopy[sort[i]].getbTime() != 0) {
                    readyQueue.add(sort[i]);
                } else break;
            }
            if (!readyQueue.isEmpty()) {
                int current = readyQueue.get(0);
                int tmp;
                int min = 0;
                Iterator<Integer> iterator = readyQueue.listIterator();
                for (int j = 0; j < readyQueue.size(); j++)
                    if (inputCopy[(tmp = iterator.next())].getbTime() < inputCopy[current].getbTime()) {
                        current = tmp;
                        min = j;
                    }
                cpuQueue.add(current);
                int currentEnd = last + inputCopy[current].getbTime();
                if (i < len && currentEnd > inputCopy[sort[i]].getaTime()) {
                    inputCopy[current].setbTime(inputCopy[current].getbTime() - inputCopy[sort[i]].getaTime() + last);
                    last = inputCopy[sort[i]].getaTime();
                } else {
                    inputCopy[current].setbTime(0);
                    readyQueue.remove(min);
                    out[current] = new Output();
                    last = currentEnd;
                    out[current].setTurnAround(last - inputCopy[current].getaTime());
                    out[current].setWaiting(out[current].getTurnAround() - input[current].getbTime());
                    out[current].setCompletion(out[current].getTurnAround()+input[current].getaTime());
                    count++;
                }
                cpuQueue.add(last);
            } else {
                cpuQueue.add(-1);
                last = inputCopy[sort[i]].getaTime();
                cpuQueue.add(last);
            }
        }
        return out;
    }
    public List<Integer> getCpuQueue() {
        return cpuQueue;
    }
}
