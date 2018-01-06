package com.example.shree.lapitchat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    AlertDialog.Builder alertDialogBuilder ;

    //private Toolbar mtoolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("You want to Logout?");
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alertDialogBuilder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        /*
        mtoolbar=(Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("lapitChat");
        */
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null)
        {
            sendToStart();
        }
    }

    private void sendToStart() {
        Toast.makeText(getApplicationContext(),"Not logged in ",Toast.LENGTH_SHORT).show();
        Intent startIntent=new Intent(MainActivity.this,StartActivity.class);
        startActivity(startIntent);
        finish();  //User cant comeback on pressing back button
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);
         if(item.getItemId()==R.id.main_logout){
             alertDialogBuilder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                     FirebaseAuth.getInstance().signOut();
                     sendToStart();
                     dialogInterface.cancel();
                 }
             });
             alertDialogBuilder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                     Toast.makeText(getApplicationContext(),"Still logged in",Toast.LENGTH_LONG).show();

                 }
             });

             alertDialogBuilder.show();
         }else if(item.getItemId()==R.id.main_setting){
             Intent settingIntent=new Intent(MainActivity.this,SettingsActivity.class);
             startActivity(settingIntent);

         }else if(item.getItemId()==R.id.about){
             Toast.makeText(getApplicationContext(),"Developed by Shree",Toast.LENGTH_SHORT).show();

         }

         return true;
    }
}
