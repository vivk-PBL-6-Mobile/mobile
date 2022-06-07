package com.example.pbl6;

import android.view.View;
import android.widget.Button;

public class HorizontalStatusText {
    private StatusText statusText;
    private Button button;

    public HorizontalStatusText(StatusText statusText, Button button) {
        this.statusText = statusText;
        this.button =  button;

        this.button.setOnClickListener(new AddHandler());
    }

    private class AddHandler implements View.OnClickListener {
        public AddHandler() {
        }

        @Override
        public void onClick(View v) {
            statusText.Invert();

            HttpHandler handler =  HttpHandler.GetInstance();
            handler.SetActuators();
        }
    }
}
