package com.frumentiusdaneswara.tubes_uts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateKosActivity extends AppCompatActivity {

    private ImageButton ibBack;
    private EditText etNamak, etAlamatk, etHargak, etNohpk, etImageIDk, etLongitudek, etLatitudek;
    double Longitude,Latitude;
    private MaterialButton btnCancel, btnCreate;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_kos);

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
        btnCreate = findViewById(R.id.btnCreate);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Longitude = Double.parseDouble(etLongitudek.getText().toString());
                Latitude = Double.parseDouble(etLatitudek.getText().toString());

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
                    etAlamatk.setError("Isikan dengan benar");
                    etAlamatk.requestFocus();
                }
                else if(etLongitudek.getText().toString().isEmpty())
                {
                    etLongitudek.setError("Isikan dengan benar");
                    etLongitudek.requestFocus();
                }
                else if(etLatitudek.getText().toString().isEmpty())
                {
                    etLatitudek.setError("Isikan dengan benar");
                    etLatitudek.requestFocus();
                }
                else
                {
                    progressDialog.show();
                    saveUser();
                }
            }
        });
    }

    private void saveUser(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<KosResponse> add = apiService.createKos(etNamak.getText().toString(), etAlamatk.getText().toString(),etHargak.getText().toString(),etNohpk.getText().toString(),etImageIDk.getText().toString(),Latitude,Longitude);

        add.enqueue(new Callback<KosResponse>() {
            @Override
            public void onResponse(Call<KosResponse> call, Response<KosResponse> response) {
                Toast.makeText(CreateKosActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Intent intent = new Intent(CreateKosActivity.this,KosAdminActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<KosResponse> call, Throwable t) {
                Toast.makeText(CreateKosActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Intent intent = new Intent(CreateKosActivity.this,KosAdminActivity.class);
                startActivity(intent);
            }
        });
    }
}