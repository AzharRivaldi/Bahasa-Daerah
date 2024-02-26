package com.azhar.kumpulanbahasa.model;

import java.io.Serializable;

/*
 * Created by Azhar Rivaldi on 09-01-2024
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */

public class ModelBahasa implements Serializable {

    String strNomor;
    String strBahasa;
    String strWilayah;
    String strId;
    String strProvinsi;
    String strDeskripsi;

    public String getStrNomor() {
        return strNomor;
    }

    public void setStrNomor(String strNomor) {
        this.strNomor = strNomor;
    }

    public String getStrBahasa() {
        return strBahasa;
    }

    public void setStrBahasa(String strBahasa) {
        this.strBahasa = strBahasa;
    }

    public String getStrWilayah() {
        return strWilayah;
    }

    public void setStrWilayah(String strWilayah) {
        this.strWilayah = strWilayah;
    }

    public String getStrId() {
        return strId;
    }

    public void setStrId(String strId) {
        this.strId = strId;
    }

    public String getStrProvinsi() {
        return strProvinsi;
    }

    public void setStrProvinsi(String strProvinsi) {
        this.strProvinsi = strProvinsi;
    }

    public String getStrDeskripsi() {
        return strDeskripsi;
    }

    public void setStrDeskripsi(String strDeskripsi) {
        this.strDeskripsi = strDeskripsi;
    }
}
