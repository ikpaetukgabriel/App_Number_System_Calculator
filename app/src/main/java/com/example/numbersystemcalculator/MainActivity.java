package com.example.numbersystemcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextDecimal;
    private EditText editTextBinary;
    private EditText editTextHexa;
    private Button buttonClear;
    private String inputValue;


    private int focusedViewId;
    private View.OnFocusChangeListener OnFocusChangeListener;
    private TextWatcher textWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextDecimal = findViewById(R.id.editTextDecimal);
        editTextBinary = findViewById(R.id.editTextBinary);
        editTextHexa = findViewById(R.id.editTextHex);

        buttonClear = findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });


        textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                inputValue = ((EditText) findViewById(focusedViewId)).getText().toString().trim();

                if (inputValue.length() > 0) {
                    convertNumber();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        OnFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //Toast.makeText(getApplicationContext(), "TYPE VALUE", Toast.LENGTH_SHORT).show();
                    focusedViewId = v.getId();
                    ((EditText) findViewById(focusedViewId)).addTextChangedListener(textWatcher);

                    GradientDrawable gradientDrawable = new GradientDrawable(
                            GradientDrawable.Orientation.TR_BL,
                            new int[]{Color.parseColor("#fed8b1"), Color.parseColor("#FFFFFF")}
                    );

                    gradientDrawable.setShape(GradientDrawable.RECTANGLE);

                    gradientDrawable.setCornerRadius(10);

                    if (focusedViewId == R.id.editTextDecimal){
                        gradientDrawable.setStroke(8,
                                ContextCompat.getColor(getApplicationContext(), android.R.color.holo_green_light));
                    }
                    else if(focusedViewId == R.id.editTextBinary) {
                        gradientDrawable.setStroke(8,
                            ContextCompat.getColor(getApplicationContext(), android.R.color.holo_orange_light));
                    }
                    else {
                        gradientDrawable.setStroke(8,
                                ContextCompat.getColor(getApplicationContext(), android.R.color.holo_red_light));
                    }

                    v.setBackground(gradientDrawable);
                }
                else{

                    ((EditText) findViewById(focusedViewId)).removeTextChangedListener(textWatcher);

                    if (focusedViewId == R.id.editTextDecimal){
                        v.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.inputview_design));
                    }
                    else if(focusedViewId == R.id.editTextBinary){
                        v.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.inputview_design2));
                    }
                    else{
                        v.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.inputview_design3));
                    }
                }
            }
        };
        editTextDecimal.setOnFocusChangeListener(OnFocusChangeListener);
        editTextBinary.setOnFocusChangeListener(OnFocusChangeListener);
        editTextHexa.setOnFocusChangeListener(OnFocusChangeListener);

    }

    private void clearFields() {
        editTextBinary.setText(" ");
        editTextHexa.setText(" ");
        editTextDecimal.setText(" ");
    }


    private void convertNumber() {
        try {

            long number = 0;

            switch (focusedViewId){
                case R.id.editTextDecimal:
                    number = Long.parseLong(inputValue);

                    editTextBinary.setText(String.valueOf(Long.toBinaryString(number)));
                    editTextHexa.setText(String.valueOf(Long.toHexString(number)));
                    break;
                case R.id.editTextBinary:
                    number = Long.parseLong(inputValue, 2);

                    editTextDecimal.setText(String.valueOf(number));
                    editTextHexa.setText(String.valueOf(Long.toHexString(number)));
                    break;

                case R.id.editTextHex:
                    number = Long.parseLong(inputValue, 16);

                    editTextDecimal.setText(String.valueOf(number));
                    editTextBinary.setText(String.valueOf(Long.toBinaryString(number)));
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}