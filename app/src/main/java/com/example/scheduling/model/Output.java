package com.example.scheduling.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Output implements Parcelable {
    private int turnAround;//Turn around time
    private int waiting;//Waiting time
    private int completion;

    protected Output(Parcel in) {
        turnAround = in.readInt();
        waiting = in.readInt();
        completion = in.readInt();
    }

    public Output() {

    }

    public Output(int wt, int tat,int ct) {
        this.waiting = wt;
        this.turnAround = tat;
        this.completion = ct;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(turnAround);
        dest.writeInt(waiting);
        dest.writeInt(completion);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Output> CREATOR = new Creator<Output>() {
        @Override
        public Output createFromParcel(Parcel in) {
            return new Output(in);
        }

        @Override
        public Output[] newArray(int size) {
            return new Output[size];
        }
    };

    //GETTERS AND SETTERS
    public int getTurnAround() {
        return turnAround;
    }

    public void setTurnAround(int turnAround) {
        this.turnAround = turnAround;
    }

    public int getWaiting() {
        return waiting;
    }

    public void setWaiting(int waiting) {
        this.waiting = waiting;
    }

    public  void setCompletion(int completion){
        this.completion = completion;
    }
    public int getCompletion(){
        return completion;
    }

    //GET AVERAGE TURN AROUND TIME OF LIST PASSED
    public static float getAverageTurnAround(Output[] outputs) {
        float avg = 0;
        for (Output o : outputs) {
            avg += o.turnAround;
        }
        avg /= outputs.length;
        return Float.valueOf(String.format("%.2f", avg));
    }

    //GET AVERAGE WAITING TIME OF LIST PASSED
    public static float getAverageWaitingTime(Output[] outputs) {
        float avg = 0;
        for (Output o : outputs) {
            avg += o.waiting;
        }
        avg /= outputs.length;
        return Float.valueOf(String.format("%.2f", avg));
    }
}
