package com.frumentiusdaneswara.tubes_uts;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailTransaksiFragment extends DialogFragment {

    private TextView twNamad, twNohpd, twmetoded, twhargad;
    private String sIdTransaksi, sNamad, sNohpd, smetoded, shargad;
    private ImageButton ibClose;
    private ProgressDialog progressDialog;
    public static DetailTransaksiFragment newInstance(){return new DetailTransaksiFragment();}
    private Button btn_delete,btn_update;

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_transaksi, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        ibClose = v.findViewById(R.id.ibClose);
        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        twNamad = v.findViewById(R.id.twNama);
        twNohpd = v.findViewById(R.id.twNohp);
        twmetoded = v.findViewById(R.id.twmetode);
        twhargad = v.findViewById(R.id.twharga);
        btn_delete = v.findViewById(R.id.btnDelete);
        btn_update = v.findViewById(R.id.btnUpdate);

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("Konfirmasi");
                alertDialogBuilder
                        .setMessage("Hapus data?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteTransaksiById(sIdTransaksi);
                                Intent intent1 = new Intent(getContext(), TransaksiAdminActivity.class);
                                startActivity(intent1);
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(view.getContext(), EditTransaksiActivity.class);
                intent2.putExtra("id_transaksi",sIdTransaksi);
                startActivity(intent2);
            }
        });


        sIdTransaksi = getArguments().getString("id","");
        loadUserById(sIdTransaksi);
        return v;
    }

    private void loadUserById(String id){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TransaksiResponse2> add = apiService.getTransaksiById(id, "data");

        add.enqueue(new Callback<TransaksiResponse2>() {
            @Override
            public void onResponse(Call<TransaksiResponse2> call, Response<TransaksiResponse2> response) {
                sNamad = response.body().getUsers().getNamapemesan();
                sNohpd = response.body().getUsers().getNohppemesan();
                smetoded = response.body().getUsers().getMetodepembayaran();
                shargad = response.body().getUsers().getHargakos();


                twNamad.setText(sNamad);
                twNohpd.setText(sNohpd);
                twmetoded.setText(smetoded);
                twhargad.setText(shargad);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<TransaksiResponse2> call, Throwable t) {
                Toast.makeText(getContext(), "kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void deleteTransaksiById(String id){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TransaksiResponse> add = apiService.deleteTransaksi(id);
        add.enqueue(new Callback<TransaksiResponse>() {
            @Override
            public void onResponse(Call<TransaksiResponse> call, Response<TransaksiResponse> response) {
                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<TransaksiResponse> call, Throwable t) {
                Toast.makeText(getContext(), "kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}