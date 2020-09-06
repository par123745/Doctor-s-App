package com.isha.doctors_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Appointment extends AppCompatActivity {
    //the URL having the json data

    //listview object
    ListView listView;

    //the hero list where we will store all the hero objects after parsing json
    List<Model> leave;

    String doc_email,contact,date,time,desc,name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        //initializing listview and hero list
        listView = (ListView) findViewById(R.id.list);
        leave = new ArrayList<>();
        doc_email = getIntent().getStringExtra("email");

        //this method will fetch and parse the data
        loadHeroList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView Id =  view.findViewById(R.id.textView1);
                String hosId = Id.getText().toString();
                Intent i =new Intent(Appointment.this,Appointment_details.class);
                i.putExtra("name",name);
                i.putExtra("contact",contact);
                i.putExtra("date",date);
                i.putExtra("time",time);
                i.putExtra("desc",desc);
                startActivity(i);
            }
        });
    }
    private void loadHeroList() {
        //getting the progressbar
        /*final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);*/

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://malnirisha.in.net/hospital/appoint_get.php?doc="+doc_email,
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
                            JSONArray heroArray = obj.getJSONArray("appointment");

                            if(heroArray.length()==0){
                                Toast.makeText(Appointment.this, "No Data", Toast.LENGTH_SHORT).show();
                            }

                            //now looping through all the elements of the json array
                            for (int i = 0; i < heroArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject heroObject = heroArray.getJSONObject(i);

                                name = heroObject.getString("name");
                                contact = heroObject.getString("contact");
                                date = heroObject.getString("date");
                                time = heroObject.getString("time");
                                desc = heroObject.getString("desc");
                                String id = heroObject.getString("id");


                                //creating a hero object and giving them the values from json object
                                Model model = new Model(name,id);

                                //adding the hero to herolist
                                leave.add(model);
                            }

                            //creating custom adapter object
                            List_adapter adapter = new List_adapter(leave, getApplicationContext());

                            //adding the adapter to listview
                            listView.setAdapter(adapter);

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


