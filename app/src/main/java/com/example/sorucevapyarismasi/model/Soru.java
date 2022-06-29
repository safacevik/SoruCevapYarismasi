package com.example.sorucevapyarismasi.model;

import java.io.Serializable;

public class Soru implements Serializable
{
    private int soruId;
    private int konuId;
    private int puan;
    private String soruAdi;
    private String cevapA, cevapB, cevapC, cevapD;
    private int dogruCevap;
    private boolean yanitlandi;

    public Soru() { }

    public Soru(int soruId, int konuId, String soruAdi, String cevapA, String cevapB, String cevapC, String cevapD, int dogruCevap, int puan, boolean yanitlandi)
    {
        this.soruId = soruId;
        this.konuId = konuId;
        this.soruAdi = soruAdi;
        this.cevapA = cevapA;
        this.cevapB = cevapB;
        this.cevapC = cevapC;
        this.cevapD = cevapD;
        this.dogruCevap = dogruCevap;
        this.puan = puan;
        this.yanitlandi = yanitlandi;
    }

    public int getSoruId() {
        return soruId;
    }
    public void setSoruId(int soruId) {
        this.soruId = soruId;
    }

    public int getKonuId() {
        return konuId;
    }
    public void setKonuId(int konuId) {
        this.konuId = konuId;
    }

    public String getSoruAdi() {
        return soruAdi;
    }
    public void setSoruAdi(String soruAdi) {
        this.soruAdi = soruAdi;
    }

    public String getCevapA() {
        return cevapA;
    }
    public void setCevapA(String cevapA) {
        this.cevapA = cevapA;
    }

    public String getCevapB() {
        return cevapB;
    }
    public void setCevapB(String cevapB) {
        this.cevapB = cevapB;
    }

    public String getCevapC() {
        return cevapC;
    }
    public void setCevapC(String cevapC) {
        this.cevapC = cevapC;
    }

    public String getCevapD() {
        return cevapD;
    }
    public void setCevapD(String cevapD) {
        this.cevapD = cevapD;
    }

    public int getDogruCevap() {
        return dogruCevap;
    }
    public void setDogruCevap(int dogruCevap) {
        this.dogruCevap = dogruCevap;
    }

    public int getPuan() { return puan; }
    public void setPuan(int puan) { this.puan = puan; }

    public boolean isYanitlandi() { return yanitlandi; }
    public void setYanitlandi(boolean yanitlandi) { this.yanitlandi = yanitlandi; }
}
