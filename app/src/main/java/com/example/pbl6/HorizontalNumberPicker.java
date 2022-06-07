package com.example.pbl6;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HorizontalNumberPicker {
    private final EditText editTextNumber;
    private int min, max;

    public HorizontalNumberPicker(EditText editTextNumber, Button lessButton, Button moreButton, int min, int max) {
        this.editTextNumber = editTextNumber;

        setMin(0);
        setMax(50);

        editTextNumber.addTextChangedListener(new AddChangedHandler());

        lessButton.setOnClickListener(new AddHandler(-1));
        moreButton.setOnClickListener(new AddHandler(1));
    }

    private class AddChangedHandler implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            HttpHandler handler =  HttpHandler.GetInstance();
            handler.SetActuators();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private class AddHandler implements View.OnClickListener {
        final int diff;

        public AddHandler(int diff) {
            this.diff = diff;
        }

        @Override
        public void onClick(View v) {
            int newValue = getValue() + diff;
            if (newValue < min) {
                newValue = min;
            } else if (newValue > max) {
                newValue = max;
            }

            editTextNumber.setText(String.valueOf(newValue));
        }
    }

    public int getValue() {
        if (editTextNumber != null) {
            try {
                final String value = editTextNumber.getText().toString();
                return Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                Log.e("HorizontalNumberPicker", ex.toString());
            }
        }
        return 0;
    }

    public void setValue(final int value) {
        if (editTextNumber != null) {
            editTextNumber.setText(String.valueOf(value));
        }
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}