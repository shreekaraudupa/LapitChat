package com.example.shree.lapitchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText etDisplayName,etEmail,etPassword;
    Button btnRegSubmit;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog regProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        regProgress=new ProgressDialog(this);

        etDisplayName=findViewById(R.id.etDisplayName);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btnRegSubmit=(Button) findViewById(R.id.btnRegSubmit);

        btnRegSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String display_name=etDisplayName.getText().toString();
                String email=etEmail.getText().toString();
                String password=etPassword.getText().toString();
                if(!TextUtils.isEmpty(display_name) ||!TextUtils.isEmpty(email)  ||!TextUtils.isEmpty(password)  ){

                    regProgress.setTitle("Registering user..");
                    regProgress.setMessage("Please wait");
                    regProgress.setCanceledOnTouchOutside(false);
                    regProgress.show();
                    reg_user(display_name,email,password);
                }else{
                    Toast.makeText(getApplicationContext(),"Empty fields",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void reg_user(final String display_name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser curr_user=FirebaseAuth.getInstance().getCurrentUser();
                            String uid=curr_user.getUid();
                            //mDatabase is the root directory
                            mDatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                            //To enter complex data we enter it in the form of hashmap

                            HashMap<String,String> userMap = new HashMap<>();
                            userMap.put("name",display_name);
                            userMap.put("status","Hi there i m using this cool app ");
                            userMap.put("image","default");
                            userMap.put("thumb","default");

                            mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        regProgress.dismiss();
                                        Intent mainIntent=new Intent(RegisterActivity.this,MainActivity.class);
                                        //on pressing back it shouldnt go to main activity so clear task
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        finish(); //shouldnt comeback

                                    }
                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
                            regProgress.hide();
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
}
