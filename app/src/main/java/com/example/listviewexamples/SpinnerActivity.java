package com.example.listviewexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SpinnerActivity extends AppCompatActivity {
    EditText et_amount, et_result;
    String itemFrom, itemTo;

    String[] items = {"USD", "AUD", "CAD", "CHF", "CNY", "EUR", "GBP", "JPY", "KRW", "SGD", "VND"};
    double[] convertFromUSD = {1, 1.404, 1.314, 0.9073, 6.686, 0.8472, 0.765, 104.7, 1133.54, 1.357, 23233.79};
    static double result;


    public void convertUSD(String itemFrom, double amount) {
        for(int i = 0; i < items.length; i++) {
            if(itemFrom.equals(items[i])) {
                result = amount / convertFromUSD[i];
                break;
            }
        }
    }

    public void convertToResult(String itemFrom, String itemTo, double amount) {
        convertUSD(itemFrom, amount);
        for(int i = 0; i < items.length; i++) {
            if(itemTo.equals(items[i])) {
                result = result * convertFromUSD[i];
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);

        Spinner spinnerFrom = findViewById(R.id.spinner1);
        Spinner spinnerTo = findViewById(R.id.spinner2);
        et_amount = findViewById(R.id.et_amount);
        et_result = findViewById(R.id.et_result);

//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_dropdown_item,
//                items);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.layout_item,
                R.id.text_view,
                items);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        spinnerFrom.setSelection(0);
        spinnerTo.setSelection(10);

        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                itemFrom = items[i];
                et_amount.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                itemTo = items[i];
                et_amount.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        et_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_amount.getText().toString().equals("")) {
                    et_result.setText("0");
                } else {
                    double amount = Double.parseDouble(et_amount.getText().toString());
                    convertToResult(itemFrom, itemTo, amount);
                    et_result.setText(String.format("%.2f", result));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}