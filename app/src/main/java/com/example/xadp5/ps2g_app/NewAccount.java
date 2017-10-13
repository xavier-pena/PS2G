package com.example.xadp5.ps2g_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewAccount extends AppCompatActivity {

    private DatabaseReference db;
    private EditText createEmail;
    private EditText createPassword;
    private EditText createAgency;
    private Button createAccountBtn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String agency;
    //private HashMap<String,String> myMap;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.xadp5.ps2g_app.R.layout.new_account);

        db = FirebaseDatabase.getInstance().getReference().child("User");
        createEmail = (EditText) findViewById(R.id.editEmail);
        createPassword = (EditText) findViewById(R.id.editPass);
        createAgency = (EditText) findViewById(R.id.editAgency);
        createAccountBtn=(Button) findViewById(R.id.submitAccount);
        mAuth = FirebaseAuth.getInstance();
        //myMap= new HashMap<String,String>();




        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null){
                    String uid=mAuth.getCurrentUser().getUid();

                    db.child(uid).child("Agency").setValue(agency);

                    startActivity(new Intent(NewAccount.this, ClientPage.class) );

                }
            }
        };

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                createUser();



            }
        });






    }//closes on create

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }






    private void createUser(){

        String email = createEmail.getText().toString().trim();
        String pass = createPassword.getText().toString().trim();
        agency = createAgency.getText().toString().trim();


       /*myMap.put("Email",email);
        myMap.put("Pass",pass);
        myMap.put("Agency", agency);*/


        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(agency)){

            Toast.makeText(NewAccount.this,"One or more fields are empty!", Toast.LENGTH_SHORT).show();

        }
        else {
            //creating account
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information

                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(NewAccount.this, "User Created.",
                                Toast.LENGTH_SHORT).show();

                    } else {// If sign in fails, display a message to the user.

                        Toast.makeText(NewAccount.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                    }


                }
            });

        }//closes else




    }//closes create user

}
