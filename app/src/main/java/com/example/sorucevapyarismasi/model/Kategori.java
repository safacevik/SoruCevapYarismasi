package com.example.sorucevapyarismasi.model;

import java.io.Serializable;

public class Kategori implements Serializable
{
    private int kategoriId;
    private String kategoriAdi;
    private String kategoriAciklamasi;

    public Kategori() { }

    public Kategori(int kategoriId, String kategoriAdi, String kategoriAciklamasi)
    {
        this.kategoriId = kategoriId;
        this.kategoriAdi = kategoriAdi;
        this.kategoriAciklamasi = kategoriAciklamasi;
    }

    public int getKategoriId() { return kategoriId; }
    public void setKategoriId(int kategoriId) { this.kategoriId = kategoriId; }

    public String getKategoriAdi() { return kategoriAdi; }
    public void setKategoriAdi(String kategoriAdi) { this.kategoriAdi = kategoriAdi; }

    public String getKategoriAciklamasi() { return kategoriAciklamasi; }
    public void setKategoriAciklamasi(String kategoriAciklamasi) { this.kategoriAciklamasi = kategoriAciklamasi; }

    @Override
    public String toString() {
        return kategoriAdi;
    }
}
