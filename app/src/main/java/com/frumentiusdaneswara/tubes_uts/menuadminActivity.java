package com.frumentiusdaneswara.tubes_uts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class menuadminActivity extends AppCompatActivity {

    private ImageButton btntransaksi, btnKos, btnsignout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuadmin);

        btntransaksi = findViewById(R.id.btn_transaksi);
        btnKos = findViewById(R.id.btn_kos);
        btnsignout = findViewById(R.id.btn_signout);

        btntransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(menuadminActivity.this,TransaksiAdminActivity.class);
                startActivity(i);

            }
        });

        btnKos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(menuadminActivity.this,KosAdminActivity.class);
                startActivity(i1);
            }
        });

        btnsignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(menuadminActivity.this,LoginUser.class);
            }
        });


    }
}