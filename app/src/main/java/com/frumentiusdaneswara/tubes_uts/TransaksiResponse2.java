package com.frumentiusdaneswara.tubes_uts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransaksiResponse2 {

    @SerializedName("data")
    @Expose
    private TransaksiDAO transaksi = null;

    @SerializedName("message")
    @Expose
    private String message;

    public TransaksiDAO getUsers(){return transaksi;}

    public String getMessage(){return message;}

    public void setUsers(TransaksiDAO transaksis){this.transaksi = transaksis;}

    public void setMessage(String message){this.message = message;}
}
