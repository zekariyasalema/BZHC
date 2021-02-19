package com.zack.ok.bzhc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kosalgeek.android.md5simply.MD5;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.ExceptionHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class Lawyer extends AppCompatActivity implements View.OnClickListener {
    final String TAG = "Lawyer";
    EditText etEmail, etPassword;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer);
        getSupportActionBar().hide();
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    private boolean emptyValidate(EditText etEmail, EditText etPassword){
        String laEmail = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        return (laEmail.isEmpty() && password.isEmpty());
    }
    String password = "";
    @Override
    public void onClick(View v) {

        final String laEmail = etEmail.getText().toString();
        password =  MD5.encrypt(etPassword.getText().toString());

        HashMap<String, String> loginData = new HashMap<>();
        loginData.put("laEmail", laEmail);
        loginData.put("password", password);

        PostResponseAsyncTask loginTask = new PostResponseAsyncTask(this,
                loginData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                Log.d(TAG, s);
                if(s.contains("LoginSuccess")){
                    SharedPreferences pref = getSharedPreferences("loginData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("laEmail", laEmail);
                    editor.putString("password", password);
                    editor.apply();
                    Intent in = new Intent(getApplicationContext(), lawyerhome.class);
                    startActivity(in);
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.swwcantlog), Toast.LENGTH_LONG).show();
                }
            }
        });
        loginTask.setExceptionHandler(new ExceptionHandler() {
            @Override
            public void handleException(Exception e) {
                if(e != null && e.getMessage() != null){
                    Log.d(TAG, e.getMessage());
                }
            }
        });
        loginTask.execute("http://10.0.2.2:8082/BZHC/Android/includes/lawyerlogin.php");
    }
}