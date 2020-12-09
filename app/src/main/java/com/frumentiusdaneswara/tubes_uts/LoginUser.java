package com.frumentiusdaneswara.tubes_uts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginUser extends AppCompatActivity {

    TextInputLayout emailText, passwordText;
    MaterialButton btnSignUp, btnSignIn;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    private String CHANNEL_ID = "Channel 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        Window window = LoginUser.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(LoginUser.this, R.color.colorPrimary));

        emailText       = (TextInputLayout) findViewById(R.id.inputEmail);
        passwordText    = (TextInputLayout) findViewById(R.id.inputPassword);
        btnSignUp       = (MaterialButton)findViewById(R.id.btnSignUp);
        btnSignIn       = (MaterialButton)findViewById(R.id.btnSignIn);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseAuth.getCurrentUser() != null && firebaseUser.isEmailVerified()){
            Toast.makeText(LoginUser.this,"Welcome Back!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),searchActivity.class));
            finish();
        }

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getEditText().getText().toString().trim();
                final String password = passwordText.getEditText().getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    emailText.setError("Email is empty.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    passwordText.setError("Password is empty.");
                    return;
                }

                if(password.length() < 6){
                    passwordText.setError("Password must be >= 6 Characters");
                    return;
                }

                else{
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                if (firebaseUser != null && firebaseUser.isEmailVerified()){
                                    Toast.makeText(LoginUser.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),searchActivity.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(LoginUser.this, "Please Verify Your Account!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(LoginUser.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }


        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterUser.class));
            }
        });
    }


}