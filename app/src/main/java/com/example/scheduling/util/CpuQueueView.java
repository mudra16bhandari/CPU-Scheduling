package com.example.scheduling.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.scheduling.R;
import com.example.scheduling.model.Input;

import java.util.Iterator;
import java.util.List;

public class CpuQueueView extends ConstraintLayout {
    public CpuQueueView(Context context) {
        super(context);
    }

    public CpuQueueView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CpuQueueView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setUp(List<Integer> cpuQueue, Input[] input) {
        removeAllViews();
        Iterator<Integer> iterator = cpuQueue.iterator();
        ConstraintSet set = new ConstraintSet();
        int lastId = LayoutParams.PARENT_ID;

        TextView start = new TextView(getContext());
        start.setId(View.generateViewId());
        start.setText(String.valueOf(iterator.next()));
        addView(start);

        TextView pid = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.cpu_queue_textview, null);
        pid.setId(View.generateViewId());
        pid.setText(input[iterator.next()].getpName());
        addView(pid);

        TextView end = new TextView(getContext());
        end.setId(View.generateViewId());
        end.setText(String.valueOf(iterator.next()));
        addView(end);

        set.clone(this);
        set.connect(start.getId(), ConstraintSet.START, lastId, ConstraintSet.START);
        set.connect(pid.getId(), ConstraintSet.START, start.getId(), ConstraintSet.END);
        lastId = pid.getId();
        set.connect(start.getId(), ConstraintSet.TOP, lastId, ConstraintSet.BOTTOM);
        set.connect(end.getId(), ConstraintSet.START, lastId, ConstraintSet.END);
        set.connect(end.getId(), ConstraintSet.END, lastId, ConstraintSet.END);
        set.connect(end.getId(), ConstraintSet.TOP, lastId, ConstraintSet.BOTTOM);
        set.applyTo(this);
        while (iterator.hasNext()) {
            pid = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.cpu_queue_textview, null);
            pid.setId(View.generateViewId());
            int p = iterator.next();
            if (p != -1)
                pid.setText(input[p].getpName());
            else
                pid.setText("\t\t");
            addView(pid);
            end = new TextView(getContext());
            end.setText(String.valueOf(iterator.next()));
            end.setId(View.generateViewId());
            addView(end);
            set.clone(this);
            set.connect(pid.getId(), ConstraintSet.START, lastId, ConstraintSet.END);
            lastId = pid.getId();
            set.connect(end.getId(), ConstraintSet.START, lastId, ConstraintSet.END);
            set.connect(end.getId(), ConstraintSet.END, lastId, ConstraintSet.END);
            set.connect(end.getId(), ConstraintSet.TOP, lastId, ConstraintSet.BOTTOM);
            set.applyTo(this);
        }
    }
}
