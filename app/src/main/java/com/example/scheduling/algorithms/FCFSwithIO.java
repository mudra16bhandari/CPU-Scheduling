package com.example.scheduling.algorithms;

import com.example.scheduling.model.Input;
import com.example.scheduling.model.Output;
import com.example.scheduling.util.MergeSort;

import java.util.ArrayList;
import java.util.List;

public class FCFSwithIO {
    private List<Integer> cpuQueue;

    public Output[] getOutput(Input[] input){
       int[] sort = MergeSort.sort(input);
       int len = input.length;
       int last = input[sort[0]].getaTime();
       for (int i=0;i<len;i++){
           input[sort[i]].setTotalB(input[sort[i]].getbTime() + input[sort[i]].getbTime2());
       }
        Input[] inputCopy = new Input[len];
        for(int i=0;i<len;i++){
            inputCopy[i] = new Input(input[i]);
        }
       Output[] out = new Output[len];
       cpuQueue = new ArrayList<>();
       List<Integer> afterIO = new ArrayList<>();
       boolean[] comp = new boolean[len];
        for (int i = 0; i <len ; i++) {
            comp[i] = false;
        }
       cpuQueue.add(last);
       int count =0;
       while (count<len) {
           for (int i = 0; i < len; i++) {
               Output tmp = new Output();
               if (inputCopy[sort[i]].getaTime() > last) {
                   cpuQueue.add(-1);
                   last = input[sort[i]].getaTime();
                   cpuQueue.add(last);
                   i--;
                   if (!afterIO.isEmpty()) {
                       for (int j = 0; j < i; j++) {
                           if (inputCopy[sort[j]].getReturnTime() <= last && !afterIO.isEmpty() && inputCopy[sort[j]].getTotalB() != 0) {
                               cpuQueue.add(sort[j]);
                               inputCopy[sort[j]].setTotalB(0);
                               last = last + inputCopy[sort[j]].getbTime2();
                               System.out.println(inputCopy[sort[j]].getpName());
                               cpuQueue.add(last);
                               tmp.setTurnAround(last - inputCopy[sort[j]].getaTime());
                               tmp.setWaiting(tmp.getTurnAround() - input[sort[j]].getTotalB());
                               tmp.setCompletion(tmp.getTurnAround() + inputCopy[sort[j]].getaTime());
                               out[sort[j]] = tmp;
                               comp[j] = true;
                               count++;
                               afterIO.remove((Integer) sort[j]);
                           }
                       }
                   }
               }
               else{
                           if (!afterIO.isEmpty()) {
                               for (int j = 0; j < i; j++) {
                                   if (inputCopy[sort[j]].getReturnTime() <= last && !afterIO.isEmpty() && inputCopy[sort[j]].getTotalB() != 0) {
                                       cpuQueue.add(sort[j]);
                                       inputCopy[sort[j]].setTotalB(0);
                                       last = last + inputCopy[sort[j]].getbTime2();
                                       System.out.println(inputCopy[sort[j]].getpName());
                                       cpuQueue.add(last);
                                       tmp.setTurnAround(last - inputCopy[sort[j]].getaTime());
                                       tmp.setWaiting(tmp.getTurnAround() - input[sort[j]].getTotalB());
                                       tmp.setCompletion(tmp.getTurnAround() + inputCopy[sort[j]].getaTime());
                                       out[sort[j]] = tmp;
                                       comp[j] = true;
                                       count++;
                                       afterIO.remove((Integer) sort[j]);
                                   }
                               }
                           }if (inputCopy[sort[i]].getbTime() != 0) {
                           cpuQueue.add(sort[i]);
                           last = last + inputCopy[sort[i]].getbTime();
                           inputCopy[sort[i]].setbTime(0);
                           inputCopy[sort[i]].setTotalB(inputCopy[sort[i]].getbTime2());
                           inputCopy[sort[i]].setReturnTime(last + inputCopy[sort[i]].getIoTime());
                           afterIO.add(sort[i]);
                           cpuQueue.add(last);
                       } else {
                           if (inputCopy[sort[i]].getReturnTime() <= last && !comp[i]) {
                               cpuQueue.add(sort[i]);
                               //System.out.println(inputCopy[sort[i]].getpName());
                               last = last + inputCopy[sort[i]].getbTime2();
                               inputCopy[sort[i]].setTotalB(0);
                               out[sort[i]] = tmp;
                               cpuQueue.add(last);
                           }
                       }
                       if (inputCopy[sort[i]].getTotalB() == 0 && !comp[i]) {
                           tmp.setTurnAround(last - inputCopy[sort[i]].getaTime());
                           tmp.setWaiting(tmp.getTurnAround() - input[sort[i]].getTotalB());
                           tmp.setCompletion(tmp.getTurnAround() + inputCopy[sort[i]].getaTime());
                           comp[i] = true;
                           count++;
                       }
                   }
               }
           }
       return out;
    }

    public List<Integer> getCpuQueue() {
        return cpuQueue;
    }
}

