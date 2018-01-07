package com.example.shree.lapitchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ReviewActivity extends AppCompatActivity {

    RadioGroup rgRating;
    RadioButton selectdRating;
    Button btnReviewSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        rgRating=findViewById(R.id.rgRating);
        btnReviewSubmit=findViewById(R.id.btnReviewSubmit);

        btnReviewSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedRat=rgRating.getCheckedRadioButtonId();
                selectdRating=findViewById(selectedRat);
                String finalRating=selectdRating.getText().toString();


            }
        });
    }
}
