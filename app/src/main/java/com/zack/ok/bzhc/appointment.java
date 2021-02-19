package com.zack.ok.bzhc;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

public class appointment extends AppCompatActivity {
    EditText editTextname,editTextemail,editTextcontact;
    Button buttonfetch;
    ListView listview;
    String ClientName,ClientEmail,ClientContno;
    ProgressDialog mProgressDialog;

    public static final String KEY_ClientName = "ClientName";
    public static final String KEY_ClientEmail = "ClientEmail";
    public static final String KEY_ClientContno = "ClientContno";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        editTextname = (EditText)findViewById(R.id.etname);
        editTextemail = (EditText)findViewById(R.id.etemail);
        editTextcontact = (EditText)findViewById(R.id.etcontactno);
        buttonfetch = (Button)findViewById(R.id.btnfetch);
        listview = (ListView)findViewById(R.id.listView);
        buttonfetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClientName= editTextname.getText().toString().trim();
                ClientEmail= editTextemail.getText().toString().trim();
                ClientContno = editTextcontact.getText().toString().trim();

                if (ClientName.equals("")||(ClientEmail.equals("")||(ClientContno.equals("")))){
                    Toast.makeText(appointment.this, getString(R.string.plsenterdetail), Toast.LENGTH_SHORT).show();
                }else {

                    GetMatchData();
                }

            }
        });
    }

    private void GetMatchData() {

        ClientName = editTextname.getText().toString().trim();
        ClientEmail= editTextemail.getText().toString().trim();
        ClientContno = editTextcontact.getText().toString().trim();

        mProgressDialog = new ProgressDialog(appointment.this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMessage(getString(R.string.progress_detail));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgress(0);
        mProgressDialog.setProgressNumberFormat(null);
        mProgressDialog.setProgressPercentFormat(null);
        mProgressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config5.MATCHDATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {

                            showJSON(response);
                            mProgressDialog.dismiss();

                        } else {

                            showJSON(response);
                            mProgressDialog.dismiss();


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(appointment.this, ""+error, Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_ClientName, ClientName);
                map.put(KEY_ClientEmail, ClientEmail);
                map.put(KEY_ClientContno, ClientContno);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config5.JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String lawyerSpecialization = jo.getString(Config5.KEY_lawyerSpecialization);
                String laName = jo.getString(Config5.KEY_laName);
                String appointmentDate = jo.getString(Config5.KEY_appointmentDate);
                String appointmentTime = jo.getString(Config5.KEY_appointmentTime);




                final HashMap<String, String> appointment = new HashMap<>();
                appointment.put(Config5.KEY_lawyerSpecialization,  lawyerSpecialization);
                appointment.put(Config5.KEY_laName, getString(R.string.lawyerName) +laName);
                appointment.put(Config5.KEY_appointmentDate, getString(R.string.appdate) +appointmentDate);
                appointment.put(Config5.KEY_appointmentTime, getString(R.string.apptime) +appointmentTime);

                list.add(appointment);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                appointment.this, list, R.layout.list_item,
                new String[]{Config5.KEY_lawyerSpecialization, Config5.KEY_laName, Config5.KEY_appointmentDate,Config5.KEY_appointmentTime},
                new int[]{R.id.tvlawyerSpecialization, R.id.tvlaName, R.id.tvappointmentDate,R.id.tvappointmentTime});

        listview.setAdapter(adapter);

    }



}