package com.example.scheduling.algorithms;


import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.scheduling.model.Output;
import com.example.scheduling.util.MergeSort;
import com.example.scheduling.model.Input;

import java.util.ArrayList;
import java.util.List;

public class FCFS {
    private List<Integer> cpuQueue;//CPU QUEUE

    //GET OUTPUT BASED ON FIRST COME FIRST SERVE ALGORITHM
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Output[] getOutput(Input[] input) {
        int[] sort = MergeSort.sort(input);
        int len = input.length;
        int last = input[sort[0]].getaTime();
        Output[] out = new Output[len];
        cpuQueue = new ArrayList<>();
        cpuQueue.add(last);
        for (int i = 0; i < len; i++) {
            Output tmp = new Output();
            if (input[sort[i]].getaTime() > last) {
                cpuQueue.add(-1);
                last = input[sort[i]].getaTime();
                cpuQueue.add(last);
                i--;
            } else {
                cpuQueue.add(sort[i]);//Push name
                last = last + input[sort[i]].getbTime();//evaluate ending time
                tmp.setTurnAround(last - input[sort[i]].getaTime());//evaluate turn around time
                tmp.setWaiting(tmp.getTurnAround() - input[sort[i]].getbTime());//evaluate waiting time
                tmp.setCompletion(tmp.getTurnAround()+input[sort[i]].getaTime());
                out[sort[i]] = tmp;
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
