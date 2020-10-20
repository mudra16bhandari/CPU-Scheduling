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
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layoutList;
    Switch s_io;
    Button add, delete, submit, display;
    TextView bt2, io_input;
    ArrayList<String> pname = new ArrayList<>();
    ArrayList<Integer> at = new ArrayList<>();
    ArrayList<Integer> bt = new ArrayList<>();
    ArrayList<Integer> list_io = new ArrayList<>();
    ArrayList<Integer> list_bt2 = new ArrayList<>();
    ArrayList<View> rv = new ArrayList<>();
    Input[] input;
    static int i = 0;
    static boolean io;
    TableLayout proTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* Thread timer = new Thread() {

            @Override
            public void run() {
                try{
                    sleep(8000);
                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(intent);
                    finish();
                    super.run();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        timer.start();*/
        proTable=findViewById(R.id.prtable);
        layoutList = findViewById(R.id.layout_list);
        s_io = findViewById(R.id.switchio);
        add = findViewById(R.id.button_add);
        delete = findViewById(R.id.row_delete);
        submit = findViewById(R.id.submit);
        bt2 = findViewById(R.id.bt2);
        io_input = findViewById(R.id.iot);
        //display = findViewById(R.id.display);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    addRow();
                    i++;
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    removeRow(rv.get(i - 1));

                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                Bundle b = new Bundle();
                b.putParcelableArray("input", input);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        /*display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display();
            }
        });*/
        io = s_io.isChecked();
        s_io.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s_io.isChecked()) {
                    bt2.setVisibility(View.VISIBLE);
                    io_input.setVisibility(View.VISIBLE);
                } else {
                    bt2.setVisibility(View.GONE);
                    io_input.setVisibility(View.GONE);
                }
                includeIO();
            }
        });
    }


    private void addRow() {

        final View rowView = getLayoutInflater().inflate(R.layout.row_add_input, null, false);
        TextView edit_text1 = (TextView) rowView.findViewById(R.id.input_process_name);
        EditText edit_text2 = (EditText) rowView.findViewById(R.id.input_at);
        EditText edit_text3 = (EditText) rowView.findViewById(R.id.input_bt);
        EditText edit_text4 = (EditText) rowView.findViewById(R.id.input_io);
        EditText edit_text5 = (EditText) rowView.findViewById(R.id.input_bt2);
        if (s_io.isChecked()) {
            edit_text4.setVisibility(View.VISIBLE);
            edit_text5.setVisibility(View.VISIBLE);
        } else {
            edit_text4.setVisibility(View.GONE);
            edit_text5.setVisibility(View.GONE);
        }
        edit_text1.setText("P" + i);
        layoutList.addView(rowView);
        rv.add(rowView);

    }

    private void removeRow(View view) {

        layoutList.removeView(view);
        rv.remove(i - 1);
        i--;

    }

    private void readData() {

        int len = layoutList.getChildCount();
        input = new Input[len];
        for (int i = 0; i < len; i++) {
            ConstraintLayout row = (ConstraintLayout) layoutList.getChildAt(i);
            Input in = new Input();
            String pname = ((TextView) row.findViewById(R.id.input_process_name)).getText().toString();
            in.setpName(pname);
            in.setaTime(Integer.parseInt(((EditText) row.findViewById(R.id.input_at)).getText().toString()));
            in.setbTime(Integer.parseInt(((EditText) row.findViewById(R.id.input_bt)).getText().toString()));
            in.setIoTime(Integer.parseInt(((EditText)row.findViewById(R.id.input_io)).getText().toString()));
            in.setbTime2(Integer.parseInt(((EditText)row.findViewById(R.id.input_bt2)).getText().toString()));
            input[i] = in;
        }
    }

    @Override
    public void onClick(View v) {
    }

    public void onBackPressed() {
        finishAffinity();
    }

    private void includeIO() {
        if (rv != null) {
            for (View v : rv) {
                if (s_io.isChecked()) {
                    v.findViewById(R.id.input_io).setVisibility(View.VISIBLE);
                    v.findViewById(R.id.input_bt2).setVisibility(View.VISIBLE);
                } else {
                    v.findViewById(R.id.input_io).setVisibility(View.GONE);
                    v.findViewById(R.id.input_bt2).setVisibility(View.GONE);
                }
            }
        }
    }

}