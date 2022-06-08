package com.example.pbl6;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class HttpHandler {
    private static HttpHandler instance;

    private RequestQueue requestQueue;

    Context applicationContext;
    NotifyComponent notifyComponent;

    TextView temperatureText;
    TextView humidityText;
    StatusText climateControlStatusText;
    EditText climateControlValueText;
    StatusText doorStatusText;

    Button climateControlStatusButton;
    Button doorStatusButton;

    String gasLeakText;
    String waterLeakText;

    StatusText gasStatusText;
    StatusText waterStatusText;

    LeakController gasLeakController;
    LeakController waterLeakController;

    HorizontalNumberPicker climateControlPicker;
    HorizontalNumberPicker groundHumidityPicker;

    private boolean update = false;

    private HttpHandler()  {
        update = false;
    }

    public static HttpHandler GetInstance()  {
        if (instance == null)
            instance = new HttpHandler();
        return instance;
    }

    public void Init(Context applicationContext,
                     TextView temperatureText,
                     TextView humidityText,
                     StatusText climateControlStatusText,
                     EditText climateControlValueText,
                     StatusText doorStatusText,
                     Button climateControlStatusButton,
                     Button doorStatusButton,
                     StatusText gasStatusText,
                     StatusText waterStatusText,
                     LeakController gasLeakController,
                     LeakController waterLeakController,
                     HorizontalNumberPicker climateControlPicker,
                     HorizontalNumberPicker groundHumidityPicker) {
        requestQueue = Volley.newRequestQueue(applicationContext);
        notifyComponent = new NotifyComponent(applicationContext);

        this.applicationContext = applicationContext;

        this.temperatureText = temperatureText;
        this.humidityText = humidityText;
        this.climateControlStatusText = climateControlStatusText;
        this.climateControlValueText = climateControlValueText;
        this.doorStatusText = doorStatusText;

        this.climateControlStatusButton = climateControlStatusButton;
        this.doorStatusButton = doorStatusButton;

        this.gasStatusText = gasStatusText;
        this.waterStatusText = waterStatusText;

        this.gasLeakController = gasLeakController;
        this.waterLeakController = waterLeakController;

        this.climateControlPicker = climateControlPicker;
        this.groundHumidityPicker = groundHumidityPicker;
    }

    public void GetSensors() {
        String url = "https://api.thingspeak.com/channels/1759719/feeds.json?api_key=LIG94UFB3BNVCYHK&results=1";
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            System.out.println(response);
            try {
                JSONObject json = new JSONObject(response);
                JSONObject data = json.getJSONArray("feeds").getJSONObject(0);

                String airTemperature = data.getString("field1");
                String airHumidity = data.getString("field2");

                temperatureText.setText(airTemperature);
                humidityText.setText(airHumidity);

                String gasLeak = data.getString("field4");
                String waterLeak = data.getString("field5");

                gasLeakController.Update(gasLeak);
                waterLeakController.Update(waterLeak);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });

        requestQueue.add(request);
    }

    public void GetActuators() {
        String url = "https://api.thingspeak.com/channels/1760075/feeds.json?api_key=2IXK83J639457S8H&results=1";
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            System.out.println(response);
            try {
                JSONObject json = new JSONObject(response);
                JSONObject data = json.getJSONArray("feeds").getJSONObject(0);

                String climateControlStatus = data.getString("field1");
                String doorStatus = data.getString("field2");
                String gasStatus = data.getString("field3");
                String waterStatus = data.getString("field4");

                climateControlStatusText.SetStatus(climateControlStatus);
                doorStatusText.SetStatus(doorStatus);

                gasStatusText.SetStatus(gasStatus);
                waterStatusText.SetStatus(waterStatus);

                String climateControlTemperature = data.getString("field5");
                climateControlPicker.setValue(Integer.parseInt(climateControlTemperature));

                String groundHumidity = data.getString("field6");
                groundHumidityPicker.setValue(Integer.parseInt(groundHumidity));

//                String climateControlTemperature = data.getString("field3");


//                String gasLeak = data.getString("field4");
//                String waterLeak = data.getString("field5");

//                String climateControlStatus = data.getString("field1");
//                String doorStatus = data.getString("field2");
//                String gasStatus = data.getString("field3");
//                String waterStatus = data.getString("field4");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });

        requestQueue.add(request);
    }

    public void SetActuators() {
        String url = "https://api.thingspeak.com/update?api_key=QUMJ5HPL2H9QJRUQ"
                +"&field1="+climateControlStatusText.GetStatus()
                +"&field2="+doorStatusText.GetStatus()
                +"&field3="+gasStatusText.GetStatus()
                +"&field4="+waterStatusText.GetStatus()
                +"&field5="+ climateControlPicker.getValue()
                +"&field6="+ groundHumidityPicker.getValue();
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {

        }, error -> {

        });

        requestQueue.add(request);
    }

    public boolean GetUpdate() {
        return update;
    }

    public void SetUpdate(boolean update) {
        this.update = update;
    }
}
