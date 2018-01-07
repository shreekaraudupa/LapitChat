package com.example.shree.lapitchat;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ReviewActivity extends AppCompatActivity {

    RadioGroup rgRating;
    RadioButton selectdRating;
    Button btnReviewSubmit,btnCamera,btnFile;
    EditText etDescription;
    private static final int  CAMERA_REQUEST_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        rgRating=findViewById(R.id.rgRating);
        btnReviewSubmit=findViewById(R.id.btnReviewSubmit);
        btnCamera=findViewById(R.id.btnCamera);
        btnFile=findViewById(R.id.btnFile);
        etDescription=findViewById(R.id.etDescription);

        btnReviewSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedRat=rgRating.getCheckedRadioButtonId();
                selectdRating=findViewById(selectedRat);
                String finalRating=selectdRating.getText().toString();


            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,CAMERA_REQUEST_CODE);
                //After user caputers image it will call onActivity result method
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
