package com.example.scheduling.algorithms;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.scheduling.model.Input;
import com.example.scheduling.model.Output;
import com.example.scheduling.util.MergeSort;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SJF {
    private List<Integer> cpuQueue;//CPU QUEUE

    //GET OUTPUT BASED ON SHORTEST JOB FIRST ALGORITHM
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Output[] getOutput(final Input[] input) {
        int[] sort = MergeSort.sort(input);
        int last = input[sort[0]].getaTime();
        cpuQueue = new LinkedList<>();
        LinkedList<Integer> readyQueue = new LinkedList<>();
        cpuQueue.add(last);
        int len = input.length;
        boolean[] completed = new boolean[len];
        int count = 0;
        int i = 0;
        Output[] out = new Output[len];
        while (count < len) {
            for (; i < len; i++) {
                if (input[sort[i]].getaTime() <= last && !completed[sort[i]]) {
                    readyQueue.add(sort[i]);
                } else break;
            }
            if (!readyQueue.isEmpty()) {
                int current = readyQueue.get(0);
                int tmp;
                int min = 0;
                Iterator<Integer> iterator = readyQueue.listIterator();
                for (int j = 0; j < readyQueue.size(); j++)
                    if (input[(tmp = iterator.next())].getbTime() < input[current].getbTime()) {
                        current = tmp;
                        min = j;
                    }
                readyQueue.remove(min);
                out[current] = new Output();
                cpuQueue.add(current);
                completed[current] = true;
                count++;
                out[current].setWaiting(last - input[current].getaTime());
                last = last + input[current].getbTime();
                out[current].setTurnAround(last - input[current].getaTime());
                out[current].setCompletion(out[current].getTurnAround()+input[current].getaTime());
                cpuQueue.add(last);
            } else {
                cpuQueue.add(-1);
                last = input[sort[i]].getaTime();
                cpuQueue.add(last);
            }
        }
        return out;
    }
    public List<Integer> getCpuQueue() {
        return cpuQueue;
    }

}
