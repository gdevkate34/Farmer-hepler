package com.example.dell.farmerhelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity implements View.OnClickListener {

    private Button Login;
    private EditText Email;
    private EditText Password;
    private Button Register;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();


        if (firebaseAuth.getCurrentUser() != null) {

            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));


            Email = findViewById(R.id.editTextEmail);
            Password = findViewById(R.id.editTextPassword);
            Login = findViewById(R.id.toLogin);
            Register = findViewById(R.id.register);

            progressDialog = new ProgressDialog(this);

            Login.setOnClickListener(this);
            Register.setOnClickListener(this);
        }
    }


    private void userLogin(View view){
        String email = Email.getText().toString().trim();
        String password  = Password.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_LONG).show();
            return;
        }



        progressDialog.setMessage("Logging in...");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        }
                        else
                        {

                            Toast.makeText(getApplicationContext(),"Incorrect password or username",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {


           if(v==Register)
           {
               Toast.makeText(this, "login", Toast.LENGTH_SHORT).show();
               startActivity(new Intent(getApplicationContext(), Register.class));
           }
            if(v==Login)
            {
                     userLogin(v);
                    Toast.makeText(this,"login",Toast.LENGTH_SHORT).show();

            }

        }

    }



