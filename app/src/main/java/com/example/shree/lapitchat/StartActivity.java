package com.example.shree.lapitchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class StartActivity extends AppCompatActivity {
    private Button btnReg,btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        btnReg=(Button) findViewById(R.id.btnReg);
        btnLogin=(Button) findViewById(R.id.btnLogin);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redIntent = new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(redIntent);

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redIntent = new Intent(StartActivity.this,LoginActivity.class);
                startActivity(redIntent);

            }
        });


    }
}
