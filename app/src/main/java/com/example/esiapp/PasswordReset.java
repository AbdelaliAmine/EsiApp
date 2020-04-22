package com.example.esiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordReset extends AppCompatActivity {

    EditText email ;
    Button confirmButton;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        setContentView(R.layout.activity_password_reset);
        setViews();
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
                if (validate()) {
                    confirmButton.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    String Resetemail = email.getText().toString();
                    fAuth.sendPasswordResetEmail(Resetemail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(PasswordReset.this, "Reset Link Sent To Your Email", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), (Login.class)));
                            finish();
                        }
                    }).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PasswordReset.this, "Error!! Reset link is not sent"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void setViews ()
    {
        email =findViewById(R.id.email);
        confirmButton = findViewById(R.id.confirm1);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.reset_password_progressBar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public boolean validate()
    {
        boolean result ;
        String semail = email.getText().toString();
        if (TextUtils.isEmpty(semail))
        {
            PasswordReset.this.email.setError("email is required email is not found");
            result=false ;
        }
        else if (!semail.contains("esi-sba.dz")) {
            PasswordReset.this.email.setError("you are not an esi sba student");
            result = false;
        }
        else result=true ;

        return result ;
    }
}
