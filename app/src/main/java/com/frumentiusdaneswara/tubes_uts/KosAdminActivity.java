package com.frumentiusdaneswara.tubes_uts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KosAdminActivity extends AppCompatActivity {

    private ImageButton ibBack,ibCreate1;
    private RecyclerView recyclerView;
    private KosRecyclerAdapter recyclerAdapter;
    private List<Kos> user = new ArrayList<>();
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kos_admin);

        ibCreate1 = findViewById(R.id.ibCreate);
        ibCreate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KosAdminActivity.this,CreateKosActivity.class);
                startActivity(i);
            }
        });


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
        Call<KosResponse> call = apiService.getAllKos("data");

        call.enqueue(new Callback<KosResponse>() {
            @Override
            public void onResponse(Call<KosResponse> call, Response<KosResponse> response) {
                generateDataList(response.body().getUsers());
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<KosResponse> call, Throwable t) {
                Toast.makeText(KosAdminActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void generateDataList(List<Kos> customerList){
        recyclerView = findViewById(R.id.userRecyclerView);
        recyclerAdapter = new KosRecyclerAdapter(this,customerList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(KosAdminActivity.this);
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