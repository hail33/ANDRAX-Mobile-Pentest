package com.thecrackertechnology.andrax;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class HIDAttack extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String args;
    EditText edithid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidattack);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edithid = (EditText) findViewById(R.id.editTexthidargs);

        Button btnstart = (Button) findViewById(R.id.buttonhidstart);
        Button btnstop = (Button) findViewById(R.id.buttonhidstop);

        /**
         *
         * Help me, i'm dying...
         *
         **/

        Spinner spinner = (Spinner) findViewById(R.id.spinnerhid);

        spinner.setOnItemSelectedListener(this);

        List<String> hidpayloads = new ArrayList<String>();


        try {



            Process process = Runtime.getRuntime().exec("su -c ls -1 /data/data/com.thecrackertechnology.andrax/ANDRAX/hidattack/payloads");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            int read;

            char[] buffer = new char[8000];
            String output;
            while ((output = reader.readLine()) != null) {

                hidpayloads.add(output);

            }
            reader.close();


            process.waitFor();




        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hidpayloads);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);


        btnstart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                args = edithid.getText().toString().replace("\n", "\\n").trim();;


                try {

                    Process process = Runtime.getRuntime().exec("su -c printf " + "\"" + args + "\n\"" + " > /data/data/com.thecrackertechnology.andrax/ANDRAX/hidattack/payloads/run.tmp");
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));
                    int read;

                    char[] buffer = new char[8000];
                    StringBuffer output = new StringBuffer();
                    while ((read = reader.read(buffer)) > 0) {
                        output.append(buffer, 0, read );


                    }
                    reader.close();


                    process.waitFor();


                    Process process2 = Runtime.getRuntime().exec("su -c /data/data/com.thecrackertechnology.andrax/ANDRAX/bin/hidparser /data/data/com.thecrackertechnology.andrax/ANDRAX/hidattack/payloads/run.tmp");




                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }










            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString();


        try {

            edithid.setText("");

            Process process = Runtime.getRuntime().exec("su -c cat /data/data/com.thecrackertechnology.andrax/ANDRAX/hidattack/payloads/" + item);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            int read;

            char[] buffer = new char[8000];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read );

                edithid.append(output.toString());

            }
            reader.close();

            process.waitFor();




        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        Toast.makeText(parent.getContext(), "Payload: " + item, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
