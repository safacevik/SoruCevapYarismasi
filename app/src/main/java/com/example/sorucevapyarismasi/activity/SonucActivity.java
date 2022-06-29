package com.example.sorucevapyarismasi.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sorucevapyarismasi.R;
import com.example.sorucevapyarismasi.helper.DatabaseHelper;
import com.example.sorucevapyarismasi.model.Konu;
import com.example.sorucevapyarismasi.model.Soru;

import java.io.IOException;
import java.util.ArrayList;

public class SonucActivity extends AppCompatActivity
{
    ArrayList<Integer> yanitlar = new ArrayList<>();
    ArrayList<String> yanitlar_harfli = new ArrayList<>();
    ArrayList<Integer> puanlar = new ArrayList<>();
    ArrayList<Soru> sorular = new ArrayList<>();
    ArrayList<Integer> soruNrs = new ArrayList<>();

    LinearLayout linearKullaniciCevabi,linearDogruCevap, linearPuan, linearSoruNo;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Konu konu;

    Button btnHome;

    @SuppressLint("Range")
    public void yanitlariGetir()
    {
        Cursor c = db.rawQuery("select * from Sorular where konuId="+konu.getKonuId(),null);

        while (c.moveToNext())
        {
            Soru soru =new Soru(
                    c.getInt(c.getColumnIndex("soruId")),
                    c.getInt(c.getColumnIndex("konuId")),
                    c.getString(c.getColumnIndex("soru")),
                    c.getString(c.getColumnIndex("cevapA")),
                    c.getString(c.getColumnIndex("cevapB")),
                    c.getString(c.getColumnIndex("cevapC")),
                    c.getString(c.getColumnIndex("cevapD")),
                    c.getInt(c.getColumnIndex("dogruCevap")),
                    c.getInt(c.getColumnIndex("puan")),
                    true
            );

            sorular.add(soru);
        }
    }

    public void verileriDoldur()
    {
        for (int i = 0; i < sorular.size(); i++)
        {
            soruNrs.add(i+1);

            if (yanitlar.get(i)==sorular.get(i).getDogruCevap())
            {
                puanlar.add(sorular.get(i).getPuan());
            }
            else
            {
                puanlar.add(0);
            }

            TextView tvSoruNo = new TextView(getApplicationContext());
            tvSoruNo.setTextSize(14f);
            tvSoruNo.setTextColor(getResources().getColor(R.color.chinese_silver));
            tvSoruNo.setText(Integer.toString(soruNrs.get(i)) + ". ");
            linearSoruNo.addView(tvSoruNo);

            TextView tvKullaniciCevabi = new TextView(getApplicationContext());
            tvKullaniciCevabi.setTextSize(14f);
            tvKullaniciCevabi.setTextColor(getResources().getColor(R.color.chinese_silver));
            tvKullaniciCevabi.setText("\t\t\t\t\t\t\t"+yanitlar_harfli.get(i));
            linearKullaniciCevabi.addView(tvKullaniciCevabi);

            String dogruCevapHarf="";
            if (sorular.get(i).getDogruCevap()==1)
                dogruCevapHarf="A";
            else if (sorular.get(i).getDogruCevap()==2)
                dogruCevapHarf="B";
            else if (sorular.get(i).getDogruCevap()==3)
                dogruCevapHarf="C";
            else
                dogruCevapHarf="D";

            TextView tvDogruCevap = new TextView(getApplicationContext());
            tvDogruCevap.setTextSize(14f);
            tvDogruCevap.setTextColor(getResources().getColor(R.color.chinese_silver));
            tvDogruCevap.setText("\t\t\t\t\t\t"+dogruCevapHarf);
            linearDogruCevap.addView(tvDogruCevap);

            TextView tvPuan = new TextView(getApplicationContext());
            tvPuan.setTextSize(14f);
            tvPuan.setTextColor(getResources().getColor(R.color.chinese_silver));
            tvPuan.setText("\t\t\t\t\t"+Integer.toString(puanlar.get(i)));
            linearPuan.addView(tvPuan);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonuc_v2);

        konu = (Konu)getIntent().getSerializableExtra("konu");
        yanitlar = (ArrayList<Integer>) getIntent().getSerializableExtra("kullaniciCevaplari");

        for (int i = 0; i < yanitlar.size(); i++)
        {
            if (yanitlar.get(i)==1)
                yanitlar_harfli.add("A");
            else if (yanitlar.get(i)==2)
                yanitlar_harfli.add("B");
            else if (yanitlar.get(i)==3)
                yanitlar_harfli.add("C");
            else
                yanitlar_harfli.add("D");
        }

        linearSoruNo = findViewById(R.id.linearSoruNo);
        linearKullaniciCevabi = findViewById(R.id.linearKullaniciCevabi);
        linearDogruCevap = findViewById(R.id.linearDogruCevap);
        linearPuan = findViewById(R.id.linearPuan);

        btnHome = (Button) findViewById(R.id.btnHome);

        try
        {
            dbHelper = new DatabaseHelper(getApplicationContext());
            db = dbHelper.getReadableDatabase();
            yanitlariGetir();
            verileriDoldur();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SonucActivity.this, StartingScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}