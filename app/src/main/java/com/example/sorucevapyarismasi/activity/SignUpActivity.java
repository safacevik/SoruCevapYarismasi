package com.example.sorucevapyarismasi.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sorucevapyarismasi.R;
import com.example.sorucevapyarismasi.helper.DatabaseHelper;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    EditText etFirstName_SignUp, etLastName_SignUp, etUserName_SignUp, etEmail_SignUp, etPassword_SignUp, etRepeatPassword_SignUp;
    Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try
        {
            dbHelper = new DatabaseHelper(getApplicationContext());
            db = dbHelper.getWritableDatabase();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        etFirstName_SignUp = findViewById(R.id.etFirstName_SignUp);
        etLastName_SignUp = findViewById(R.id.etLastName_SignUp);
        etUserName_SignUp = findViewById(R.id.etUserName_SignUp);
        etEmail_SignUp = findViewById(R.id.etEmail_SignUp);
        etPassword_SignUp = findViewById(R.id.etPassword_SignUp);
        etRepeatPassword_SignUp = findViewById(R.id.etRepeatPassword_SignUp);
        btRegister = findViewById(R.id.btRegister);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId()==android.R.id.home)
        {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("activityInfo", "SignUp");
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void performSignUp(View view)
    {
        String adi, soyadi, kullaniciadi, email, sifre;
        int puan;

        adi = etFirstName_SignUp.getText().toString();
        soyadi = etLastName_SignUp.getText().toString();
        kullaniciadi = etUserName_SignUp.getText().toString();
        email = etEmail_SignUp.getText().toString();
        sifre = etPassword_SignUp.getText().toString();
        puan = 0;

        int res = register(adi, soyadi, kullaniciadi, email,sifre, puan);

        if(res == 1)
        {
            Toast.makeText(getApplicationContext(), "Kayıt Başarılı.", Toast.LENGTH_SHORT).show();

            etFirstName_SignUp.getText().clear();
            etLastName_SignUp.getText().clear();
            etUserName_SignUp.getText().clear();
            etEmail_SignUp.getText().clear();
            etPassword_SignUp.getText().clear();
            etRepeatPassword_SignUp.getText().clear();
        }
        else if(res == 2)
        {
            Toast.makeText(getApplicationContext(), "Kayıt Başarısız!", Toast.LENGTH_SHORT).show();
        }
        else if(res == 3)
        {
            Toast.makeText(getApplicationContext(), "Boş Geçilen Alan Mevcut!", Toast.LENGTH_SHORT).show();
        }
        else if(res == 4)
        {
            Toast.makeText(getApplicationContext(), "Tekrar edilen şifre aynı değil!", Toast.LENGTH_SHORT).show();
        }
        else if(res == -1)
        {
            Toast.makeText(getApplicationContext(), "Aynı Kullanıcı Adına Sahip Bir Kullanıcı Mevcut!", Toast.LENGTH_SHORT).show();
        }
        else if(res == -2)
        {
            Toast.makeText(getApplicationContext(), "Bu Email Hesabına Kayıtlı Kullanıcı Mevcut!", Toast.LENGTH_SHORT).show();
        }
    }

    private int register(String adi, String soyadi, String kullaniciadi, String email, String sifre, int puan)
    {
        String sorgu1 = "select * from Kullanicilar where kullaniciadi='"+kullaniciadi+"'";
        String sorgu2 = "select * from Kullanicilar where email='"+email+"'";

        Cursor c = db.rawQuery(sorgu1,null);
        Cursor c1 = db.rawQuery(sorgu2,null);

        if(etFirstName_SignUp.getText().toString().isEmpty() || etLastName_SignUp.getText().toString().isEmpty()
         || etUserName_SignUp.getText().toString().isEmpty() || etEmail_SignUp.getText().toString().isEmpty()
         || etPassword_SignUp.getText().toString().isEmpty() || etRepeatPassword_SignUp.getText().toString().isEmpty())
        {
            return 3;
        }
        else if(!etPassword_SignUp.getText().toString().equals(etRepeatPassword_SignUp.getText().toString()))
        {
            return 4;
        }

        if(c.getCount()>0) return -1;

        if (c1.getCount()>0) return -2;

        else
        {
            ContentValues values = new ContentValues();
            values.put("adi", adi);
            values.put("soyadi", soyadi);
            values.put("kullaniciadi", kullaniciadi);
            values.put("email", email);
            values.put("sifre", sifre);
            values.put("skor", puan);

            long l = db.insert("Kullanicilar",null,values);
            db.close();
            if(l>0) { return 1; }
            else { return 2; }
        }
    }
}