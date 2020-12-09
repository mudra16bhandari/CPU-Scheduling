package com.example.scheduling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.scheduling.model.Input;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layoutList;
    Button add, delete, submit, display;
    TextView bt2;
    ArrayList<View> rv = new ArrayList<>();
    Input[] input;
    static int i = 0;
    TableLayout proTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        proTable=findViewById(R.id.prtable);
        layoutList = findViewById(R.id.layout_list);
        add = findViewById(R.id.button_add);
        delete = findViewById(R.id.row_delete);
        submit = findViewById(R.id.submit);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    addRow();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    if (i != 0) {
                        removeRow(rv.get(i - 1));
                    } else {
                        setToast();
                    }


                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (readData()) {
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        Bundle b = new Bundle();
                        b.putParcelableArray("input", input);
                        intent.putExtras(b);
                        startActivity(intent);
                    } else {
                        noRowToast();
                    }
                } catch (Exception e) {
                    noInputToast();
                }
            }
        });
    }

    private void noInputToast() {
        Toast.makeText(this, "Please insert all inputs!", Toast.LENGTH_LONG).show();
    }


    private void addRow() {
        if (i <= 6) {
            final View rowView = getLayoutInflater().inflate(R.layout.row_add_input, null, false);
            TextView edit_text1 = (TextView) rowView.findViewById(R.id.input_process_name);
            EditText edit_text2 = (EditText) rowView.findViewById(R.id.input_at);
            EditText edit_text3 = (EditText) rowView.findViewById(R.id.input_bt);
            edit_text1.setText("P" + i);
            layoutList.addView(rowView);
            rv.add(rowView);
            i++;

        } else {
            Toast.makeText(this, "Limit Reached! Atmost 7 inputs allowed. ", Toast.LENGTH_LONG).show();
        }
    }

    private void removeRow(View view) {

        layoutList.removeView(view);
        rv.remove(i - 1);
        i--;


    }

    private boolean readData() {
        boolean next = false;
        int len = layoutList.getChildCount();
        input = new Input[len];
        if (len == 0) {
            next = false;
        } else {
            for (int i = 0; i < len; i++) {
                ConstraintLayout row = (ConstraintLayout) layoutList.getChildAt(i);
                Input in = new Input();
                String pname = ((TextView) row.findViewById(R.id.input_process_name)).getText().toString();
                in.setpName(pname);
                in.setaTime(Integer.parseInt(((EditText) row.findViewById(R.id.input_at)).getText().toString()));
                in.setbTime(Integer.parseInt(((EditText) row.findViewById(R.id.input_bt)).getText().toString()));
                input[i] = in;
                next = true;
            }
        }
        return next;
    }

    @Override
    public void onClick(View v) {
    }

    public void onBackPressed() {
        finishAffinity();
    }

    private void setToast() {
        Toast.makeText(this, "No rows to remove!", Toast.LENGTH_LONG).show();
    }

    private void noRowToast() {
        Toast.makeText(this, "No process detected. Please enter at least one process", Toast.LENGTH_LONG).show();
    }


}