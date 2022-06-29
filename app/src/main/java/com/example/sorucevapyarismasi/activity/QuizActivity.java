package com.example.sorucevapyarismasi.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sorucevapyarismasi.R;
import com.example.sorucevapyarismasi.helper.DatabaseHelper;
import com.example.sorucevapyarismasi.model.Konu;
import com.example.sorucevapyarismasi.model.Kullanici;
import com.example.sorucevapyarismasi.model.Soru;

import java.io.IOException;
import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity
{
    DatabaseHelper dbHelper;
    SQLiteDatabase database;

    ArrayList<Soru> sorular = new ArrayList<>();
    ArrayList<Integer> cevaplar = new ArrayList<>();

    TextView tvSoruNo;
    TextView tvSoruBaslik;
    TextView tvPuan;

    RadioGroup rgCevaplar;
    RadioButton rbCevap1, rbCevap2, rbCevap3, rbCevap4;

    ColorStateList Rb_txtColorDefault;

    Button btnOnayla;
    Button btnOnceki;
    Button btnSonraki;
    Button btnBitir;

    Kullanici kullanici;

    int currentIndex = 0;
    int maxIndex = 0;
    int puan = 0;

    @SuppressLint("Range")
    public Kullanici kullaniciyiGetir(int id)
    {
        int kullaniciId = 0;
        String ad = "", soyad = "", kullaniciadi = "", email = "", sifre = "";
        int skor = 0;

        Cursor c = database.rawQuery("select * from Kullanicilar where kullaniciId=" + id,null);

        while (c.moveToNext())
        {
            kullaniciId = c.getInt(c.getColumnIndex("kullaniciId"));
            ad = c.getString(c.getColumnIndex("adi"));
            soyad = c.getString(c.getColumnIndex("soyadi"));
            kullaniciadi = c.getString(c.getColumnIndex("kullaniciadi"));
            email = c.getString(c.getColumnIndex("email"));
            sifre = c.getString(c.getColumnIndex("sifre"));
            skor = c.getInt(c.getColumnIndex("skor"));
        }

        return new Kullanici(kullaniciId, ad, soyad, kullaniciadi, email, sifre, skor);
    }

    public void bringQuestions()
    {
        tvSoruBaslik.setText(sorular.get(currentIndex).getSoruAdi());

        rbCevap1.setText(sorular.get(currentIndex).getCevapA());
        rbCevap2.setText(sorular.get(currentIndex).getCevapB());
        rbCevap3.setText(sorular.get(currentIndex).getCevapC());
        rbCevap4.setText(sorular.get(currentIndex).getCevapD());

        rbCevap1.setTextColor(Rb_txtColorDefault);
        rbCevap2.setTextColor(Rb_txtColorDefault);
        rbCevap3.setTextColor(Rb_txtColorDefault);
        rbCevap4.setTextColor(Rb_txtColorDefault);

        rgCevaplar.clearCheck();

        tvSoruNo.setText(currentIndex + 1 + "/" + maxIndex);

        btnBitir.setVisibility(View.INVISIBLE);

        if (sorular.get(currentIndex).isYanitlandi() == true)
        {
            btnOnayla.setClickable(false);
            btnOnayla.setBackgroundColor(Color.GRAY);
            showSolution();
        }
        else
        {
            btnOnayla.setClickable(true);
            btnOnayla.setBackgroundColor(getResources().getColor(R.color.sinopia));
        }
        if (currentIndex<maxIndex-1)
        {
            btnSonraki.setClickable(true);
            btnSonraki.setBackgroundColor(getResources().getColor(R.color.sinopia));
        }
        else {
            btnSonraki.setClickable(false);
            btnSonraki.setBackgroundColor(Color.GRAY);
        }
        if (sorular.get(currentIndex).isYanitlandi()==false)
        {
            btnSonraki.setClickable(false);
            btnSonraki.setBackgroundColor(Color.GRAY);
        }
        else
        {
            if (currentIndex<maxIndex-1)
            {
                btnSonraki.setClickable(true);
                btnSonraki.setBackgroundColor(getResources().getColor(R.color.sinopia));
            }
        }
        if (currentIndex>0)
        {
            btnOnceki.setClickable(true);
            btnOnceki.setBackgroundColor(getResources().getColor(R.color.sinopia));
        }
        else
        {
            btnOnceki.setClickable(false);
            btnOnceki.setBackgroundColor(Color.GRAY);
        }

        if (sorular.get(currentIndex).isYanitlandi()==true)
        {
            rbCevap1.setClickable(false);
            rbCevap2.setClickable(false);
            rbCevap3.setClickable(false);
            rbCevap4.setClickable(false);
        }
        else
        {
            rbCevap1.setClickable(true);
            rbCevap2.setClickable(true);
            rbCevap3.setClickable(true);
            rbCevap4.setClickable(true);
        }

        for (int i = 0; i<sorular.size(); i++)
        {
            if (sorular.get(i).isYanitlandi()==true) btnBitir.setVisibility(View.VISIBLE);
            else btnBitir.setVisibility(View.INVISIBLE);
        }
    }

    private void checkAnswer()
    {
        RadioButton rgSecilen = findViewById(rgCevaplar.getCheckedRadioButtonId());
        int answerNr = rgCevaplar.indexOfChild(rgSecilen)+1;

        if (answerNr == sorular.get(currentIndex).getDogruCevap())
        {
            puan += sorular.get(currentIndex).getPuan();
            tvPuan.setText("Puan: " + puan);
            sorular.get(currentIndex).setYanitlandi(true);
        }
        if (sorular.get(currentIndex).isYanitlandi() == true)
        {
            btnOnayla.setClickable(false);
            btnOnayla.setBackgroundColor(Color.GRAY);
            showSolution();
        }
        else
        {
            btnOnayla.setClickable(true);
            btnOnayla.setBackgroundColor(getResources().getColor(R.color.sinopia));
        }
        if (sorular.get(currentIndex).isYanitlandi()==true)
        {
            rbCevap1.setClickable(false);
            rbCevap2.setClickable(false);
            rbCevap3.setClickable(false);
            rbCevap4.setClickable(false);
        }
        else
        {
            rbCevap1.setClickable(true);
            rbCevap2.setClickable(true);
            rbCevap3.setClickable(true);
            rbCevap4.setClickable(true);
        }
        if (sorular.get(currentIndex).isYanitlandi()==false)
        {
            btnSonraki.setClickable(false);
            btnSonraki.setBackgroundColor(Color.GRAY);
        }
        else
        {
            if (currentIndex<maxIndex-1)
            {
                btnSonraki.setClickable(true);
                btnSonraki.setBackgroundColor(getResources().getColor(R.color.sinopia));
            }
        }

        for (int i = 0; i<sorular.size(); i++)
        {
            if (sorular.get(i).isYanitlandi()==true) btnBitir.setVisibility(View.VISIBLE);
            else btnBitir.setVisibility(View.INVISIBLE);
        }
        showSolution();
    }

    private void showSolution()
    {
        rbCevap1.setTextColor(Color.RED);
        rbCevap2.setTextColor(Color.RED);
        rbCevap3.setTextColor(Color.RED);
        rbCevap4.setTextColor(Color.RED);

        switch(sorular.get(currentIndex).getDogruCevap())
        {
            case 1:
                rbCevap1.setTextColor(getResources().getColor(R.color.green));
                tvSoruBaslik.setText("Doğru Cevap: A" /* + sorular.get(currentIndex).getCevapA()*/);
                break;
            case 2:
                rbCevap2.setTextColor(getResources().getColor(R.color.green));
                tvSoruBaslik.setText("Doğru Cevap: B" /*+ sorular.get(currentIndex).getCevapB()*/);
                break;
            case 3:
                rbCevap3.setTextColor(getResources().getColor(R.color.green));
                tvSoruBaslik.setText("Doğru Cevap: C" /*+ sorular.get(currentIndex).getCevapC()*/);
                break;
            case 4:
                rbCevap4.setTextColor(getResources().getColor(R.color.green));
                tvSoruBaslik.setText("Doğru Cevap: D" /*+ sorular.get(currentIndex).getCevapD()*/);
                break;
        }

        if (cevaplar.get(currentIndex)==1)
        {
            rbCevap1.setChecked(true);
        }
        else if (cevaplar.get(currentIndex)==2)
        {
            rbCevap2.setChecked(true);
        }
        else if (cevaplar.get(currentIndex)==3)
        {
            rbCevap3.setChecked(true);
        }
        else if (cevaplar.get(currentIndex)==4)
        {
            rbCevap4.setChecked(true);
        }
    }

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Konu konu = (Konu)getIntent().getSerializableExtra("konu");
        this.setTitle("Konu: " + konu.getKonuAdi());

        tvSoruBaslik = (TextView) findViewById(R.id.tvSonuclarBaslik);
        tvSoruNo = (TextView) findViewById(R.id.tvSoruNo);
        tvPuan = (TextView) findViewById(R.id.tvPuan);

        rgCevaplar = (RadioGroup) findViewById(R.id.rgCevaplar);

        rbCevap1 = (RadioButton) findViewById(R.id.rbCevap1);
        rbCevap2 = (RadioButton) findViewById(R.id.rbCevap2);
        rbCevap3 = (RadioButton) findViewById(R.id.rbCevap3);
        rbCevap4 = (RadioButton) findViewById(R.id.rbCevap4);

        btnOnayla = (Button) findViewById(R.id.btnOnayla);
        btnOnceki = (Button) findViewById(R.id.btnOncekiSoru);
        btnSonraki = (Button) findViewById(R.id.btnSonrakiSoru);
        btnBitir = (Button) findViewById(R.id.btnBitir);

        Rb_txtColorDefault = rbCevap1.getTextColors();

        SharedPreferences preferences = getSharedPreferences("loginData", MODE_PRIVATE);
        int kullaniciId = preferences.getInt("userId", 0);

        try
        {
            dbHelper = new DatabaseHelper(QuizActivity.this);
            database = dbHelper.getWritableDatabase();
            kullanici = kullaniciyiGetir(kullaniciId);

            Cursor cursor = database.rawQuery("SELECT * FROM Sorular where konuId = " + konu.getKonuId(), null);

            while (cursor.moveToNext())
            {
                Soru soru = new Soru(
                        cursor.getInt(cursor.getColumnIndex("soruId")),
                        cursor.getInt(cursor.getColumnIndex("konuId")),
                        cursor.getString(cursor.getColumnIndex("soru")),
                        cursor.getString(cursor.getColumnIndex("cevapA")),
                        cursor.getString(cursor.getColumnIndex("cevapB")),
                        cursor.getString(cursor.getColumnIndex("cevapC")),
                        cursor.getString(cursor.getColumnIndex("cevapD")),
                        cursor.getInt(cursor.getColumnIndex("dogruCevap")),
                        cursor.getInt(cursor.getColumnIndex("puan")),
                        false);

                sorular.add(soru);
            }

            //Collections.shuffle(sorular);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        maxIndex = sorular.size(); //cursor.getCount();
        bringQuestions();

        btnOnayla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sorular.get(currentIndex).isYanitlandi()==false)
                {
                    if (rbCevap1.isChecked() || rbCevap2.isChecked() || rbCevap3.isChecked() || rbCevap4.isChecked())
                    {
                        RadioButton rgSecilen = findViewById(rgCevaplar.getCheckedRadioButtonId());
                        int answer = rgCevaplar.indexOfChild(rgSecilen)+1;
                        cevaplar.add(answer);
                        sorular.get(currentIndex).setYanitlandi(true);
                        checkAnswer();
                    }
                    else
                    {
                        Toast.makeText(QuizActivity.this, "Lütfen bir cevap seçiniz", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnSonraki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex++;
                bringQuestions();
            }
        });
        btnOnceki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentIndex>0) currentIndex--;
                bringQuestions();
            }
        });
        btnBitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kullanici.getSkor()<puan)
                {
                    ContentValues values = new ContentValues();
                    values.put("skor", puan);
                    database.update("Kullanicilar", values, "kullaniciId = ?", new String[]{String.valueOf(kullaniciId)});
                }

                Konu konu = (Konu)getIntent().getSerializableExtra("konu");
                Intent intent = new Intent(QuizActivity.this, SonucActivity.class);
                intent.putExtra("kullaniciCevaplari", cevaplar);
                intent.putExtra("konu", konu);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId()==android.R.id.home)
        {
            Intent intent = new Intent(QuizActivity.this, StartingScreenActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}