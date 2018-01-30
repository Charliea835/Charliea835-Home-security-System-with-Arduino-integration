package com.example.user.appproject;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ViewSensorData extends AppCompatActivity {
    RequestQueue requestQueue;
    String showUrl = "http://ourSystem.000webhostapp.com/showSensorData.php";
    //String showUrl = "http://192.168.1.9/showSensorData.php";
    TextView result;
    TextView mainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sensor_data);
        result = (TextView) findViewById(R.id.dataTxt);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        getSensorData();
        final String usersEmail = getIntent().getExtras().getString("currentEmail");
        mainText = (TextView) findViewById(R.id.mainTxt);
        mainText.setText(usersEmail + ", These are your captured sensor data");
    }

    public void getSensorData(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                showUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                try {
                    JSONArray sensor = response.getJSONArray("sensor");
                    for (int i = 0; i < sensor.length(); i++) {
                        JSONObject data = sensor.getJSONObject(i);

                        String value = data.getString("Value");
                        System.out.println("VALUES:" + value);

                        result.append("\n \n" + "Sensor value recieved:" + value +"\n \n");
                    }
                    result.append("");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.append(error.getMessage());

            }
        });

        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}
