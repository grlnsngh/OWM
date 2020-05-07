package com.example.owm;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.TextureView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        textView = findViewById(R.id.tv);
        imageView = findViewById(R.id.image_view);
        showWeatherData();
    }

    private void showWeatherData() {
        //api key 364b781d30e5d6e4d13d3e1d6064013a

        String url = "https://api.openweathermap.org/data/2.5/weather?lat=43.728402&lon=-79.607440&appid=91c0b02fe6caf490726de810d51c39bf&units=metric";
        //TODO add &units=metric
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        textView.setText("Response: " + response.toString());
                        try {
                            JSONObject main = response.getJSONObject("main");
                            Double temprature = main.getDouble("temp");
                            Double feelsLike = main.getDouble("feels_like");
                            textView.setText("Temp: "+temprature+"\nFeels like: "+feelsLike);
                            JSONArray weather = response.getJSONArray("weather");
                            for(int i=0; i<weather.length(); i++){
                                JSONObject object = weather.getJSONObject(i);
                                String description = object.getString("description");
                                textView.setText(textView.getText()+"\nDescription: "+description );
                                String iconURL = object.getString("icon");
                                Picasso.get().load("https://openweathermap.org/img/wn/"+iconURL+"@2x.png").into(imageView);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        textView.setText("Error " + error.toString());
                    }
                });
// Access the RequestQueue through your singleton class.
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
