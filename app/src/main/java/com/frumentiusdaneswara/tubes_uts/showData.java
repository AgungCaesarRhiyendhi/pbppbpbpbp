package com.frumentiusdaneswara.tubes_uts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.frumentiusdaneswara.tubes_uts.databinding.ActivityShowDataBinding;
import com.google.android.material.appbar.MaterialToolbar;

public class showData extends AppCompatActivity {

    private TextView nama,alamat,nohp,harga;
    private Kos kos;
    private Button back,btnMaps,btnTransaksi;
    private ActivityShowDataBinding binding;
    private ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_show_data);
        Window window = showData.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(showData.this, R.color.colorPrimary));

        kos = (Kos) getIntent().getSerializableExtra("kos");
        binding.setKos(kos);



        MaterialToolbar materialToolbar = (MaterialToolbar) findViewById(R.id.profileToolbar);
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nanti disini balik ke home
                finish();
            }
        });


        btnMaps = findViewById(R.id.btn_Maps);
        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
                data.putSerializable("kos", kos);
                Intent intent = new Intent(view.getContext(),MapsActivity.class);
                intent.putExtras(data);
                view.getContext().startActivity(intent);
            }
        });

        btnTransaksi = findViewById(R.id.btn_transaksi);
        btnTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
                data.putSerializable("kos", kos);
                Intent intent = new Intent(showData.this,TransaksiActivity.class);
                intent.putExtras(data);
                view.getContext().startActivity(intent);

            }
        });




    }
}