package com.example.sorucevapyarismasi.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sorucevapyarismasi.R;
import com.example.sorucevapyarismasi.helper.DatabaseHelper;
import com.example.sorucevapyarismasi.model.Kategori;
import com.example.sorucevapyarismasi.model.Konu;

import java.io.IOException;
import java.util.ArrayList;

public class StartingScreenActivity extends AppCompatActivity
{
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    ArrayAdapter<Kategori> kategoriAdapter;
    ArrayAdapter<Konu> konuAdapter;

    ArrayList<Kategori> kategoriler = new ArrayList<>();
    ArrayList<Konu> konular = new ArrayList<>();

    TextView tvCurrentUser, tvHighScore;
    Spinner spKategori, spKonu;
    Button btnStartQuiz;

    String kullaniciAdi;
    int skor;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_screen);

        SharedPreferences preferences = getSharedPreferences("loginData", MODE_PRIVATE);
        int kullaniciId = preferences.getInt("userId", 0);

        tvCurrentUser = findViewById(R.id.tvCurrentUser);
        tvHighScore = findViewById(R.id.tvHighScore);
        spKategori = findViewById(R.id.spKategori);
        spKonu = findViewById(R.id.spKonu);
        btnStartQuiz = findViewById(R.id.btnStartQuiz);

        try {
            dbHelper = new DatabaseHelper(StartingScreenActivity.this);
            db = dbHelper.getWritableDatabase();

            kategoriler.add(new Kategori(0,"Kategori Seçiniz","seciniz"));

            Cursor c = db.rawQuery("SELECT * from Kategoriler",null);
            Cursor kullaniciCursor = db.rawQuery("SELECT * FROM Kullanicilar WHERE kullaniciId="+kullaniciId,null);

            while (c.moveToNext())
            {
                kategoriler.add(new Kategori(
                        c.getInt(c.getColumnIndex("kategoriId")),
                        c.getString(c.getColumnIndex("kategoriAdi")),
                        c.getString(c.getColumnIndex("kategoriAciklamasi"))
                ));
            }
            while (kullaniciCursor.moveToNext())
            {
                kullaniciAdi = kullaniciCursor.getString(kullaniciCursor.getColumnIndex("kullaniciadi"));
                skor = kullaniciCursor.getInt(kullaniciCursor.getColumnIndex("skor"));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        tvCurrentUser.setText("Kullanıcı: " + kullaniciAdi);
        tvHighScore.setText("En Yüksek Puan: " + skor);

        kategoriAdapter = new ArrayAdapter<>(
                StartingScreenActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                kategoriler);
        spKategori.setAdapter(kategoriAdapter);

        spKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                Cursor c2 = db.rawQuery("SELECT * from Konular where kategoriId = "+kategoriler.get(i).getKategoriId(),null);

                konular.clear();
                konular.add(new Konu(0,0,"Konu Seçiniz", "seciniz"));

                if (spKategori.getSelectedItem().toString().equals("Kategori Seçiniz"))
                {
                    konular.get(0).setKonuAdi("Önce Kategori Seçmelisiniz");
                }
                else konular.get(0).setKonuAdi("Konu Seçiniz");

                while (c2.moveToNext()){
                    konular.add(new Konu(
                            c2.getInt(c2.getColumnIndex("konuId")),
                            c2.getInt(c2.getColumnIndex("kategoriId")),
                            c2.getString(c2.getColumnIndex("konuAdi")),
                            c2.getString(c2.getColumnIndex("konuAciklamasi"))
                    ));
                }
                konuAdapter = new ArrayAdapter<>(
                        getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        konular
                );
                spKonu.setAdapter(konuAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { /*<...>*/ }
        });

        btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startQuiz();
            }
        });
    }

    private void startQuiz()
    {
        boolean konuflag = false;
        boolean bothflag = false;

        if(spKonu.getSelectedItem().toString().equals("Konu Seçiniz")) konuflag = false;
        else konuflag = true;

        if(spKonu.getSelectedItem().toString().equals("Önce Kategori Seçmelisiniz")) bothflag = false;
        else bothflag = true;

        if(konuflag == false)
        {
            Toast.makeText(
                    StartingScreenActivity.this,
                    "Konu Seçilmedi",
                    Toast.LENGTH_SHORT
            ).show();
        }
        else if (bothflag == false)
        {
            Toast.makeText(
                    StartingScreenActivity.this,
                    "Kategori ve Konu Seçilmedi",
                    Toast.LENGTH_SHORT
            ).show();
        }
        else
        {
            Intent intent = new Intent(StartingScreenActivity.this, QuizActivity.class);
            intent.putExtra("konu", konular.get(spKonu.getSelectedItemPosition()));
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_starting_screen,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if((item.getItemId()==R.id.editProfile_menu))
        {
            Intent intent = new Intent(StartingScreenActivity.this, UserProfileActivity.class);
            startActivity(intent);
            finish();
        }
        else if ((item.getItemId()==R.id.logOut_menu))
        {
            SharedPreferences pref = getSharedPreferences("loginData", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("remember", "false");
            editor.putInt("userId",-1);
            editor.apply();
            Intent intent = new Intent(StartingScreenActivity.this,LoginActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Oturum Sonlandırıldı", Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}