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

public class lappointment extends AppCompatActivity {
    EditText editTextspecialization,editTextlaname;
    Button buttonfetch;
    ListView listview;
    String lawyerSpecialization,laName;
    ProgressDialog mProgressDialog;

    public static final String KEY_lawyerSpecialization = "lawyerSpecialization";
    public static final String KEY_laName= "laName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lappointment);
        editTextspecialization = (EditText)findViewById(R.id.etcase);
        editTextlaname = (EditText)findViewById(R.id.etlaname);
        buttonfetch = (Button)findViewById(R.id.btnfetch);
        listview = (ListView)findViewById(R.id.listView);
        buttonfetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lawyerSpecialization= editTextspecialization.getText().toString().trim();
                laName= editTextlaname.getText().toString().trim();

                if (lawyerSpecialization.equals("")||(laName.equals(""))){
                    Toast.makeText(lappointment.this, getString(R.string.pleaseenter), Toast.LENGTH_SHORT).show();
                }else {

                    GetMatchData();
                }

            }
        });
    }

    private void GetMatchData() {

        lawyerSpecialization = editTextspecialization.getText().toString().trim();
        laName= editTextlaname.getText().toString().trim();

        mProgressDialog = new ProgressDialog(lappointment.this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMessage(getString(R.string.progress_detail));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgress(0);
        mProgressDialog.setProgressNumberFormat(null);
        mProgressDialog.setProgressPercentFormat(null);
        mProgressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config6.MATCHDATA_URL,
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
                        Toast.makeText(lappointment.this, ""+error, Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_lawyerSpecialization, lawyerSpecialization);
                map.put(KEY_laName, laName);
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
            JSONArray result = jsonObject.getJSONArray(Config6.JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String lawyerSpecialization = jo.getString(Config6.KEY_lawyerSpecialization);
                String  ClientName = jo.getString(Config6.KEY_ClientName);
                String  ClientEmail = jo.getString(Config6.KEY_ClientEmail);
                String  ClientAdd = jo.getString(Config6.KEY_ClientAdd);
                String appointmentDate = jo.getString(Config6.KEY_appointmentDate);
                String appointmentTime = jo.getString(Config6.KEY_appointmentTime);




                final HashMap<String, String> appointment = new HashMap<>();
                appointment.put(Config6.KEY_lawyerSpecialization,  lawyerSpecialization);
                appointment.put(Config6.KEY_ClientName, getString(R.string.cltname) +ClientName);
                appointment.put(Config6.KEY_ClientEmail, getString(R.string.cltema) +ClientEmail);
                appointment.put(Config6.KEY_ClientAdd, getString(R.string.cltadd) +ClientAdd);
                appointment.put(Config6.KEY_appointmentDate, getString(R.string.cltapp) +appointmentDate);
                appointment.put(Config6.KEY_appointmentTime, getString(R.string.clttim) +appointmentTime);

                list.add(appointment);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                lappointment.this, list, R.layout.llist_item,
                new String[]{Config6.KEY_lawyerSpecialization, Config6.KEY_ClientName, Config6.KEY_ClientEmail, Config6.KEY_ClientAdd, Config6.KEY_appointmentDate,Config6.KEY_appointmentTime},
                new int[]{R.id.tvlawyerSpecialization, R.id.tvClientName, R.id.tvClientEmail, R.id.tvClientAdd, R.id.tvappointmentDate,R.id.tvappointmentTime});

        listview.setAdapter(adapter);

    }

}