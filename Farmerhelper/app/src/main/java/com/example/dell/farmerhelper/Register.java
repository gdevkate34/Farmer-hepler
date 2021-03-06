package com.example.dell.farmerhelper;


        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Patterns;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity implements View.OnClickListener {

   private EditText editTextEmail,editTextPassword;


    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail=(EditText) findViewById(R.id.editTextEmail) ;
        editTextPassword=(EditText) findViewById(R.id.editTextPassword);


        progressDialog=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.signup).setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.signup:
                registerUser(v);

        }

    }

    public void registerUser(View view) {

        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();

        if(email.isEmpty()) {

            editTextEmail.setError("Email is required !");
            editTextEmail.requestFocus();
            return;

        }


        if(password.isEmpty()) {

            editTextPassword.setError("Password is required !");
            editTextPassword.requestFocus();
            return;

        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editTextEmail.setError("Please enter valid email !!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            editTextPassword.setError("Minimum length of password should be 6 !");
            editTextPassword.requestFocus();
            return;
        }

        progressDialog.setMessage("Registering..");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Registered successfully !",Toast.LENGTH_SHORT).show();


                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Registration failed", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
