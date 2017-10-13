package com.example.xadp5.ps2g_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import com.google.firebase.auth.FirebaseAuth;

public class AdminPage extends AppCompatActivity {

    Button newProjectBtn;
    private TabHost tab;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        mAuth = FirebaseAuth.getInstance();
        newProjectBtn = (Button) findViewById(R.id.newProject);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_admin_toolbar);
        setSupportActionBar(myToolbar);

        newProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AdminPage.this, NewProject.class));

            }
        });


        tab = (TabHost) findViewById(R.id.admin_tabHost);
        tab.setup();
        TabHost.TabSpec spec1 = tab.newTabSpec("Home");

        spec1.setIndicator(spec1.getTag());
        spec1.setContent(R.id.admin_home_in);
        tab.addTab(spec1);

        TabHost.TabSpec spec2 = tab.newTabSpec("Projects");
        spec2.setIndicator(spec2.getTag());
        spec2.setContent(R.id.admin_projects_in);
        tab.addTab(spec2);

        TabHost.TabSpec spec3 = tab.newTabSpec("Account");
        spec3.setIndicator(spec3.getTag());
        spec3.setContent(R.id.admin_account_in);
        tab.addTab(spec3);

        TabHost.TabSpec spec4 = tab.newTabSpec("Contact");
        spec4.setIndicator(spec4.getTag());
        spec4.setContent(R.id.admin_contacts_in);
        tab.addTab(spec4);
    }


    //menu inflater items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dropmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.otherServices:
                startActivity(new Intent(AdminPage.this,OtherServices.class));
                return true;
            case R.id.aboutUs:
                startActivity(new Intent(AdminPage.this,AboutUs.class));
                return true;
            case R.id.signout:
                //add log to see if connection is not being made
                mAuth.signOut();
                finish();
                startActivity(new Intent(AdminPage.this,Main_SignIn.class));


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
