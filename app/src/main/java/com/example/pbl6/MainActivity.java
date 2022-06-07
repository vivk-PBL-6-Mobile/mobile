package com.example.pbl6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context applicationContext = getApplicationContext();

        TextView temperatureText = (TextView) findViewById(R.id.TemperatureValue);
        TextView humidityText = (TextView) findViewById(R.id.HumidityValue);
        StatusText climateControlStatusText = new StatusText(findViewById(R.id.ClimatControlStatus));
        EditText climateControlValueText = (EditText) findViewById(R.id.et_number_cc);
        StatusText doorStatusText = new StatusText(findViewById(R.id.DoorStatus));

        Button climateControlStatusButton = (Button) findViewById(R.id.ClimatControlStatusButton);
        Button doorStatusButton = (Button) findViewById(R.id.DoorStatusButton);

        StatusText gasStatusText = new StatusText(findViewById(R.id.GasStatus));
        StatusText waterStatusText = new StatusText(findViewById(R.id.WaterStatus));

        Button gasStatusButton = (Button) findViewById(R.id.GasStatusButton);
        Button waterStatusButton = (Button) findViewById(R.id.WaterStatusButton);

        Button climateControlLessButton = (Button) findViewById(R.id.btn_less_cc);
        Button climateControlMoreButton = (Button) findViewById(R.id.btn_more_cc);

        EditText groundHumidityValueText = (EditText) findViewById(R.id.et_number_gh);

        Button groundHumidityLessButton = (Button) findViewById(R.id.btn_less_gh);
        Button groundHumidityMoreButton = (Button) findViewById(R.id.btn_more_gh);

        HorizontalNumberPicker climateControlPicker = new HorizontalNumberPicker(climateControlValueText,
                climateControlLessButton,
                climateControlMoreButton,
                0,
                50);

        HorizontalNumberPicker groundHumidityPicker = new HorizontalNumberPicker(groundHumidityValueText,
                groundHumidityLessButton,
                groundHumidityMoreButton,
                0,
                100);

        HorizontalStatusText horizontalClimateControlStatus = new HorizontalStatusText(
                climateControlStatusText,
                climateControlStatusButton);
        HorizontalStatusText horizontalDoorStatus = new HorizontalStatusText(
                doorStatusText,
                doorStatusButton);

        HorizontalStatusText horizontalGasStatus = new HorizontalStatusText(
                gasStatusText,
                gasStatusButton);
        HorizontalStatusText horizontalWaterStatus = new HorizontalStatusText(
                waterStatusText,
                waterStatusButton);

        LeakController gasLeakController = new LeakController(applicationContext,
                gasStatusText,
                "GAS TITLE!",
                "GAS LEAK!!!");
        LeakController waterLeakController = new LeakController(applicationContext,
                waterStatusText,
                "WATER TITLE!",
                "WATER LEAK!!!");

        HttpHandler handler = HttpHandler.GetInstance();
        handler.Init(applicationContext,
                temperatureText,
                humidityText,
                climateControlStatusText,
                climateControlValueText,
                doorStatusText,
                climateControlStatusButton,
                doorStatusButton,
                gasStatusText,
                waterStatusText,
                gasLeakController,
                waterLeakController,
                climateControlPicker,
                groundHumidityPicker);

        handler.GetSensors();
        handler.GetActuators();

        startService(new Intent(this, NotificationService.class));
    }
}