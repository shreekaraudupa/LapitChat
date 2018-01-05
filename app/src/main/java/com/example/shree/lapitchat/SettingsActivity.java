package com.example.shree.lapitchat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

 public class SettingsActivity extends AppCompatActivity {

    private DatabaseReference mUserDatabase,mStatus;
    FirebaseUser mcurrUser;
    CircleImageView CircleDP;
    TextView tvSettingName,tvStatus;
    Button btnStatus,btnImg;
    String m_Text;
    ProgressDialog mProgressDialog;
    private static final int GallleryPick=1;

     private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mcurrUser= FirebaseAuth.getInstance().getCurrentUser();
        CircleDP=findViewById(R.id.CircleDP);
        tvSettingName=findViewById(R.id.tvSettingName);
        tvStatus=findViewById(R.id.tvStatus);
        btnStatus=findViewById(R.id.btnStatus);
        btnImg=findViewById(R.id.btnImg);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        String curr_uid=mcurrUser.getUid();
        mUserDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(curr_uid);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("name").getValue().toString();
                String image=dataSnapshot.child("image").getValue().toString();
                String status=dataSnapshot.child("status").getValue().toString();

                tvSettingName.setText(name);
                tvStatus.setText(status);
                Picasso.with(SettingsActivity.this).load(image).into(CircleDP);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("Title");

// Set up the input
                final EditText input = new EditText(SettingsActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         m_Text = input.getText().toString();

                        mcurrUser=FirebaseAuth.getInstance().getCurrentUser();
                        String cu_uid=mcurrUser.getUid();

                        mStatus=FirebaseDatabase.getInstance().getReference().child("Users").child(cu_uid);
                        mStatus.child("status").setValue(m_Text);
                        Toast.makeText(getApplicationContext(),m_Text,Toast.LENGTH_LONG).show();


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });

        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent=new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent,"Select Imsge"),GallleryPick);
                /*
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(SettingsActivity.this);
                */
            }
        });

    }

     @Override
     protected void  onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GallleryPick && resultCode==RESULT_OK){
            Uri imageUri=data.getData();
            CropImage.activity(imageUri).setAspectRatio(1,1)
                    .start(this);
        }
         if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
             CropImage.ActivityResult result = CropImage.getActivityResult(data);
             if (resultCode == RESULT_OK) {
                 //will start uploading
                 mProgressDialog =new ProgressDialog(SettingsActivity.this);
                 mProgressDialog.setTitle("Uploading immage");
                 mProgressDialog.setMessage("Please wait");
                 mProgressDialog.setCanceledOnTouchOutside(false);
                 mProgressDialog.show();

                 Uri resultUri = result.getUri();

                 String currUser=mcurrUser.getUid();
                 StorageReference filepath=mStorageRef.child("profile_image").child(currUser+".jpg");
                 filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                         if(task.isSuccessful()){
                             String downUrl=task.getResult().getDownloadUrl().toString();
                             mUserDatabase.child("image").setValue(downUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     if(task.isSuccessful()){
                                         mProgressDialog.dismiss();
                                         Toast.makeText(getApplicationContext(),"kadak dp  ",Toast.LENGTH_LONG).show();

                                     }
                                 }
                             });
                             //Toast.makeText(getApplicationContext(),"working",Toast.LENGTH_LONG).show();

                         }else {
                             mProgressDialog.dismiss();
                             Toast.makeText(getApplicationContext(),"Not working",Toast.LENGTH_LONG).show();

                         }
                     }
                 });

             } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                 Exception error = result.getError();
             }
         }

    }


 }
