package com.example.xadp5.ps2g_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class AboutUs extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        mAuth = FirebaseAuth.getInstance();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.aboutUsToolbar);
        setSupportActionBar(myToolbar);
    }
    //menu inflater items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dropmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.otherServices:
                startActivity(new Intent(AboutUs.this,OtherServices.class));
                return true;
            case R.id.aboutUs:
                startActivity(new Intent(AboutUs.this,AboutUs.class));
                return true;
            case R.id.signout:
                //add log to see if connection is not being made
                mAuth.signOut();
                finish();
                startActivity(new Intent(AboutUs.this,Main_SignIn.class));


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
