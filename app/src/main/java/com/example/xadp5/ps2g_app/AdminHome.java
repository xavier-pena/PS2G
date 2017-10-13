package com.example.xadp5.ps2g_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminHome extends AppCompatActivity{

    private Button adminBtn;
    private FirebaseAuth mAuth;
    private ListView LV;
    private Button newAccBtn,newSurveyBtn;
    private ArrayList<String> agencyArray;
    private DatabaseReference db;
    private ArrayAdapter dbAdapter;
    private User User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_index);

        mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser();

        LV=(ListView) findViewById(R.id.agencyList);
        agencyArray=new ArrayList<>();
        dbAdapter=new ArrayAdapter<>(AdminHome.this,android.R.layout.simple_spinner_dropdown_item,agencyArray);




        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar);

        addSpinnerItems(LV);

        //LV.setAdapter(dbAdapter);
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(AdminHome.this, AdminPage.class));

            }
        });


    }//closes on create


    public void addSpinnerItems(ListView LV){
        db = FirebaseDatabase.getInstance().getReference().child("User");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               for(DataSnapshot child:dataSnapshot.getChildren()){

                   String name = (String) child.child("Agency").getValue();
                   Log.d("adding", name);
                   agencyArray.add(name);
                   dbAdapter.notifyDataSetChanged();


               }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        LV.setAdapter(dbAdapter);



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
                startActivity(new Intent(AdminHome.this,OtherServices.class));
                return true;
            case R.id.aboutUs:
                startActivity(new Intent(AdminHome.this,AboutUs.class));
                return true;
            case R.id.signout:
                //add log to see if connection is not being made
                mAuth.signOut();
                finish();
                startActivity(new Intent(AdminHome.this,Main_SignIn.class));


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
