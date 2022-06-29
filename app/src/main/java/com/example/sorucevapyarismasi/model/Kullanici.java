package com.example.sorucevapyarismasi.model;

import java.io.Serializable;

public class Kullanici implements Serializable
{
    private int kullaniciId;
    private String ad, soyad, kullaniciAdi, email, sifre;
    private int skor;

    public Kullanici() { }

    public Kullanici(int kullaniciId, String ad, String soyad, String kullaniciAdi, String email, String sifre, int skor) {
        this.kullaniciId = kullaniciId;
        this.ad = ad;
        this.soyad = soyad;
        this.kullaniciAdi = kullaniciAdi;
        this.email = email;
        this.sifre = sifre;
        this.skor = skor;
    }

    public int getKullaniciId() { return kullaniciId; }
    public void setKullaniciId(int kullaniciId) { this.kullaniciId = kullaniciId; }

    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }

    public String getSoyad() { return soyad; }
    public void setSoyad(String soyad) { this.soyad = soyad; }

    public String getKullaniciAdi() { return kullaniciAdi; }
    public void setKullaniciAdi(String kullaniciAdi) { this.kullaniciAdi = kullaniciAdi; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSifre() { return sifre; }
    public void setSifre(String sifre) { this.sifre = sifre; }

    public int getSkor() { return skor; }
    public void setSkor(int skor) { this.skor = skor; }

    @Override
    public String toString() { return kullaniciAdi; }
}
