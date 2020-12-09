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

public class EditTransaksiActivity extends AppCompatActivity {

    private ImageButton ibBack;
    private EditText etNama, etNohp, etHarga;
    private AutoCompleteTextView exposedDropdownmetode;
    private MaterialButton btnCancel, btnUpdate;
    private String smetode = "";
    private String[] sametode = new String[] {"Cash", "Credit"};
    private ProgressDialog progressDialog;
    private String userId;
    private Call<TransaksiResponse> add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaksi);
        Intent intent = getIntent();
        userId = intent.getStringExtra("id_transaksi");

        progressDialog = new ProgressDialog(this);

        ibBack = findViewById(R.id.ibBack);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        etNama = findViewById(R.id.etNama);
        etNohp = findViewById(R.id.etNohp);
        exposedDropdownmetode = findViewById(R.id.edmetode);
        etHarga = findViewById(R.id.etharga);
        btnCancel = findViewById(R.id.btnCancel);
        btnUpdate = findViewById(R.id.btnUPDATE);


        ArrayAdapter<String> adaptermetode = new ArrayAdapter<>(Objects.requireNonNull(this),
                R.layout.list_item, R.id.item_list, sametode);
        exposedDropdownmetode.setAdapter(adaptermetode);
        exposedDropdownmetode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                smetode = sametode[i];
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etNama.getText().toString().isEmpty())
                {
                    etNama.setError("Isikan dengan benar");
                    etNama.requestFocus();
                }
                else if(etNohp.getText().toString().isEmpty())
                {
                    etNohp.setError("Isikan dengan benar");
                    etNohp.requestFocus();
                }
                else if(smetode.isEmpty())
                {
                    exposedDropdownmetode.setError("Isikan dengan benar", null);
                    exposedDropdownmetode.requestFocus();
                }
                else if(etHarga.getText().toString().isEmpty())
                {
                    etHarga.setError("Isikan dengan benar");
                    etHarga.requestFocus();
                }
                else
                {
                    progressDialog.show();
                    update_user();
                    Intent i = new Intent(EditTransaksiActivity.this,TransaksiAdminActivity.class);
                    startActivity(i);
                }
            }
        });
        load_user();

    }

    private void load_user(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TransaksiResponse2> add = apiService.getTransaksiById(userId, "data");
        add.enqueue(new Callback<TransaksiResponse2>() {
            @Override
            public void onResponse(Call<TransaksiResponse2> call, Response<TransaksiResponse2> response) {
                etNama.setText(response.body().getUsers().getNamapemesan());
                etNohp.setText(response.body().getUsers().getNohppemesan());
                etHarga.setText(response.body().getUsers().getHargakos());
                smetode = response.body().getUsers().getMetodepembayaran();
                for(int i=0; i<sametode.length; i++)
                    if(sametode[i].equals(smetode))
                    {
                        exposedDropdownmetode.setText(exposedDropdownmetode.getAdapter().getItem(i).toString(), false);
                        break;
                    }
            }


            @Override
            public void onFailure(Call<TransaksiResponse2> call, Throwable t) {
                Toast.makeText(EditTransaksiActivity.this, "kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void update_user(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TransaksiResponse> add = apiService.updateTransaksi(userId, etNama.getText().toString(), etNohp.getText().toString(), smetode,etHarga.getText().toString());

        add.enqueue(new Callback<TransaksiResponse>() {
            @Override
            public void onResponse(Call<TransaksiResponse> call, Response<TransaksiResponse> response) {
                Toast.makeText(EditTransaksiActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<TransaksiResponse> call, Throwable t) {
                Toast.makeText(EditTransaksiActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }


}