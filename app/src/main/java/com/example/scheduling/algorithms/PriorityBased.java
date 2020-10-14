package com.example.scheduling.algorithms;


import com.example.scheduling.model.Input;
import com.example.scheduling.model.Output;
import com.example.scheduling.util.MergeSort;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PriorityBased {
    private List<Integer> cpuQueue;//CPU QUEUE

    //GET OUTPUT BASED ON NON-PREEMPTIVE PRIORITY ALGORITHM
    public Output[] getNonPreemptive(final Input[] input) {
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
                int highest = 0;
                Iterator<Integer> iterator = readyQueue.listIterator();
                for (int j = 0; j < readyQueue.size(); j++)
                    if (input[(tmp = iterator.next())].getPriority() > input[current].getPriority()) {
                        current = tmp;
                        highest = j;
                    }
                readyQueue.remove(highest);
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

    //GET OUTPUT BASED ON PREEMPTIVE PRIORITY ALGORITHM
    public Output[] getPreemptive(final Input[] input) {
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
                int max = 0;
                Iterator<Integer> iterator = readyQueue.listIterator();
                for (int j = 0; j < readyQueue.size(); j++)
                    if (inputCopy[(tmp = iterator.next())].getPriority() > inputCopy[current].getPriority()) {
                        current = tmp;
                        max= j;
                    }
                cpuQueue.add(current);
                int currentEnd = last + inputCopy[current].getbTime();
                if (i < len && currentEnd > inputCopy[sort[i]].getaTime()) {
                    inputCopy[current].setbTime(inputCopy[current].getbTime() - inputCopy[sort[i]].getaTime() + last);
                    last = inputCopy[sort[i]].getaTime();
                } else {
                    inputCopy[current].setbTime(0);
                    readyQueue.remove(max);
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

    //GETTER FOR CPU QUEUE
    public List<Integer> getCpuQueue() {
        return cpuQueue;
    }
}
