package com.example.sorucevapyarismasi.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sorucevapyarismasi.R;
import com.example.sorucevapyarismasi.helper.DatabaseHelper;
import com.example.sorucevapyarismasi.model.Kullanici;

import java.io.IOException;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity
{
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    EditText etUserName, etPassword;
    Button btnLogin;
    TextView tvSignUp, tvTemizle;
    CheckBox cbRememberMe;

    ArrayList<Kullanici> kullanicilar = new ArrayList<>();

    int index = 0;

    public boolean validate(String kullaniciAdi, String sifre)
    {
        boolean flag = false;

        String sorgu = "select * from Kullanicilar where kullaniciadi='"+kullaniciAdi+"' and sifre='"+sifre+"'";
        Cursor c = db.rawQuery(sorgu,null);

        if(c.getCount()>0)
        {
            flag = true;

            int userId = kullanicilar.get(index).getKullaniciId();

            SharedPreferences pref = getSharedPreferences("loginData", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            editor.putInt("userId", userId);

            editor.apply();
        }
        return flag;
    }

    public int findIndexOfUserArray(String username, @NonNull ArrayList<Kullanici> kullanicilar)
    {
        int index = -1;

        for (int j = 0; j < kullanicilar.size(); j++)
        {
            if (kullanicilar.get(j).getKullaniciAdi().equals(username))
            {
                index = j;
            }
        }
        return index;
    }

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = findViewById(R.id.etUserNameLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        tvTemizle = findViewById(R.id.tvTemizle);

        try
        {
            dbHelper = new DatabaseHelper(LoginActivity.this);
            db = dbHelper.getWritableDatabase();

            Cursor cursor = db.rawQuery("select * from Kullanicilar", null);
            while (cursor.moveToNext())
            {
                kullanicilar.add(new Kullanici(
                        cursor.getInt(cursor.getColumnIndex("kullaniciId")),
                        cursor.getString(cursor.getColumnIndex("adi")),
                        cursor.getString(cursor.getColumnIndex("soyadi")),
                        cursor.getString(cursor.getColumnIndex("kullaniciadi")),
                        cursor.getString(cursor.getColumnIndex("email")),
                        cursor.getString(cursor.getColumnIndex("sifre")),
                        cursor.getInt(cursor.getColumnIndex("skor"))
                ));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        SharedPreferences preferences = getSharedPreferences("loginData", MODE_PRIVATE);
        String cb = preferences.getString("remember", "");

        if (cb.equals("true"))
        {
            Intent intent = new Intent(LoginActivity.this, StartingScreenActivity.class);
            startActivity(intent);
        }
        else if (cb.equals("false"))
        {
            Intent intent = getIntent();
            String info = intent.getStringExtra("activityInfo");

            if (info == null)
            {
                Toast.makeText(this,"Oturum Sonlandırıldı.", Toast.LENGTH_SHORT).show();
                cbRememberMe.setChecked(false);
            }
            else if (info.equals("SignUp"))
            {

            }

        }

        etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                index = findIndexOfUserArray(editable.toString(), kullanicilar);
            }
        });

        cbRememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked() && index != -1)
                {
                    SharedPreferences pref = getSharedPreferences("loginData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();

                    editor.putString("remember", "true");

                    editor.apply();
                }
                else if (!compoundButton.isChecked() && index != -1)
                {
                    SharedPreferences pref = getSharedPreferences("loginData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();

                    editor.putString("remember", "false");

                    editor.apply();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(validate(etUserName.getText().toString(),etPassword.getText().toString()))
                {
                    Intent intent = new Intent(LoginActivity.this, StartingScreenActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Kullanıcı Adı veya Şifre Hatalı!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        tvTemizle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUserName.getText().clear();
                etPassword.getText().clear();
            }
        });
    }
}