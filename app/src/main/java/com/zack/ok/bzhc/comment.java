package com.zack.ok.bzhc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class comment extends AppCompatActivity implements View.OnClickListener {
    final String TAG = "comment";
    EditText etEmail,etFullName,etContactno,etMessage ;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        etFullName=(EditText)findViewById(R.id.etFullName);
        etContactno=(EditText)findViewById(R.id.etContactno);
        etMessage=(EditText)findViewById(R.id.etMessage);
        etEmail = (EditText)findViewById(R.id.etEmail);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        if (!emptyValidate(etFullName, etContactno, etMessage, etEmail)) {
            String fullname = etFullName.getText().toString();
            String contactno = etContactno.getText().toString();
            String message = etMessage.getText().toString();
            String email = etEmail.getText().toString();

            HashMap<String, String> postData = new HashMap<>();
            postData.put("fullname", fullname);
            postData.put("contactno", contactno);
            postData.put("message", message);
            postData.put("email", email);

            PostResponseAsyncTask task1 = new PostResponseAsyncTask(this,
                    postData, new AsyncResponse() {
                @Override
                public void processFinish(String s) {
                    Log.d(TAG, s);
                    if (s.contains("ErrorInsert")) {
                        Toast.makeText(comment.this,
                                getString(R.string.swwwdwninsrtd),
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(comment.this,
                                getString(R.string.sentsucces),
                                Toast.LENGTH_LONG).show();
                        Intent in = new Intent(getApplicationContext(),
                                Clienthome.class);
                        startActivity(in);
                    }
                }
            });
            task1.execute("http://10.0.2.2:8082/BZHC/Android/includes/comment.php");

        }
    }
        private boolean emptyValidate(EditText etFullName,
                                      EditText etContactno,
                                      EditText etMessage,
                                      EditText etEmail) {
            String fullname = etFullName.getText().toString();
            String contactno = etContactno.getText().toString();
            String message= etMessage.getText().toString();
            String email = etEmail.getText().toString();


            return (email.isEmpty());
        }
}
