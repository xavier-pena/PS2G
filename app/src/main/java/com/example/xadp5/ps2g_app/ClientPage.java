package com.example.xadp5.ps2g_app;

import android.content.Intent;
import android.provider.ContactsContract;
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
import android.widget.ProgressBar;
import android.widget.QuickContactBadge;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class ClientPage extends AppCompatActivity {


    private TabHost tab;
    private TextView agencyView;
    private Spinner homeSpinner;
    private ArrayAdapter<String> dbAdapter;
    private ProgressBar PB;
    private String selected;
    private User User;
    private FirebaseAuth mAuth;
    private DatabaseReference db;
    private Spinner contactSpinner;
    private ArrayAdapter<String> contactSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_page);
        mAuth=FirebaseAuth.getInstance();
        User = new User(mAuth.getCurrentUser().getUid());

        db= FirebaseDatabase.getInstance().getReference().child("User").child(User.getUID());

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String AgencyName = dataSnapshot.child("Agency").getValue(String.class);
                User.setAgencyName(AgencyName);
                agencyView.setText(User.getAgencyName());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        agencyView=(TextView) findViewById(R.id.agencyView);
        homeSpinner=(Spinner) findViewById(R.id.projectSpinner);
        dbAdapter=new ArrayAdapter<>(ClientPage.this,android.R.layout.simple_spinner_dropdown_item,User.getProjects());
        PB=(ProgressBar) findViewById(R.id.progressBar2);



        //contact

        String[] names= {"Add","Delete"};
        contactSpinner=(Spinner) findViewById(R.id.spinner2);
        contactSpinnerAdapter= new ArrayAdapter<>(ClientPage.this,android.R.layout.simple_spinner_dropdown_item,names);
        contactSpinner.setAdapter(contactSpinnerAdapter);






        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);




        //tab host
        tab=(TabHost) findViewById(R.id.tabHost);
        tab.setup();
        TabHost.TabSpec spec1= tab.newTabSpec("Home");

        spec1.setIndicator(spec1.getTag());
        spec1.setContent(R.id.home);
        tab.addTab(spec1);

        TabHost.TabSpec spec2= tab.newTabSpec("Projects");
        spec2.setIndicator(spec2.getTag());
        spec2.setContent(R.id.projects);
        tab.addTab(spec2);

        TabHost.TabSpec spec3= tab.newTabSpec("Account");
        spec3.setIndicator(spec3.getTag());
        spec3.setContent(R.id.account);
        tab.addTab(spec3);

        TabHost.TabSpec spec4= tab.newTabSpec("Contact");
        spec4.setIndicator(spec4.getTag());
        spec4.setContent(R.id.contacts);
        tab.addTab(spec4);



        //WILL PUT HOME STUFF HERE
        addSpinnerItems(homeSpinner);
        addListenerOnItemSelection(homeSpinner);






    }//closes on create





    //dynamically add items on spinner from firebase db
    //project names are permenant rn because they are keys :/

    public void addSpinnerItems(Spinner spinner){
        db = FirebaseDatabase.getInstance().getReference().child("User").child(User.getUID()).child("Projects");

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String keys=dataSnapshot.getKey();

                User.getProjects().add(keys);

                dbAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        spinner.setAdapter(dbAdapter);


    }


    //changes the progress bar depending on project

    public void addListenerOnItemSelection(final Spinner spinner){

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //spinner.setSelection(1);
                selected=spinner.getSelectedItem().toString();
                Log.d("Selected", selected);
                db = FirebaseDatabase.getInstance().getReference().child("User").child(User.getUID()).child("Projects").child(selected).child("percentage complete");

                db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String pp = dataSnapshot.getValue(String.class);
                            System.out.println(pp);
                            PB.setProgress(Integer.parseInt(pp));
                            User.setProjectPercentage(pp);
                }

                    @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                });




            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.getCurrentUser();



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
                startActivity(new Intent(ClientPage.this,OtherServices.class));
                return true;
            case R.id.aboutUs:
                startActivity(new Intent(ClientPage.this,AboutUs.class));
                return true;
            case R.id.signout:
                //add log to see if connection is not being made
                mAuth.signOut();
                finish();
                startActivity(new Intent(ClientPage.this,Main_SignIn.class));


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }





    public void onStop(){
        super.onStop();


    }


}
