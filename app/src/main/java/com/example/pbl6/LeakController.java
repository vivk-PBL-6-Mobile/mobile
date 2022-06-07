package com.example.pbl6;

import android.content.Context;

public class LeakController {
    private NotifyComponent notifyComponent;

    private StatusText statusText;
    private String title;
    private String text;

    private final String ON_VALUE = "1";

    public LeakController(Context applicationContext,
                          StatusText statusText,
                          String title,
                          String text) {
        notifyComponent = new NotifyComponent(applicationContext);

        this.statusText = statusText;

        this.title = title;
        this.text = text;
    }

    public void Update(String status) {
        if (statusText.GetStatusBool())
            return;

        if (status.equals(ON_VALUE)) {
            Notify();
        }
    }

    private void Notify() {
        notifyComponent.Notify(title, text);
    }
}
