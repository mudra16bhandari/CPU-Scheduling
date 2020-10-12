package com.example.scheduling;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.scheduling.algorithms.FCFS;
import com.example.scheduling.algorithms.LJF;
import com.example.scheduling.algorithms.SJF;
import com.example.scheduling.algorithms.SRTF;
import com.example.scheduling.model.Input;
import com.example.scheduling.model.Output;
import com.example.scheduling.util.CpuQueueView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.view.View.VISIBLE;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "processScheduling";
    String[] algoritms;
    Spinner algoClass;
    TableLayout processTable;
    CpuQueueView cpuQueueView;
    ConstraintLayout outputContainer;
    TextView avgTurnAround;
    TextView avgWaiting;
    Button go;
    Parcelable[] p;
    LinearLayout tr;
    List<TableRow> rows;
    Input[] input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        Bundle b = getIntent().getExtras();
        p = b.getParcelableArray("input");
        input = toInputObject(p);
        p = b.getParcelableArray("output");
        algoClass = findViewById(R.id.algo_class);
        processTable = findViewById(R.id.processTable);
        go = findViewById(R.id.go);
        cpuQueueView = findViewById(R.id.cpu_queue);
        tr = findViewById(R.id.sum_row);
        outputContainer = findViewById(R.id.outputContainer);
        rows = new ArrayList<>();
        algoritms = new String[]{"Select algorithm", "FCFS", "SJF", "SRTF", "LJF" ,"LRTF" , "Priority" , "Round robin"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_textview, algoritms);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        algoClass.setAdapter(adapter);
        execute();
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOuput();
            }
        });
    }

    void getOuput() {
        int type = algoClass.getSelectedItemPosition();
        if (type == 0) {
            Toast.makeText(this, "Please select algorithm type", Toast.LENGTH_LONG).show();
        } else {
            }
        Output[] output=null;
            List<Integer> cpuQueue = null;
            switch (type) {
                case 1:
                    FCFS fcfs = new FCFS();
                    output = fcfs.getOutput(input);
                    cpuQueue = fcfs.getCpuQueue();
                    for (int i = 0; i < input.length; i++) {
                        final View rowView = getLayoutInflater().inflate(R.layout.sum_add, null, false);
                        TextView tat = (TextView) rowView.findViewById(R.id.sum_tat);
                        TextView wt = (TextView) rowView.findViewById(R.id.sum_wt);
                        tat.setText(String.valueOf(output[i].getTurnAround()));
                        wt.setText(String.valueOf(output[i].getWaiting()));
                        tr.addView(rowView);
                    }
                    break;
                case 2:
                    SJF sjf = new SJF();
                    output = sjf.getOutput(input);
                    cpuQueue = sjf.getCpuQueue();
                    for (int i = 0; i < input.length; i++) {
                        final View rowView = getLayoutInflater().inflate(R.layout.sum_add, null, false);
                        TextView wt = (TextView) rowView.findViewById(R.id.sum_wt);
                        TextView tat = (TextView) rowView.findViewById(R.id.sum_tat);
                        tat.setText(String.valueOf(output[i].getTurnAround()));
                        wt.setText(String.valueOf(output[i].getWaiting()));
                        tr.addView(rowView);
                    }
                    break;
                case 5:
                   /* PriorityBased priority = new PriorityBased();

                    else
                        output = priority.getPreemptive(input);
                    cpuQueue = priority.getCpuQueue();*/
                    break;
                case 3:
                    SRTF srtf = new SRTF();
                    output = srtf.getOutput(input);
                    cpuQueue = srtf.getCpuQueue();
                    for (int i = 0; i < input.length; i++) {
                        final View rowView = getLayoutInflater().inflate(R.layout.sum_add, null, false);
                        TextView wt = (TextView) rowView.findViewById(R.id.sum_wt);
                        TextView tat = (TextView) rowView.findViewById(R.id.sum_tat);
                        tat.setText(String.valueOf(output[i].getTurnAround()));
                        wt.setText(String.valueOf(output[i].getWaiting()));
                        tr.addView(rowView);
                    }
                    break;
                case 4:
                    LJF ljf = new LJF();
                    output = ljf.getOutput(input);
                    cpuQueue = ljf.getCpuQueue();
                    for (int i = 0; i < input.length; i++) {
                        final View rowView = getLayoutInflater().inflate(R.layout.sum_add, null, false);
                        TextView wt = (TextView) rowView.findViewById(R.id.sum_wt);
                        TextView tat = (TextView) rowView.findViewById(R.id.sum_tat);
                        tat.setText(String.valueOf(output[i].getTurnAround()));
                        wt.setText(String.valueOf(output[i].getWaiting()));
                        tr.addView(rowView);
                    }
                    break;
            }
            outputContainer.setVisibility(VISIBLE);
            /*if (type == 3) {
                summaryTable.getChildAt(0).findViewById(R.id.priority).setVisibility(VISIBLE);
            } else
                summaryTable.getChildAt(0).findViewById(R.id.priority).setVisibility(View.GONE);
            for (int i = 0; i < len - 1; i++) {
                TableRow row = (TableRow) summaryTable.getChildAt(i + 1);
                if (row == null) {
                    row = (TableRow) LayoutInflater.from(this).inflate(R.layout.summary_row, null);
                    summaryTable.addView(row);
                }
                if (type == 3) {
                    row.findViewById(R.id.priority).setVisibility(VISIBLE);
                    ((TextView) (row.findViewById(R.id.priority))).setText(String.valueOf(input[i].getPriority()));
                } else
                    row.findViewById(R.id.priority).setVisibility(View.GONE);
                ((TextView) (row.findViewById(R.id.pid))).setText(input[i].getpName());
                ((TextView) (row.findViewById(R.id.atime))).setText(String.valueOf(input[i].getaTime()));
                ((TextView) (row.findViewById(R.id.btime))).setText(String.valueOf(input[i].getbTime()));
                ((TextView) (row.findViewById(R.id.turnaround))).setText(String.valueOf(output[i].getTurnAround()));
                ((TextView) (row.findViewById(R.id.waiting))).setText(String.valueOf(output[i].getWaiting()));
            }

            int len2 = summaryTable.getChildCount();
            if (len2 > len) {
                for (int i = len; i < len2; i++) {
                    summaryTable.removeViewAt(len);
                }
            }*/
            cpuQueueView.setUp(cpuQueue, input);

            /*TableRow row = (TableRow) comparisionTable.getChildAt(1);
            if (row == null) {
                row = (TableRow) LayoutInflater.from(this).inflate(R.layout.comparision_row, null);
                ((TextView) row.findViewById(R.id.type)).setText("FCFS");
                comparisionTable.addView(row);
            }
            FCFS fcfs = new FCFS();
            Output[] output1 = fcfs.getOutput(input);
            ((TextView) row.findViewById(R.id.waiting)).setText(String.valueOf(Output.getAverageWaitingTime(output1)));
            ((TextView) row.findViewById(R.id.turnaround)).setText(String.valueOf(Output.getAverageTurnAround(output1)));

            row = (TableRow) comparisionTable.getChildAt(2);
            if (row == null) {
                row = (TableRow) LayoutInflater.from(this).inflate(R.layout.comparision_row, null);
                ((TextView) row.findViewById(R.id.type)).setText("SJF (Preemptive)");
                comparisionTable.addView(row);
            }
            SJF sjf = new SJF();
            output1 = sjf.getPreemptive(input);
            ((TextView) row.findViewById(R.id.waiting)).setText(String.valueOf(Output.getAverageWaitingTime(output1)));
            ((TextView) row.findViewById(R.id.turnaround)).setText(String.valueOf(Output.getAverageTurnAround(output1)));

            row = (TableRow) comparisionTable.getChildAt(3);
            if (row == null) {
                row = (TableRow) LayoutInflater.from(this).inflate(R.layout.comparision_row, null);
                ((TextView) row.findViewById(R.id.type)).setText("SJF (Non-preemptive)");
                comparisionTable.addView(row);
            }
            output1 = sjf.getNonPreemptive(input);
            ((TextView) row.findViewById(R.id.waiting)).setText(String.valueOf(Output.getAverageWaitingTime(output1)));
            ((TextView) row.findViewById(R.id.turnaround)).setText(String.valueOf(Output.getAverageTurnAround(output1)));

            row = (TableRow) comparisionTable.getChildAt(4);
            if (row == null) {
                row = (TableRow) LayoutInflater.from(this).inflate(R.layout.comparision_row, null);
                ((TextView) row.findViewById(R.id.type)).setText("Priority (Preemptive)");
                comparisionTable.addView(row);
            }
            PriorityBased priority = new PriorityBased();
            output1 = priority.getPreemptive(input);
            ((TextView) row.findViewById(R.id.waiting)).setText(String.valueOf(Output.getAverageWaitingTime(output1)));
            ((TextView) row.findViewById(R.id.turnaround)).setText(String.valueOf(Output.getAverageTurnAround(output1)));

            row = (TableRow) comparisionTable.getChildAt(5);
            if (row == null) {
                row = (TableRow) LayoutInflater.from(this).inflate(R.layout.comparision_row, null);
                ((TextView) row.findViewById(R.id.type)).setText("Priority (Non-preemptive)");
                comparisionTable.addView(row);
            }
            output1 = priority.getNonPreemptive(input);
            ((TextView) row.findViewById(R.id.waiting)).setText(String.valueOf(Output.getAverageWaitingTime(output1)));
            ((TextView) row.findViewById(R.id.turnaround)).setText(String.valueOf(Output.getAverageTurnAround(output1)));

            row = (TableRow) comparisionTable.getChildAt(6);
            if (row == null) {
                row = (TableRow) LayoutInflater.from(this).inflate(R.layout.comparision_row, null);
                ((TextView) row.findViewById(R.id.type)).setText("Round robin");
                comparisionTable.addView(row);
            }
            RoundRobin robin = new RoundRobin();
            quantumComparision.setText(String.valueOf(3));
            output1 = robin.getOutput(input, 3);
            ((TextView) row.findViewById(R.id.waiting)).setText(String.valueOf(Output.getAverageWaitingTime(output1)));
            ((TextView) row.findViewById(R.id.turnaround)).setText(String.valueOf(Output.getAverageTurnAround(output1)));
            quantumComparision.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus)
                        ((ScrollView) findViewById(R.id.scrollView2)).fullScroll(View.FOCUS_DOWN);
                }
            });
            goComparision.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRoundRobinComparision();
                }
            });
            Toast.makeText(this,"Done",Toast.LENGTH_SHORT).show();
            scrollView.smoothScrollTo(0,summaryTable.getTop());
        }*/

    }

    private void execute() {
        for (int i = 0; i < input.length; i++) {
            final View rowView = getLayoutInflater().inflate(R.layout.sum_add, null, false);
            TextView pname = (TextView) rowView.findViewById(R.id.sum_pro);
            TextView at = (TextView) rowView.findViewById(R.id.sum_at);
            TextView bt = (TextView) rowView.findViewById(R.id.sum_bt);
            pname.setText(input[i].getpName());
            at.setText(String.valueOf(input[i].getaTime()));
            bt.setText(String.valueOf(input[i].getbTime()));
            tr.addView(rowView);
        }
    }

    public static Input[] toInputObject(Parcelable[] p) {
        if (p == null) {
            return null;
        }
        return Arrays.copyOf(p, p.length, Input[].class);
    }
    public static Output[] toOutputObject(Parcelable[] p) {
        if (p == null) {
            return null;
        }
        return Arrays.copyOf(p, p.length, Output[].class);
    }
}