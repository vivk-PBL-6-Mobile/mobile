package com.example.pbl6;

import android.widget.TextView;

import java.util.Objects;

public class StatusText {
    private TextView textView;
    private boolean status;

    private final String ON_VALUE = "1";
    private final String OFF_VALUE = "0";

    private final String ON_STATUS = "ON";
    private final String OFF_STATUS = "OFF";

    public StatusText(TextView textView) {
        this.textView = textView;
        this.status = false;
        setStatus();
    }

    public void SetStatus(String statusString) {
        status = Objects.equals(statusString, ON_VALUE);
        setStatus();
    }

    public boolean GetStatusBool() {
        return status;
    }

    public String GetStatus() {
        if (status) {
            return ON_VALUE;
        } else {
            return OFF_VALUE;
        }
    }

    public void Invert() {
        status = !status;
        setStatus();
    }

    private void setStatus() {
        if (this.status) {
            this.textView.setText(ON_STATUS);
        } else {
            this.textView.setText(OFF_STATUS);
        }
    }
}
