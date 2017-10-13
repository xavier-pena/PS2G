package com.example.xadp5.ps2g_app;

        import android.content.Intent;
        import android.graphics.PorterDuff;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import static com.example.xadp5.ps2g_app.R.color.royalBlue;


public class Main_SignIn extends AppCompatActivity {

    private EditText emailTxt;
    private EditText passwordTxt;
    private Button login;
    private Button createAccBtn;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ImageView iv;
    private  User User;
    private String Access;
    public FirebaseAuth mAuth;
    private boolean notNull=false;
    private DatabaseReference db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_signin);
        mAuth= FirebaseAuth.getInstance();

        db= FirebaseDatabase.getInstance().getReference().child("User");



        emailTxt = (EditText) findViewById(R.id.emailField);
        passwordTxt = (EditText) findViewById(R.id.passField);
        login = (Button) findViewById(R.id.LoginButton);
        createAccBtn=(Button) findViewById(R.id.createAccount);
        iv = (ImageView)findViewById(R.id.ps2g);
        iv.setImageResource(R.drawable.ps2g_logo);










        //this checks the state of the sign in. if the state isn't null(meaning signed in)
        //it checks the value for account access
        //if admin it sends to admin home, if not it sends to client account page
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null){


                    User=new User(mAuth.getCurrentUser().getUid());
                    db= FirebaseDatabase.getInstance().getReference().child("User").child(User.getUID());
                    ValueEventListener v = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String userAccess=dataSnapshot.child("AccountAccess").getValue(String.class);
                            User.setUserAccess(userAccess);
                            startNextActivity();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    };
                    db.addListenerForSingleValueEvent(v);




                    startNextActivity();


                }
            }
        };



        emailTxt.getBackground().setColorFilter(getResources().getColor(royalBlue), PorterDuff.Mode.SRC_IN);
        passwordTxt.getBackground().setColorFilter(getResources().getColor(royalBlue), PorterDuff.Mode.SRC_IN);




        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //what button does

                startSignIn();


            }
        });

        createAccBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(Main_SignIn.this, NewAccount.class));

            }
        });



    }//closes on create



    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }



    private void startSignIn() {

        String email = emailTxt.getText().toString();
        String pass = passwordTxt.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){

            Toast.makeText(Main_SignIn.this,"One or more fields are empty!", Toast.LENGTH_SHORT).show();

        }
        else{

            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){

                        Toast.makeText(Main_SignIn.this,"Sign in Problem", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        }//closes else


    }//start sign in


    public void startNextActivity() {
        Access=User.getAccess();

        if (Access != null) {
            Log.d("null?", notNull + "");

            Log.d("made it:", "main activity sign in");

            if (Access.equalsIgnoreCase("Admin")) {
                notNull = true;
                startActivity(new Intent(Main_SignIn.this, AdminHome.class));


            } else {
                notNull = true;
                startActivity(new Intent(Main_SignIn.this, ClientPage.class));

            }

        }
    }
}
