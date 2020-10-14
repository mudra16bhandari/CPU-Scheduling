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

    //COPY CONSTRUCTOR
    public Input(Input in) {
        pName = in.pName;
        aTime = in.aTime;
        bTime = in.bTime;
        priority = in.priority;
    }

    //DEFAULT CONSTRUCTOR
    public Input() {

    }

    protected Input(Parcel in) {
        pName = in.readString();
        aTime = in.readInt();
        bTime = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pName);
        dest.writeInt(aTime);
        dest.writeInt(bTime);
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
}
