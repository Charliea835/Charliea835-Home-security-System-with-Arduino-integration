/*
   This java file shows the user their historical alarm data from the system, the data is retrieved through JSON
   and appeneded to the result edittext
 */

package com.example.user.appproject;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
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


public class ViewTimestamp extends AppCompatActivity {
    RequestQueue requestQueue;
    String showUrl = "http://ourSystem.000webhostapp.com/retrieveTimestamp.php";
   // String showUrl = "http://192.168.1.9/retrieveTimestamp.php";
    TextView result;
    TextView mainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_timestamp);
        result = (TextView) findViewById(R.id.textView);
        result.setMovementMethod(new ScrollingMovementMethod());
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final String usersEmail = getIntent().getExtras().getString("currentEmail");
        mainText = (TextView) findViewById(R.id.mainTxt);
        mainText.setText(usersEmail + ", your alarm was activated at these times");
        getSensorData();
    }

    public void getSensorData(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                showUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                try {
                    JSONArray sensor = response.getJSONArray("Alarm");
                    for (int i = 0; i < sensor.length(); i++) {
                        JSONObject alarm = sensor.getJSONObject(i);

                        String value = alarm.getString("time");

                        result.append("\n \n \n" + "Alarm activated at:" + value +"\n \n");
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

        //reset network retry policy to avoid timeouts
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