package com.example.landview;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Weather extends AppCompatActivity {
    static final String API_KEY = "57bac8d8bf773db943c0a945fb404977";
    private EditText edtCity;
    private ImageView imgStatus;
    private Button btnFind,btnNextday;
    private TextView txtNameCity,txtNameNation,txtDegree,txtStatus,txtCloud,txtRainy,txtwind,txtNowDegree;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_weather);
        //initUi
        initUI();
        //

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textCity = edtCity.getText().toString().trim();
                if(!checkInput(textCity))
                {
                    getJsonWeather(textCity);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please type city",Toast.LENGTH_LONG).show();
                }
            }
        });
        //Proccess when press Enter
        edtCity.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction()==KeyEvent.ACTION_DOWN)
                {
                    switch (i)
                    {
                        case KeyEvent.KEYCODE_ENTER:
                            getJsonWeather(edtCity.getText().toString().trim());
                            break;
                    }

                }
                return false;
            }
        });
        btnNextday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = edtCity.getText().toString().trim();

                if(checkInput(text))
                {
                    Toast.makeText(getApplicationContext(),"Please type city",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(),Weathernext.class);
                    intent.putExtra("city",text);
                    startActivity(intent);
                }

            }
        });
    }

    public boolean checkInput(String text)
    {
        if(text.length()==0)
        {
            return true;
        }
        return false;
    }
    public void getJsonWeather(String city)
    {
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+API_KEY+"&units=metric";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                            L???y th??ng tin weather : icon,tr???ng th??i
                            JSONArray weatherArray = response.getJSONArray("weather");
                            JSONObject weatherObj = weatherArray.getJSONObject(0);
                            String icon = weatherObj.getString("icon");
                            String urlIcon = "http://openweathermap.org/img/wn/"+icon+".png";
                            Picasso.get().load(urlIcon).into(imgStatus);

                            String weatherStatus = weatherObj.getString("main");
                            txtStatus.setText(weatherStatus);
//                            l???y th??ng tin main: nhi???t ?????,????? ???m
                            JSONObject tempObj =response.getJSONObject("main");
                            String temp = tempObj.getString("temp");
                            txtDegree.setText(temp+"??C");
                            String humidity = tempObj.getString("humidity");
                            txtRainy.setText(humidity+"%");
                            // L???y th??ng tin wind: t???c ????? gi??
                            JSONObject wind =response.getJSONObject("wind");
                            String windSpeed = wind.getString("speed");
                            txtwind.setText(windSpeed+"m/s");
                            //l???y th??ng tin clouds : m??y
                            JSONObject clouds = response.getJSONObject("clouds");
                            String cloud = clouds.getString("all");
                            txtCloud.setText(cloud+"%");
                            //l???y ng??y nhi???t ????? hi???n t???i
                            String date = response.getString("dt");
                            Date datenow = new Date(Long.parseLong(date)*1000);//chuy???n v??? d???ng long
                            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd--MM--yyyy HH::mm::ss");
                            String currentDate = dateFormat.format(datenow);
                            txtNowDegree.setText(currentDate);
                            //l???y th??ng tin t??n th??nh ph???
                            String city = response.getString("name");
                            txtNameCity.setText(city);
                            //l???y th??ng tin n?????c
                            JSONObject sys = response.getJSONObject("sys");
                            String nation = sys.getString("country");
                            txtNameNation.setText(nation);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Don't find city!",Toast.LENGTH_LONG).show();
                        edtCity.setText("");
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
    private void initUI() {
        edtCity = findViewById(R.id.edtCountry);
        btnFind = findViewById(R.id.btnFindCity);
        btnNextday = findViewById(R.id.btnMore);
        txtNameCity = findViewById(R.id.txtWname);
        txtNameNation = findViewById(R.id.txtWnation);
        txtDegree = findViewById(R.id.txtDegree);
        txtStatus = findViewById(R.id.txtStatus);
        txtCloud = findViewById(R.id.txtCloud);
        txtRainy = findViewById(R.id.txtRainy);
        txtwind = findViewById(R.id.txtwind);
        txtNowDegree = findViewById(R.id.txtnow);
        imgStatus = findViewById(R.id.imgStatus);
    }
}

