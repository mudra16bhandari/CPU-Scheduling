package com.example.scheduling.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class Input implements Parcelable {
    private String pName;//Process Name
    private int priority;//Priority
    private int aTime;//Arrival Time
    private int bTime;//Burst Time
    private int ioTime;
    private int bTime2;
    private int totalB;
    private int returnTime;

    public int getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(int returnTime) {
        this.returnTime = returnTime;
    }




    //COPY CONSTRUCTOR
    public Input(Input in) {
        pName = in.pName;
        aTime = in.aTime;
        bTime = in.bTime;
        priority = in.priority;
        ioTime = in.ioTime;
        bTime2 = in.bTime2;

    }

    //DEFAULT CONSTRUCTOR
    public Input() {

    }

    protected Input(Parcel in) {
        pName = in.readString();
        aTime = in.readInt();
        bTime = in.readInt();
        ioTime = in.readInt();
        bTime2 = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pName);
        dest.writeInt(aTime);
        dest.writeInt(bTime);
        dest.writeInt(ioTime);
        dest.writeInt(bTime2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Input> CREATOR = new Creator<Input>() {
        @Override
        public Input createFromParcel(Parcel in) {
            return new Input(in);
        }

        @Override
        public Input[] newArray(int size) {
            return new Input[size];
        }
    };

    //COMPARE TWO Input Objects on the basis of arrival time
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public int compareTo(Input o) {
        return Integer.compare(aTime, o.aTime);
    }

    //GETTERS AND SETTERS
    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public int getaTime() {
        return aTime;
    }

    public void setaTime(int aTime) {
        this.aTime = aTime;
    }

    public int getbTime() {
        return bTime;
    }

    public void setbTime(int bTime) {
        this.bTime = bTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
    public int getIoTime() {
        return ioTime;
    }

    public void setIoTime(int ioTime) {
        this.ioTime = ioTime;
    }

    public int getbTime2() {
        return bTime2;
    }

    public void setbTime2(int bTime2) {
        this.bTime2 = bTime2;
    }
    public int getTotalB() {
        return totalB;
    }

    public void setTotalB(int totalB) {
        this.totalB = totalB;
    }

}
