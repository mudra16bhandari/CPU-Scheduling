package com.example.scheduling.algorithms;

import com.example.scheduling.model.Input;
import com.example.scheduling.model.Output;
import com.example.scheduling.util.MergeSort;

import java.util.ArrayList;
import java.util.List;

public class FCFSwithIO {
    private List<Integer> cpuQueue;

    public Output[] getOutput(Input[] input) {
        int[] sort = MergeSort.sort(input);
        int len = input.length;
        int last = input[sort[0]].getaTime();
        for (int i = 0; i < len; i++) {
            input[sort[i]].setTotalB(input[sort[i]].getbTime() + input[sort[i]].getbTime2());
        }
        Input[] inputCopy = new Input[len];
        for (int i = 0; i < len; i++) {
            inputCopy[i] = new Input(input[i]);
        }
        Output[] out = new Output[len];
        cpuQueue = new ArrayList<>();
        List<Integer> afterIO = new ArrayList<>();
        boolean[] comp = new boolean[len];
        for (int i = 0; i < len; i++) {
            comp[i] = false;
        }
        cpuQueue.add(last);
        int count = 0;
        while (count < len) {
            for (int i = 0; i < len; i++) {
                System.out.println("In first Loop");
                Output tmp = new Output();
                if (!afterIO.isEmpty()) {
                    System.out.println("After IO not empty");
                    for (int j = 0; j <= i; j++) {
                        if(inputCopy[sort[j]].getReturnTime()>last){
                            System.out.println("Should be Empty");
                        }
                        else{
                            if (inputCopy[sort[j]].getbTime() == 0 && !afterIO.isEmpty() && inputCopy[sort[j]].getReturnTime() <= last &&
                                    afterIO.contains(sort[j]) && inputCopy[sort[j]].getTotalB()!=0) {
                                System.out.println("Condition1 satisfied");
                                if(inputCopy[sort[j]].getReturnTime() <= inputCopy[sort[i]].getaTime()) {
                                    System.out.println("After IO Process executed " + inputCopy[sort[j]].getpName() + " " + sort[j] + " " + j);
                                    cpuQueue.add(sort[j]);
                                    inputCopy[sort[j]].setTotalB(0);
                                    last = last + inputCopy[sort[j]].getbTime2();
                                    tmp.setTurnAround(last - inputCopy[sort[j]].getaTime());
                                    tmp.setWaiting(tmp.getTurnAround() - input[sort[j]].getTotalB());
                                    tmp.setCompletion(tmp.getTurnAround() + inputCopy[sort[j]].getaTime());
                                    out[sort[j]] = tmp;
                                    cpuQueue.add(last);
                                    int index = afterIO.indexOf(sort[j]);
                                    afterIO.remove(index);
                                    comp[sort[j]] = true;
                                    count++;
                                }
                                else {
                                    if(sort[j]<=sort[i]){
                                        System.out.println("After IO Process executed " + inputCopy[sort[j]].getpName() + " " + sort[j] + " " + j);
                                        cpuQueue.add(sort[j]);
                                        inputCopy[sort[j]].setTotalB(0);
                                        last = last + inputCopy[sort[j]].getbTime2();
                                        tmp.setTurnAround(last - inputCopy[sort[j]].getaTime());
                                        tmp.setWaiting(tmp.getTurnAround() - input[sort[j]].getTotalB());
                                        tmp.setCompletion(tmp.getTurnAround() + inputCopy[sort[j]].getaTime());
                                        out[sort[j]] = tmp;
                                        cpuQueue.add(last);
                                        int index = afterIO.indexOf(sort[j]);
                                        afterIO.remove(index);
                                        comp[sort[j]] = true;
                                        count++;
                                    }
                                    else{
                                        if (inputCopy[sort[i]].getbTime() != 0) {
                                            System.out.println("Before IO executed " + inputCopy[sort[i]].getpName());
                                            cpuQueue.add(sort[i]);
                                            last = last + inputCopy[sort[i]].getbTime();
                                            inputCopy[sort[i]].setbTime(0);
                                            inputCopy[sort[i]].setTotalB(inputCopy[sort[i]].getbTime2());
                                            inputCopy[sort[i]].setReturnTime(last + inputCopy[sort[i]].getIoTime());
                                            afterIO.add(sort[i]);
                                            cpuQueue.add(last);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if(inputCopy[sort[i]].getReturnTime()>last){
                        System.out.println("Should be Empty");
                        //System.out.println("No process executed");
                        cpuQueue.add(-1);
                        last = last + 1;
                        cpuQueue.add(last);
                    }
                }

                if (inputCopy[sort[i]].getaTime() > last) {
                    System.out.println("No process executed");
                    cpuQueue.add(-1);
                    last = input[sort[i]].getaTime();
                    cpuQueue.add(last);
                    i--;
                } else {
                    if (inputCopy[sort[i]].getbTime() != 0) {
                        System.out.println("Before IO executed " + inputCopy[sort[i]].getpName());
                        cpuQueue.add(sort[i]);
                        last = last + inputCopy[sort[i]].getbTime();
                        inputCopy[sort[i]].setbTime(0);
                        inputCopy[sort[i]].setTotalB(inputCopy[sort[i]].getbTime2());
                        inputCopy[sort[i]].setReturnTime(last + inputCopy[sort[i]].getIoTime());
                        afterIO.add(sort[i]);
                        cpuQueue.add(last);
                    }
                }

                /*if(inputCopy[sort[i]].getTotalB()==0 && !comp[sort[i]]){
                    System.out.println("After IO Process executed "+inputCopy[sort[i]].getpName());
                    cpuQueue.add(sort[i]);
                    inputCopy[sort[i]].setTotalB(0);
                    last = last + inputCopy[sort[i]].getbTime2();
                    tmp.setTurnAround(last - inputCopy[sort[i]].getaTime());
                    tmp.setWaiting(tmp.getTurnAround() - input[sort[i]].getTotalB());
                    tmp.setCompletion(tmp.getTurnAround() + inputCopy[sort[i]].getaTime());
                    out[sort[i]] = tmp;
                    cpuQueue.add(last);
                    int index = afterIO.indexOf(sort[i]);
                    afterIO.remove(index);
                    count++;
                }*/
            }
        }
        return out;
    }

    public List<Integer> getCpuQueue() {
        return cpuQueue;
    }
}

