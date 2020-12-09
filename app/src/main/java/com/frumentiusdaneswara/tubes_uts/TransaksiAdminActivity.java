package com.frumentiusdaneswara.tubes_uts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiAdminActivity extends AppCompatActivity {

    private ImageButton ibBack;
    private RecyclerView recyclerView;
    private TransaksiRecyclerAdapter recyclerAdapter;
    private List<TransaksiDAO> user = new ArrayList<>();
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_admin);

        ibBack = findViewById(R.id.ibBack);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        searchView = findViewById(R.id.searchUser);
        swipeRefresh = findViewById(R.id.swipeRefresh);

        swipeRefresh.setRefreshing(true);
        loadUser();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadUser();
            }
        });

    }

    public void loadUser(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TransaksiResponse> call = apiService.getAllTransaksi("data");

        call.enqueue(new Callback<TransaksiResponse>() {
            @Override
            public void onResponse(Call<TransaksiResponse> call, Response<TransaksiResponse> response) {
                generateDataList(response.body().getUsers());
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<TransaksiResponse> call, Throwable t) {
                Toast.makeText(TransaksiAdminActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void generateDataList(List<TransaksiDAO> transaksiList){
        recyclerView = findViewById(R.id.userRecyclerView);
        recyclerAdapter = new TransaksiRecyclerAdapter(this,transaksiList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TransaksiAdminActivity.this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                recyclerAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                recyclerAdapter.getFilter().filter(s);
                return false;
            }
        });
    }







}