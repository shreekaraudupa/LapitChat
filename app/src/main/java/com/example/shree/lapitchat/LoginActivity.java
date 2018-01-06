package com.example.shree.lapitchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText loginEmail,loginPassword;
    ProgressDialog loginProgress;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail=findViewById(R.id.loginEmail);
        loginPassword=findViewById(R.id.loginPassword);
        btnLogin=findViewById(R.id.btnLogin);
        mAuth = FirebaseAuth.getInstance();
        loginProgress=new ProgressDialog(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String lEmail=loginEmail.getText().toString();
                String lPassword=loginPassword.getText().toString();

                if(!TextUtils.isEmpty(lEmail) || !TextUtils.isEmpty(lPassword)){
                    loginProgress.setTitle("Logging in");
                    loginProgress.setMessage("Please wait");
                    loginProgress.setCanceledOnTouchOutside(false);
                    loginProgress.show();

                    loginUser(lEmail,lPassword);
                }else{
                    Toast.makeText(getApplicationContext(),"Field is empty ",Toast.LENGTH_LONG).show();
                    loginEmail.requestFocus();
                }
            }
        });


    }

    private void loginUser(String lEmail, String lPassword) {
    mAuth.signInWithEmailAndPassword(lEmail, lPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                loginProgress.dismiss();
                Intent logIntent=new Intent(LoginActivity.this,MainActivity.class);
                //on pressing back it shouldnt go to main activity so clear task
                logIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logIntent);
                finish();
            }else{
                loginProgress.hide();
                loginEmail.setText("");
                loginPassword.setText("");
                loginEmail.requestFocus();
                Toast.makeText(getApplicationContext(),"Cannot signin ",Toast.LENGTH_LONG).show();
            }
        }
    });
    }

}
