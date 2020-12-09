package com.example.scheduling;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.scheduling.algorithms.FCFS;
import com.example.scheduling.algorithms.LJF;
import com.example.scheduling.algorithms.LRTF;
import com.example.scheduling.algorithms.PriorityBased;
import com.example.scheduling.algorithms.RoundRobin;
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
    String[] algoritms;
    Spinner algoClass;
    TableLayout processTable;
    CpuQueueView cpuQueueView;
    ConstraintLayout outputContainer;
    Button go, change_tq;
    Parcelable[] p;
    LinearLayout tr;
    List<TableRow> rows;
    Input[] input;
    Output[] output = null;
    TextView priority, time_quantum;
    TextView avgTurnAround;
    TextView avgWaiting;
    int tq = 2;
    boolean noTQexe = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        initViews();
        rows = new ArrayList<>();
        algoritms = new String[]{"Select algorithm", "FCFS", "SJF", "SRTF", "LJF", "LRTF", "Priority Non-Preemptive", "Priority Preemptive", "Round robin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_textview, algoritms);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        algoClass.setAdapter(adapter);
        execute();
        change_tq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAlert();
            }
        });
        algoClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        priority.setVisibility(View.INVISIBLE);
                        outputContainer.setVisibility(View.INVISIBLE);
                        change_tq.setVisibility(View.GONE);
                        time_quantum.setVisibility(View.GONE);
                        avgTurnAround.setVisibility(View.GONE);
                        avgWaiting.setVisibility(View.GONE);
                        editExecute();
                        break;
                    case 6:
                    case 7:
                        outputContainer.setVisibility(View.INVISIBLE);
                        change_tq.setVisibility(View.GONE);
                        time_quantum.setVisibility(View.GONE);
                        avgTurnAround.setVisibility(View.GONE);
                        avgWaiting.setVisibility(View.GONE);
                        setUpPriority();
                        editExecute();
                        break;
                    case 8:
                        outputContainer.setVisibility(View.INVISIBLE);
                        priority.setVisibility(View.INVISIBLE);
                        change_tq.setVisibility(VISIBLE);
                        if (!displayAlert()) {
                            time_quantum.setVisibility(VISIBLE);
                            avgTurnAround.setVisibility(View.GONE);
                            avgWaiting.setVisibility(View.GONE);
                            editExecute();
                        } else {
                            return;
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOuput();
            }
        });
    }

    void setUpPriority() {
        priority.setVisibility(VISIBLE);
    }

    private void initViews() {
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
        output = toOutputObject(p);
        priority = findViewById(R.id.prior);
        change_tq = findViewById(R.id.change_tq);
        time_quantum = findViewById(R.id.time_quantum);
        avgTurnAround = findViewById(R.id.avgTurnAround);
        avgWaiting = findViewById(R.id.avgWaiting);
    }


    void getOuput() {
        int type = algoClass.getSelectedItemPosition();
        if (type == 0) {
            Toast.makeText(this, "Please select algorithm type", Toast.LENGTH_LONG).show();
        } else {
            List<Integer> cpuQueue = null;
            switch (type) {
                case 1:
                    FCFS fcfs = new FCFS();
                    output = fcfs.getOutput(input);
                    cpuQueue = fcfs.getCpuQueue();
                    editTable(output);
                    break;
                case 2:
                    SJF sjf = new SJF();
                    output = sjf.getOutput(input);
                    cpuQueue = sjf.getCpuQueue();
                    editTable(output);
                    break;
                case 3:
                    SRTF srtf = new SRTF();
                    output = srtf.getOutput(input);
                    cpuQueue = srtf.getCpuQueue();
                    editTable(output);
                    break;
                case 4:
                    LJF ljf = new LJF();
                    output = ljf.getOutput(input);
                    cpuQueue = ljf.getCpuQueue();
                    editTable(output);
                    break;
                case 5:
                    LRTF lrtf = new LRTF();
                    output = lrtf.getOutput(input);
                    cpuQueue = lrtf.getCpuQueue();
                    editTable(output);
                    break;
                case 6:
                    if (readPriority(input)) {
                        PriorityBased pr = new PriorityBased();
                        output = pr.getNonPreemptive(input);
                        cpuQueue = pr.getCpuQueue();
                        editTable(output);
                    } else {
                        return;
                    }
                    break;
                case 7:
                    if (readPriority(input)) {
                        PriorityBased prio = new PriorityBased();
                        output = prio.getPreemptive(input);
                        cpuQueue = prio.getCpuQueue();
                        editTable(output);
                    } else {
                        return;
                    }
                    break;
                case 8:
                    RoundRobin rr = new RoundRobin();
                    output = rr.getOutput(input, tq);
                    cpuQueue = rr.getCpuQueue();
                    editTable(output);
                    break;
            }
            outputContainer.setVisibility(VISIBLE);
            cpuQueueView.setUp(cpuQueue, input);
            //cpuQueueView.startAnimation((Animation)AnimationUtils.loadAnimation(this,R.anim.shake));
            Animation a = AnimationUtils.loadAnimation(this, R.anim.shake);
            a.reset();
            cpuQueueView.clearAnimation();
            cpuQueueView.startAnimation(a);
            avgWaiting.setVisibility(VISIBLE);
            avgTurnAround.setVisibility(VISIBLE);
            avgWaiting.setText("Average waiting time : " + Output.getAverageWaitingTime(output));
            avgTurnAround.setText("Average turn around time : " + Output.getAverageTurnAround(output));
        }

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

    private void editExecute() {
        for (int i = 0; i < tr.getChildCount(); i++) {
            ConstraintLayout rowView = (ConstraintLayout) tr.getChildAt(i);
            int type = algoClass.getSelectedItemPosition();
            if (type != 6 && type != 7) {
                EditText pri = (EditText) rowView.findViewById(R.id.sum_prior);
                pri.setVisibility(View.INVISIBLE);
            } else {
                EditText pri = (EditText) rowView.findViewById(R.id.sum_prior);
                pri.setVisibility(View.VISIBLE);
            }
            TextView wt = (TextView) rowView.findViewById(R.id.sum_wt);
            TextView tat = (TextView) rowView.findViewById(R.id.sum_tat);
            TextView ct = (TextView) rowView.findViewById(R.id.sum_ct);
            wt.setText("");
            tat.setText("");
            ct.setText("");
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

    public void editTable(Output[] out) {
        for (int i = 0; i < tr.getChildCount(); i++) {
            int type = algoClass.getSelectedItemPosition();
            ConstraintLayout rowView = (ConstraintLayout) tr.getChildAt(i);
            TextView wt = (TextView) rowView.findViewById(R.id.sum_wt);
            TextView tat = (TextView) rowView.findViewById(R.id.sum_tat);
            TextView ct = (TextView) rowView.findViewById(R.id.sum_ct);
            wt.setText(String.valueOf(out[i].getWaiting()));
            tat.setText(String.valueOf(out[i].getTurnAround()));
            ct.setText(String.valueOf(out[i].getCompletion()));
        }
    }

    boolean displayAlert() {
        try {
            AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
            alert.setTitle("Enter Time Quantum");
            alert.setMessage("");
            final EditText in_tq = new EditText(HomeActivity.this);
            alert.setView(in_tq);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        tq = Integer.parseInt(in_tq.getText().toString());
                        time_quantum.setText("Time Quantum : " + tq);
                        //System.out.println(tq);
                        noTQexe = false;
                    } catch (NumberFormatException e) {
                        noTQ();
                        time_quantum.setText("Time Quantum : " + tq);
                        noTQexe = true;
                    }
                }
            });
            alert.show();
            editExecute();
        } catch (Exception e) {
            Toast.makeText(this, "Enter Time quantum.", Toast.LENGTH_LONG).show();
        }
        return noTQexe;
    }

    boolean readPriority(Input[] input) {
        boolean next = false;
        try {
            for (int i = 0; i < tr.getChildCount(); i++) {
                int type = algoClass.getSelectedItemPosition();
                ConstraintLayout rowView = (ConstraintLayout) tr.getChildAt(i);
                if (type == 6 || type == 7) {
                    input[i].setPriority(Integer.parseInt(((EditText) rowView.findViewById(R.id.sum_prior)).getText().toString()));
                }
            }
            next = true;
        } catch (Exception e) {
            Toast.makeText(this, "Priorities not entered.", Toast.LENGTH_LONG).show();
            next = false;
        }
        return next;
    }

    private void noTQ() {
        Toast.makeText(this, "Enter Time Quantum!", Toast.LENGTH_LONG).show();
    }

}