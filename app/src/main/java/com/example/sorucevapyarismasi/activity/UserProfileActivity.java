package com.example.sorucevapyarismasi.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sorucevapyarismasi.R;
import com.example.sorucevapyarismasi.helper.DatabaseHelper;
import com.example.sorucevapyarismasi.model.Kullanici;

import java.io.IOException;

public class UserProfileActivity extends AppCompatActivity {
    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    EditText etKullanici_ad, etKullanici_soyad, etKullaniciAdi ,etEmail, etSifre, etSifre_tekrar;

    Kullanici kullanici;

    int userId = 0;

    @SuppressLint("Range")
    public Kullanici kullaniciyiGetir(int id)
    {
        int kullaniciId = 0;
        String ad = "", soyad = "", kullaniciadi = "", email = "", sifre = "";
        int skor = 0;

        Cursor c = db.rawQuery("select * from Kullanicilar where kullaniciId=" + id,null);

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etKullanici_ad = findViewById(R.id.etAd_duzenle);
        etKullanici_soyad = findViewById(R.id.etSoyad_duzenle);
        etKullaniciAdi = findViewById(R.id.etKullaniciAdi_duzenle);
        etEmail = findViewById(R.id.etEmail_duzenle);
        etSifre = findViewById(R.id.etSifre_duzenle);
        etSifre_tekrar = findViewById(R.id.etSifreTekrar_duzenle);

        SharedPreferences preferences = getSharedPreferences("loginData", MODE_PRIVATE);
        userId = preferences.getInt("userId", 0);

        try
        {
            dbHelper = new DatabaseHelper(UserProfileActivity.this);
            db = dbHelper.getWritableDatabase();
            kullanici = kullaniciyiGetir(userId);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        this.setTitle("Kullan??c??y?? D??zenle: "+kullanici.getKullaniciAdi());

        etKullanici_ad.setText(kullanici.getAd());
        etKullanici_soyad.setText(kullanici.getSoyad());
        etKullaniciAdi.setText(kullanici.getKullaniciAdi());
        etEmail.setText(kullanici.getEmail());
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId()==android.R.id.home)
        {
            Intent intent = new Intent(UserProfileActivity.this, StartingScreenActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void performEditProfile(View view)
    {
        int id = kullanici.getKullaniciId();
        int result = editProfile(id);

        if (result == -1)
        {
            Toast.makeText(getApplicationContext(), "Bu Email Hesab??na Kay??tl?? Kullan??c?? Mevcut!", Toast.LENGTH_SHORT).show();
        }
        else if (result == -2)
        {
            Toast.makeText(getApplicationContext(), "Yeni ??ifre ??ncekiyle ayn?? olamaz!", Toast.LENGTH_SHORT).show();
        }
        else if (result == -3)
        {
            Toast.makeText(getApplicationContext(), etKullaniciAdi.getText().toString() + " ad??na kay??tl?? kullan??c?? mevcut!", Toast.LENGTH_SHORT).show();
        }
        else if (result == 1)
        {
            Toast.makeText(getApplicationContext(), "Bo?? Ge??ilen Alan Mevcut!", Toast.LENGTH_SHORT).show();
        }
        else if (result == 2)
        {
            Toast.makeText(getApplicationContext(), "Tekrar edilen ??ifre ayn?? de??il!", Toast.LENGTH_SHORT).show();
        }
        else if (result == 3)
        {
            Toast.makeText(getApplicationContext(), "Kaydedildi.", Toast.LENGTH_SHORT).show();
        }
        else if (result == 5)
        {
            Toast.makeText(getApplicationContext(), "Kaydedildi.", Toast.LENGTH_SHORT).show();
        }
        else if (result == 4 || result == 6)
        {
            Toast.makeText(getApplicationContext(), "Hata olu??tu!", Toast.LENGTH_SHORT).show();
        }
    }

    private int editProfile(int id)
    {
        String sorgu = "select * from Kullanicilar where email='"+etEmail.getText().toString()+"'";
        String sorgu2 = "select * from Kullanicilar where kullaniciadi='"+etKullaniciAdi.getText().toString()+"'";

        if (!etEmail.getText().toString().equals(kullanici.getEmail())) // kullan??c??n??n mevcut emailinden farkl??ysa ve;
        {
            Cursor c = db.rawQuery(sorgu, null);
            if (c.getCount()>0) // girilen email ba??ka bir kay??tl?? kullan??c??ya aitse
                return -1; // girilen emaile ba??ka bir kullan??c?? kay??tl?? bilgisi d??nd??r.
        }

        if (!etKullaniciAdi.getText().toString().equals(kullanici.getKullaniciAdi()))
        {
            Cursor c = db.rawQuery(sorgu2,null);
            if (c.getCount()>0) return -3; // girilen kullan??c?? ad?? ba??ka bir kullan??c??ya ait
        }

        if(etSifre.getText().toString().equals(etSifre_tekrar.getText().toString()))
        {
            if (kullanici.getSifre().equals(etSifre.getText().toString()))
            {
                return -2; // tekrar edilen ??ifre ayn?? fakat ??nceki ??ifreyle yeni ??ifre de ayn??
            }
        }

        if(etKullanici_ad.getText().toString().isEmpty() || etKullanici_soyad.getText().toString().isEmpty() || etKullaniciAdi.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty()) // ??ifre bo?? girilirse farkl?? bir kontrol var
        {
            return 1; //bo?? ge??ilen alan var
        }

        else if(!etSifre.getText().toString().equals(etSifre_tekrar.getText().toString()))
        {
            return 2; // tekrar edilen ??ifre ayn?? de??il
        }

        else if (etSifre == null && etSifre_tekrar == null || etSifre.getText().toString().equals("") && etSifre_tekrar.getText().toString().equals("")) // e??er ??ifre bo?? d??nerse ??ifreyi g??ncelleme
        {
            ContentValues values = new ContentValues();
            values.put("adi", etKullanici_ad.getText().toString());
            values.put("soyadi", etKullanici_soyad.getText().toString());
            values.put("kullaniciadi", etKullaniciAdi.getText().toString());
            values.put("email", etEmail.getText().toString());

            /*
            try {
                db.execSQL("INSERT INTO Kullanici (adi,soyadi,email) VALUES (" + adi + "," + soyadi + "," + email + ")");
            }catch (Exception e)
            {
                return 4;
            }
            return 3;
            */

            long l = db.update("Kullanicilar", values, "kullaniciId = ?", new String[]{String.valueOf(id)});
            if(l>0) {  return 3;  } // ??ifre de??i??tirilmedi ve g??ncelleme ba??ar??l??
            else return 4; //g??ncelleme ba??ar??s??z

        }
        else
        {
            ContentValues values = new ContentValues();
            values.put("adi", etKullanici_ad.getText().toString());
            values.put("soyadi", etKullanici_soyad.getText().toString());
            values.put("kullaniciadi", etKullaniciAdi.getText().toString());
            values.put("email", etEmail.getText().toString());
            values.put("sifre", etSifre.getText().toString());

            /*
            try {
                db.execSQL("INSERT INTO Kullanici (adi,soyadi,email,sifre) VALUES (" + adi + "," + soyadi + "," + email + "," + sifre + ")");
            }catch (Exception e)
            {
                return 6;
            }
            return 5;
            */

            long l = db.update("Kullanicilar", values, "kullaniciId = ?", new String[]{String.valueOf(id)});
            if(l>0) { return 5; }// g??ncelleme ba??ar??l??
            else { return 6; } //g??ncelleme ba??ar??s??z
        }
    }
}