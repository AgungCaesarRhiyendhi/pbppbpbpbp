package com.frumentiusdaneswara.tubes_uts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KosResponse2 {

    @SerializedName("data")
    @Expose
    private Kos users = null;

    @SerializedName("message")
    @Expose
    private String message;

    public Kos getUsers(){return users;}

    public String getMessage(){return message;}

    public void setUsers(Kos users){this.users = users;}

    public void setMessage(String message){this.message = message;}
}
