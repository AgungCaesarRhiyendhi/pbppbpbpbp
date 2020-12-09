package com.frumentiusdaneswara.tubes_uts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.frumentiusdaneswara.tubes_uts.adapter.KosRecyclerViewAdapter;
import com.frumentiusdaneswara.tubes_uts.databinding.ActivitySearchBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;


import java.util.ArrayList;
import java.util.List;

public class searchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    private KosRecyclerViewAdapter mAdapter;
    private com.google.android.material.button.MaterialButton profile;
    private RecyclerView recyclerView;
    private com.google.android.material.textfield.TextInputEditText searchtext;
    private List<Kos> kosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        Window window = searchActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(searchActivity.this, R.color.colorWhite));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        createKos();

        mAdapter = new KosRecyclerViewAdapter(searchActivity.this, kosList);
        recyclerView = binding.employeeRv;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);


        profile = findViewById(R.id.profile_btn);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ProfileActivity.class);
                startActivity(intent);
            }
        });



        searchtext = findViewById(R.id.search_name);
        searchtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


        //Firebase CloudMessaging
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String CHANNEL_ID = "Channel 1";
            CharSequence name = "Channel 1";
            String description = "This is Channel 1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


        FirebaseMessaging.getInstance().subscribeToTopic("news")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String mag = "Successful";
                        if(!task.isSuccessful()){
                            mag = "failed";
                        }
                    }
                });

    }







    private void filter(String text){
        List<Kos> filteredList = new ArrayList<>();
        for (Kos item : kosList){
            if(item.getNamakos().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        mAdapter.filterList(filteredList);
    }

    public void createKos(){
        kosList = new ArrayList<>();
        kosList.add(new Kos("Babarsari", "Jalan Babarsari no 969", "Rp 1.000.000", "088-888-888-888", "https://images.bisnis-cdn.com/posts/2020/02/04/1197010/kos.jpg",-7.776532, 110.415286 ));
        kosList.add(new Kos("Kledokan", "Jalan Kledokan no 46", "Rp 560.000", "084-433-232-322", "https://1.bp.blogspot.com/-AFAOANCNNDQ/XUo3K4fxKrI/AAAAAAAADFM/3FsaVpqvRFw89Ruxu9PshIt8ZNBXVCQZQCK4BGAYYCw/s1600/kamar%2Bkos.jpg",-7.777520, 110.408298));
        kosList.add(new Kos("Seturan", "Jalan Seturan no 111", "Rp 234.000", "083-723-872-322","https://cdn-radar.jawapos.com/uploads/radarbali/news/2019/10/05/perbup-352019-berlaku-kamar-kos-di-badung-bakal-dikenakan-pajak-10_m_159308.jpg", -7.765931, 110.410639 ));
        kosList.add(new Kos("Tambak Bayan", "Jalan Tambak Bayan no 23", "Rp 210.000", "082-282-382-398","https://cdn0-production-images-kly.akamaized.net/c5KwmVO-IjsqwvK5_74nB0EJZq0=/640x360/smart/filters:quality(75):strip_icc():format(jpeg)/kly-media-production/medias/861635/original/074525100_1429960385-10.jpg", -7.780070, 110.416518 ));
        kosList.add(new Kos("Ringroad", "Jalan Ringroad no 55", "Rp 1.200.000", "081-773-332-211","https://www.radarcirebon.com/wp-content/uploads/2018/01/tempat-kos-ilustrasi.jpg", -7.762786, 110.416882 ));
        kosList.add(new Kos("Janti", "Jalan Janti no 83", "Rp 340.000", "089-937-373-73","https://djuragan.sgp1.digitaloceanspaces.com/djurkam/production/images/lodgings/5c5d5525db446.jpeg",-7.789932, 110.410172 ));
        kosList.add(new Kos("Mrican", "Jalan Mrican no 37", "Rp 333.000", "083-622-233-78", "https://propertijogja.co.id/joimg/posts_image/4339812-kos-kosan-dijual-di-jakal-km-13-894-1.jpg", -7.776552, 110.393073));
    }




}
