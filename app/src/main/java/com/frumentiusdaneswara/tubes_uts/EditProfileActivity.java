package com.frumentiusdaneswara.tubes_uts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public static final int mode = Activity.MODE_PRIVATE;
    private String fullNamePref = "";
    private String phoneNumberPref = "";
    TextInputLayout nameText, phoneNumberText, emailText;

    FirebaseFirestore firebaseFirestore;
    MaterialButton btnSave,btnCancel;
    String userID;
    private static final String TAG = "UpdateUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Window window = EditProfileActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(EditProfileActivity.this, R.color.colorPrimary));

        MaterialToolbar materialToolbar = (MaterialToolbar) findViewById(R.id.profileToolbar);
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
                finish();
            }
        });

        btnSave         = (MaterialButton) findViewById(R.id.btnSave);
        btnCancel       = (MaterialButton) findViewById(R.id.btnCancel);

        FirebaseUser user;
        firebaseFirestore = FirebaseFirestore.getInstance();


        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            TextInputEditText inputEditTextEmail = findViewById(R.id.inputEditTextEmail);
            TextInputEditText editTextName = findViewById(R.id.inputEditTextName);
            TextInputEditText editTextPhone = findViewById(R.id.inputEditTextPhone);

            System.out.println(user);
            userID = user.getUid();

            DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    inputEditTextEmail.setText(value.getString("email"));
                    editTextName.setText(value.getString("name"));
                    editTextPhone.setText(value.getString("phone"));
                }
            });

        }
        else{
            TextInputEditText inputEditTextEmail = findViewById(R.id.inputEditTextEmail);
            TextInputEditText editTextName = findViewById(R.id.inputEditTextName);
            TextInputEditText editTextPhone = findViewById(R.id.inputEditTextPhone);
            inputEditTextEmail.setText("-");
            editTextName.setText("-");
            editTextPhone.setText("-");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText inputEditTextEmail = findViewById(R.id.inputEditTextEmail);
                TextInputEditText editTextName = findViewById(R.id.inputEditTextName);
                TextInputEditText editTextPhone = findViewById(R.id.inputEditTextPhone);
                String email = inputEditTextEmail.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();

                DocumentReference documentReference = firebaseFirestore.collection("users").document(userID); //bikin collection namanya users di firestore
                Map<String,Object> user = new HashMap<>();
                user.put("name", name);
                user.put("phone",phone);
                user.put("email",email);

                documentReference.set(user, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>(){
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
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void savePreferences() {
        TextInputEditText editTextName = findViewById(R.id.inputEditTextName);
        TextInputEditText editTextPhone = findViewById(R.id.inputEditTextPhone);
        SharedPreferences.Editor editor = preferences.edit();
        if (!editTextName.getText().toString().isEmpty() && !editTextPhone.getText().toString().isEmpty()){
            editor.putString("fullName", editTextName.getText().toString());
            editor.putString("phoneNumber", editTextPhone.getText().toString());
            editor.apply();
            Toast.makeText(this, "Profile Saved", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Fill Correctly", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadPreferences() {
        preferences = getSharedPreferences("myKey", mode);
        TextInputLayout viewFullName    =  findViewById(R.id.inputFullName);
        TextInputLayout viewPhone       =  findViewById(R.id.inputPhone);
        if (preferences!=null){
            fullNamePref = preferences.getString("fullName","-");
            phoneNumberPref = preferences.getString("phoneNumber", "-");
            viewFullName.getEditText().setText(fullNamePref);
            viewPhone.getEditText().setText(phoneNumberPref);
        }

    }
}