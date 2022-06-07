package com.example.pbl6;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    private Timer timer;
    private TimerTask timerTask;
    private NotifyComponent notifyComponent;

    private final String TAG = "Timers";
    private final int SCHEDULE_SECONDS = 5;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);

        startTimer();

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        stopTimerTask();
        super.onDestroy();
    }

    final Handler handler = new Handler();

    public void startTimer() {
        timer = new Timer();
        notifyComponent = new NotifyComponent(getApplicationContext());

        initializeTimerTask();

        timer.schedule(timerTask, 5000, SCHEDULE_SECONDS * 1000L);
    }

    public void stopTimerTask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        // TODO CALL NOTIFICATION FUNC
                        // notifyComponent.Notify("Test Header", "TEST");
                        HttpHandler handler = HttpHandler.GetInstance();
                        handler.GetSensors();
                        handler.SetActuators();
                    }
                });
            }
        };
    }
}