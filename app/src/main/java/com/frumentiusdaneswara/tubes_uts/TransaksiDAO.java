package com.frumentiusdaneswara.tubes_uts;

import com.google.gson.annotations.SerializedName;

public class TransaksiDAO {

    @SerializedName("id")
    private String id;

    @SerializedName("namapemesan")
    public String namapemesan;

    @SerializedName("nohppemesan")
    public String nohppemesan;

    @SerializedName("metodepembayaran")
    public String metodepembayaran;

    @SerializedName("hargakos")
    public String hargakos;

    public TransaksiDAO(String namapemesan, String nohppemesan, String metodepembayaran, String hargakos) {
        this.namapemesan = namapemesan;
        this.nohppemesan = nohppemesan;
        this.metodepembayaran = metodepembayaran;
        this.hargakos = hargakos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamapemesan() {
        return namapemesan;
    }

    public void setNamapemesan(String namapemesan) {
        this.namapemesan = namapemesan;
    }

    public String getNohppemesan() {
        return nohppemesan;
    }

    public void setNohppemesan(String nohppemesan) {
        this.nohppemesan = nohppemesan;
    }

    public String getMetodepembayaran() {
        return metodepembayaran;
    }

    public void setMetodepembayaran(String metodepembayaran) {
        this.metodepembayaran = metodepembayaran;
    }

    public String getHargakos() {
        return hargakos;
    }

    public void setHargakos(String hargakos) {
        this.hargakos = hargakos;
    }
}
