package com.example.shree.lapitchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListOfProjects extends AppCompatActivity {

    Button btnSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_projects);
        btnSelected=findViewById(R.id.btnSelected);
        btnSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reviewIntent=new Intent(ListOfProjects.this,ReviewActivity.class);
                startActivity(reviewIntent);
            }
        });
    }
}
