package com.frumentiusdaneswara.tubes_uts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {

    TextInputLayout nameText, phoneNumber, emailText, passwordText;
    TextView result; //buat temp-nya hashcode
    MaterialButton btnSignUp, btnSignIn;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressBar progressBar;
    //    User user;
    private String CHANNEL_ID = "Channel 1";
    private static final String TAG = "RegisterUser";
    private String userID;
    private String  encryptPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        Window window = RegisterUser.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(RegisterUser.this, R.color.colorPrimary));

        nameText        = (TextInputLayout) findViewById(R.id.inputNama);
        phoneNumber     = (TextInputLayout) findViewById(R.id.inputPhone);
        emailText       = (TextInputLayout) findViewById(R.id.inputEmail);
        passwordText    = (TextInputLayout) findViewById(R.id.inputPassword);
        btnSignUp       = (MaterialButton)findViewById(R.id.btnSignUp);
        btnSignIn       = (MaterialButton)findViewById(R.id.btnSignIn);
        result          = findViewById(R.id.passwordHash);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
//        if(firebaseAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(),LoginUser.class));
//            finish();
//        }

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getEditText().getText().toString().trim();
                String password = passwordText.getEditText().getText().toString().trim();
                String name = nameText.getEditText().getText().toString().trim();
                String phone = phoneNumber.getEditText().getText().toString().trim();


                if(TextUtils.isEmpty(name)){
                    nameText.setError("Full name is empty");
                    return;
                }

                if(TextUtils.isEmpty(phone)){
                    phoneNumber.setError("Phone number is empty");
                    return;
                }

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

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            //send verification link

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegisterUser.this, "Verification Email Has Been Sent.", Toast.LENGTH_SHORT).show();
                                    userID = firebaseAuth.getCurrentUser().getUid();
                                    computeMD5Hash(passwordText.toString()); //encrypt password

                                    DocumentReference documentReference = firebaseFirestore.collection("users").document(userID); //bikin collection namanya users di firestore
                                    Map<String,Object> user = new HashMap<>();
                                    user.put("name", name);
                                    user.put("phone",phone);
                                    user.put("email",email);
                                    user.put("password",result.getText().toString().trim()); //masukin hasil encrypt ke atribut password
                                    user.put("profileImage",null);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>(){
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG,"onSuccess: Profile Created" + userID);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure: " + e.toString());
                                        }
                                    });
//                                    savePreferences();
                                    startActivity(new Intent(getApplicationContext(),VerificationActivity.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure : Email not sent" + e.getMessage());
                                }
                            });

                        }
                        else{
                            Toast.makeText(RegisterUser.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginUser.class));
                finish();
            }
        });

    }

//    private void savePreferences() {
//        nameText        = (TextInputLayout) findViewById(R.id.inputNama);
//        phoneNumber     = (TextInputLayout) findViewById(R.id.inputPhone);
//
//        SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString("fullName", nameText.getEditText().getText().toString());
//        editor.putString("phoneNumber", phoneNumber.getEditText().getText().toString());
//        editor.putString("email", emailText.getEditText().getText().toString());
//        editor.putString("password", passwordText.getEditText().getText().toString());
//        editor.apply();
//    }

    public void computeMD5Hash(String password)
    {
        try {
            // create md5 hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte[] messageDigest = digest.digest();

            StringBuffer MD5Hash = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
            {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() <2 )
                    h = "0"+h;
                MD5Hash.append(h);
            }
            System.out.println(MD5Hash);//ngecek MD5Hash bener ga
            result.setText(MD5Hash);// ngeset MD5hash ke textview result
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
    }
}