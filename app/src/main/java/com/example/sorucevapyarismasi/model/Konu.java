package com.example.sorucevapyarismasi.model;

import java.io.Serializable;

public class Konu implements Serializable
{
    private int konuId;
    private int kategoriId;
    private String konuAdi;
    private String konuAciklamasi;

    public Konu() { }

    public Konu(int konuId, int kategoriId, String konuAdi, String konuAciklamasi)
    {
        this.konuId = konuId;
        this.kategoriId = kategoriId;
        this.konuAdi = konuAdi;
        this.konuAciklamasi = konuAciklamasi;
    }

    public int getKonuId() { return konuId; }
    public void setKonuId(int konuId) {
        this.konuId = konuId;
    }

    public int getKategoriId() {
        return kategoriId;
    }
    public void setKategoriId(int kategoriId) {
        this.kategoriId = kategoriId;
    }

    public String getKonuAdi() {
        return konuAdi;
    }
    public void setKonuAdi(String konuAdi) {
        this.konuAdi = konuAdi;
    }

    public String getKonuAciklamasi() {
        return konuAciklamasi;
    }
    public void setKonuAciklamasi(String konuAciklamasi) {
        this.konuAciklamasi = konuAciklamasi;
    }

    @Override
    public String toString() {
        return konuAdi;
    }
}
