package com.example.scheduling.algorithms;

import com.example.scheduling.model.Input;
import com.example.scheduling.model.Output;
import com.example.scheduling.util.MergeSort;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class LRTF{

    private List<Integer> cpuQueue;

    public Output[] getOutput(final Input[] input){
        int len = input.length;
        Input[] inputCopy = new Input[len];
        for(int i=0;i<len;i++){
            inputCopy[i] = new Input(input[i]);
        }
        int[] sort = MergeSort.sort(input);
        int last = inputCopy[sort[0]].getaTime();
        cpuQueue = new LinkedList<>();
        LinkedList<Integer> readuQueue = new LinkedList<>();
        cpuQueue.add(last);
        int count = 0;
        int i = 0;
        Output[] out = new Output[len];
        while (count<len){
            for (;i<len;i++){
                if (inputCopy[sort[i]].getaTime() <= last && inputCopy[sort[i]].getbTime()!=0){
                    readuQueue.add(sort[i]);
                }
                else break;
            }
            if(!readuQueue.isEmpty()){
                int current = readuQueue.get(0); //first element in ready queue is current
                int tmp; // index of max btime process
                int max = 0;  // gives on which index in ready queue is max btime process
                Iterator<Integer> iterator = readuQueue.listIterator();
                for(int j=0;j<readuQueue.size();j++){
                    if (inputCopy[tmp = iterator.next()].getbTime() > inputCopy[current].getbTime()) {
                        current = tmp;
                        max = j;
                    }
                }
                cpuQueue.add(current);
                int currentEnd = last + inputCopy[current].getbTime();
                if (i<len && currentEnd > inputCopy[sort[i]].getaTime()){
                    inputCopy[current].setbTime(inputCopy[current].getbTime()-inputCopy[sort[i]].getaTime() + last);
                    last = inputCopy[sort[i]].getaTime();
                }
                else {
                    inputCopy[current].setbTime(inputCopy[current].getbTime()-1);
                    last = last + 1;
                    if (inputCopy[current].getbTime()==0) {
                        readuQueue.remove(max);
                        out[current] = new Output();
                        last = currentEnd;
                        out[current].setTurnAround(last - inputCopy[current].getaTime());
                        out[current].setWaiting(out[current].getTurnAround() - input[current].getbTime());
                        out[current].setCompletion(out[current].getTurnAround() + input[current].getaTime());
                        count++;
                    }
                }
                cpuQueue.add(last);
            }
            else {
                cpuQueue.add(-1);
                last = inputCopy[sort[i]].getaTime();
                cpuQueue.add(last);
                System.out.println("Ready Queue Empty");
            }
        }
        return out;
    }
    public  List<Integer> getCpuQueue(){
        return cpuQueue;
    }
}