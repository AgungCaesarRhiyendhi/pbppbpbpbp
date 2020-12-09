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

public class DetailKosFragment extends DialogFragment {

    private TextView twNamak, twAlamatk, twHargak, twNohpk, twImageIDk, twLongitudek, twLatitudek;
    private String sIdKos, sNamak, sAlamatk, sHargak, sNohpk, sImageIDk ;
    private double sLongitudek, sLatitudek;
    private ImageButton ibClose;
    private ProgressDialog progressDialog;
    public static DetailKosFragment newInstance(){return new DetailKosFragment();}
    private Button btn_delete,btn_update1;

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_kos, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        ibClose = v.findViewById(R.id.ibClose);
        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        twNamak = v.findViewById(R.id.twNama);
        twAlamatk = v.findViewById(R.id.twAlamat);
        twHargak = v.findViewById(R.id.twHarga);
        twNohpk = v.findViewById(R.id.twNohp);
        twImageIDk = v.findViewById(R.id.twImageID);
        twLongitudek = v.findViewById(R.id.twLongitude);
        twLatitudek = v.findViewById(R.id.twLatitude);
        btn_delete = v.findViewById(R.id.btnDelete);
        btn_update1 = v.findViewById(R.id.btnUpdate1);

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
                                deleteKosById(sIdKos);
                                Intent intent1 = new Intent(getContext(), KosAdminActivity.class);
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

        btn_update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(view.getContext(), EditKosActivity.class);
                intent2.putExtra("id_kos",sIdKos);
                startActivity(intent2);
            }
        });


        sIdKos = getArguments().getString("id","");
        loadUserById(sIdKos);
        return v;
    }

    private void loadUserById(String id){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<KosResponse2> add = apiService.getKosById(id, "data");

        add.enqueue(new Callback<KosResponse2>() {
            @Override
            public void onResponse(Call<KosResponse2> call, Response<KosResponse2> response) {
                sNamak = response.body().getUsers().getNamakos();
                sAlamatk = response.body().getUsers().getAlamatkos();
                sHargak = response.body().getUsers().getHargakos();
                sNohpk = response.body().getUsers().getNohpkos();
                sImageIDk = response.body().getUsers().getImageID();
                sLongitudek = response.body().getUsers().getLongitude();
                sLatitudek = response.body().getUsers().getLatitude();

                String longitude = Double.toString(sLongitudek);
                String latitude = Double.toString(sLatitudek);

                twNamak.setText(sNamak);
                twAlamatk.setText(sAlamatk);
                twHargak.setText(sHargak);
                twNohpk.setText(sNohpk);
                twImageIDk.setText(sImageIDk);
                twLongitudek.setText(longitude);
                twLatitudek.setText(latitude);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<KosResponse2> call, Throwable t) {
//                Toast.makeText(getContext(), "kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void deleteKosById(String id){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<KosResponse> add = apiService.deleteKos(id);
        add.enqueue(new Callback<KosResponse>() {
            @Override
            public void onResponse(Call<KosResponse> call, Response<KosResponse> response) {
                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<KosResponse> call, Throwable t) {
//                Toast.makeText(getContext(), "kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}