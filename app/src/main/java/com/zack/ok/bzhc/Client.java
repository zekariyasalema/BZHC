package com.zack.ok.bzhc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kosalgeek.android.md5simply.MD5;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class Client extends AppCompatActivity implements View.OnClickListener {
    final String TAG = "RegisterActivity";
    EditText etEmail,etFullName,etAddress,etCity,etPassword, etConfirmPassword ;
    RadioGroup etGender;
    TextView btnlogin;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        etFullName=(EditText)findViewById(R.id.etFullName);
        etAddress=(EditText)findViewById(R.id.etAddress);
        etCity=(EditText)findViewById(R.id.etCity);
        etGender=(RadioGroup)findViewById(R.id.etGender);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etConfirmPassword = (EditText)findViewById(R.id.etConfirmPassword);
        btnlogin=(TextView) findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Client.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        if(!emptyValidate(etFullName,etAddress,etCity,etGender,etEmail, etPassword, etConfirmPassword)){
            if(passwordValidate(etPassword, etConfirmPassword)){
                String fullName = etFullName.getText().toString();
                String address = etAddress.getText().toString();
                String city = etCity.getText().toString();
                String gender = ((RadioButton) findViewById(etGender.getCheckedRadioButtonId())).getText().toString();
                String email = etEmail.getText().toString();
                String password = MD5.encrypt(etPassword.getText().toString());

                HashMap<String, String> postData = new HashMap<>();
                postData.put("fullName", fullName);
                postData.put("address", address);
                postData.put("city", city);
                postData.put("gender", gender);
                postData.put("email", email);
                postData.put("password", password);

                PostResponseAsyncTask task1 = new PostResponseAsyncTask(this,
                        postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.d(TAG, s);
                        if(s.contains("ErrorInsert")){
                            Toast.makeText(Client.this,
                                    getString(R.string.datanotinsrtd),
                                    Toast.LENGTH_LONG).show();
                        }else {
                            Intent in = new Intent(getApplicationContext(),
                                    LoginActivity.class);
                            startActivity(in);
                        }
                    }
                });
                task1.execute("http://10.0.2.2:8082/BZHC/Android/includes/register.php");
            }
            else{  // not equals
                Toast.makeText(getApplicationContext(),
                        getString(R.string.msure),
                        Toast.LENGTH_LONG).show();
            }
        } else{
            Toast.makeText(getApplicationContext(), getString(R.string.fillall),
                    Toast.LENGTH_LONG).show();
        }
    }

    private boolean emptyValidate(EditText etFullName,
                                  EditText etAddress,
                                  EditText etCity,
                                  RadioGroup etGender,
                                  EditText etEmail,
                                  EditText etPassword,
                                  EditText etConfirmPassword){
        String fullName = etFullName.getText().toString();
        String address = etAddress.getText().toString();
        String city = etCity.getText().toString();
        String gender = ((RadioButton) findViewById(etGender.getCheckedRadioButtonId())).getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirm = etConfirmPassword.getText().toString();
        return (email.isEmpty() && password.isEmpty() && confirm.isEmpty());
    }

    private boolean passwordValidate(EditText etPassword,
                                     EditText etConfirmPassword){
        String password = etPassword.getText().toString();
        String confirm = etConfirmPassword.getText().toString();
        return (password.equals(confirm));
    }
}