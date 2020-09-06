package com.isha.doctors_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText t1,t2,t3,t4,t5;
    String hos;
    //spinner object
    Spinner spinner;
    //the hero list where we will store all the hero objects after parsing json
    ArrayList<String> leave;
    String query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1=findViewById(R.id.editText3);
        t2=findViewById(R.id.editText4);
        t3=findViewById(R.id.editText5);
        t4=findViewById(R.id.editText6);
        t5=findViewById(R.id.editText7);
        spinner = (Spinner) findViewById(R.id.spinner);
        leave = new ArrayList<>();


        //this method will fetch and parse the data
        loadHeroList();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView t=(TextView)view;
                hos=t.getText().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void GO(View view) {
        String name = t1.getText().toString();
        String email = t2.getText().toString();
        String mob = t3.getText().toString();
        String pass = t4.getText().toString();
        String cost = t5.getText().toString();
        String h = null;
        try {
            h = URLEncoder.encode(hos, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        RequestQueue rq = Volley.newRequestQueue(MainActivity.this);
        String url = "http://malnirisha.in.net/hospital/doc_insert.php?name=" + name + "&password=" + pass + "&email=" + email + "&mobile=" + mob + "&cost=" + cost + "&hos=" + h;

        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("1")) {
                    Toast.makeText(MainActivity.this, "Sign Up Success", Toast.LENGTH_SHORT).show();
                }
                if (response.trim().equals("0")) {
                    Toast.makeText(MainActivity.this, "Email Exist", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
        sr.setShouldCache(false);
        sr.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(sr);


    }

    private void loadHeroList() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://malnirisha.in.net/hospital/hospital_get.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       /* //hiding the progressbar after completion
                        progressBar.setVisibility(View.INVISIBLE);*/


                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray heroArray = obj.getJSONArray("hospitals");

                            if(heroArray.length()==0){
                                Toast.makeText(MainActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                            }

                            //now looping through all the elements of the json array
                            for (int i = 0; i < heroArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject heroObject = heroArray.getJSONObject(i);

                                String name = heroObject.getString("name");
                                //adding the hero to herolist
                                leave.add(name);
                            }

                            //adding the adapter to listview
                            spinner.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,leave));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}
