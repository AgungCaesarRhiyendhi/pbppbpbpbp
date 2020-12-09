package com.frumentiusdaneswara.tubes_uts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditProfileActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public static final int mode = Activity.MODE_PRIVATE;
    private String fullNamePref = "";
    private String phoneNumberPref = "";
    TextInputLayout nameText, phoneNumberText, emailText;

    MaterialButton btnSave,btnCancel;

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
                finish();
            }
        });

        nameText        = (TextInputLayout) findViewById(R.id.inputNama);
        phoneNumberText = (TextInputLayout) findViewById(R.id.inputPhone);
        emailText       = (TextInputLayout) findViewById(R.id.inputEmail);
        btnSave         = (MaterialButton) findViewById(R.id.btnSave);
        btnCancel       = (MaterialButton) findViewById(R.id.btnCancel);

        FirebaseUser user;

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            String email = user.getEmail();
            TextInputEditText inputEditTextEmail = findViewById(R.id.inputEditTextEmail);
            inputEditTextEmail.setText(email);
            loadPreferences();//load data dari shared preferences
        }
        else{
            TextInputEditText inputEditTextEmail = findViewById(R.id.inputEditTextEmail);
            inputEditTextEmail.setText("-");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences();
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