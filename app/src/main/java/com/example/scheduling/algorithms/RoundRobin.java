package com.example.scheduling.algorithms;

import com.example.scheduling.model.Input;
import com.example.scheduling.model.Output;
import com.example.scheduling.util.MergeSort;

import java.util.LinkedList;
import java.util.List;

public class RoundRobin {
    private List<Integer> cpuQueue;//CPU QUEUE

    //GET OUTPUT BASED ON ROUND ROBIN ALGORITHM
    public Output[] getOutput(final Input[] input, int quantum) {
        int len = input.length;
        Input[] inputCopy = new Input[len];
        for (int i = 0; i < len; i++) {
            inputCopy[i] = new Input(input[i]);
        }
        int[] sort = MergeSort.sort(inputCopy);
        int last = inputCopy[sort[0]].getaTime();

        cpuQueue = new LinkedList<>();
        LinkedList<Integer> readyQueue = new LinkedList<>();
        cpuQueue.add(last);
        int current = 0;
        boolean toBeAdded=false;
        int count = 0;
        int i = 0;
        Output[] out = new Output[len];
        while (count < len) {
            for (; i < len; i++) {
                if (inputCopy[sort[i]].getaTime() <= last && inputCopy[sort[i]].getbTime() != 0) {
                    readyQueue.add(sort[i]);
                } else break;
            }
            if (toBeAdded)
                readyQueue.add(current);
            toBeAdded=false;
            if (!readyQueue.isEmpty()) {
                current = readyQueue.get(0);
                cpuQueue.add(current);
                if (inputCopy[current].getbTime() > quantum) {
                    last += quantum;
                    inputCopy[current].setbTime(inputCopy[current].getbTime() - quantum);
                    readyQueue.remove(0);
                    toBeAdded=true;
                } else {
                    last += inputCopy[current].getbTime();
                    inputCopy[current].setbTime(0);
                    readyQueue.remove(0);
                    out[current] = new Output();
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
