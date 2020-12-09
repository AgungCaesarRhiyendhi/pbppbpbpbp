package com.frumentiusdaneswara.tubes_uts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditKosActivity extends AppCompatActivity {


    private ImageButton ibBack;
    private EditText etNamak, etAlamatk, etHargak, etNohpk, etImageIDk, etLongitudek, etLatitudek;
    private MaterialButton btnCancel, btnUpdate;
    private ProgressDialog progressDialog;
    private String userId;
    private Call<KosResponse> add;
    private Kos kos;
    double Longitude, Latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kos);
        Intent intent = getIntent();
        userId = intent.getStringExtra("id_kos");

        progressDialog = new ProgressDialog(this);

        ibBack = findViewById(R.id.ibBack);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        etNamak = findViewById(R.id.etNamakos);
        etAlamatk = findViewById(R.id.etAlamat);
        etHargak = findViewById(R.id.etHarga);
        etNohpk = findViewById(R.id.etNohp);
        etImageIDk = findViewById(R.id.etImageID);
        etLatitudek = findViewById(R.id.etLatitude);
        etLongitudek = findViewById(R.id.etLongitude);
        btnCancel = findViewById(R.id.btnCancel);
        btnUpdate = findViewById(R.id.btnUPDATE);

        etImageIDk.setEnabled(false);
        etLatitudek.setEnabled(false);
        etLongitudek.setEnabled(false);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etNamak.getText().toString().isEmpty())
                {
                    etNamak.setError("Isikan dengan benar");
                    etNamak.requestFocus();
                }
                else if(etAlamatk.getText().toString().isEmpty())
                {
                    etAlamatk.setError("Isikan dengan benar");
                    etAlamatk.requestFocus();
                }
                else if(etHargak.getText().toString().isEmpty())
                {
                    etHargak.setError("Isikan dengan benar");
                    etHargak.requestFocus();
                }
                else if(etNohpk.getText().toString().isEmpty())
                {
                    etNohpk.setError("Isikan dengan benar");
                    etNohpk.requestFocus();
                }
                else if(etImageIDk.getText().toString().isEmpty())
                {
                    etImageIDk.setError("Isikan dengan benar");
                    etImageIDk.requestFocus();
                }
                else if(etLatitudek.getText().toString().isEmpty())
                {
                    etLatitudek.setError("Isikan dengan benar");
                    etLatitudek.requestFocus();
                }
                else if(etLongitudek.getText().toString().isEmpty())
                {
                    etLongitudek.setError("Isikan dengan benar");
                    etLongitudek.requestFocus();
                }
                else
                {
                    progressDialog.show();
                    update_user();
                    Intent i = new Intent(EditKosActivity.this,KosAdminActivity.class);
                    startActivity(i);
                }
            }
        });
        load_user();

    }

    private void load_user(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<KosResponse2> add = apiService.getKosById(userId, "data");
        add.enqueue(new Callback<KosResponse2>() {
            @Override
            public void onResponse(Call<KosResponse2> call, Response<KosResponse2> response) {


                etNamak.setText(response.body().getUsers().getNamakos());
                etAlamatk.setText(response.body().getUsers().getAlamatkos());
                etHargak.setText(response.body().getUsers().getHargakos());
                etNohpk.setText(response.body().getUsers().getNohpkos());
                etImageIDk.setText(response.body().getUsers().getImageID());

                Longitude = response.body().getUsers().getLongitude();
                Latitude = response.body().getUsers().getLatitude();

                String etlongitude = Double.toString(Longitude);
                String etlatitude = Double.toString(Latitude);
                etLatitudek.setText(etlatitude);
                etLongitudek.setText(etlongitude);



            }

            @Override
            public void onFailure(Call<KosResponse2> call, Throwable t) {
                Toast.makeText(EditKosActivity.this, "kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void update_user(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<KosResponse> add = apiService.updateKos(userId, etNamak.getText().toString(), etAlamatk.getText().toString(),etHargak.getText().toString(),etNohpk.getText().toString(),etImageIDk.getText().toString(),Latitude,Longitude);

        add.enqueue(new Callback<KosResponse>() {
            @Override
            public void onResponse(Call<KosResponse> call, Response<KosResponse> response) {
                Toast.makeText(EditKosActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<KosResponse> call, Throwable t) {
                Toast.makeText(EditKosActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}