package com.frumentiusdaneswara.tubes_uts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KosResponse {

    @SerializedName("data")
    @Expose
    private List<Kos> users = null;

    @SerializedName("message")
    @Expose
    private String message;

    public List<Kos> getUsers(){return users;}

    public String getMessage(){return message;}

    public void setUsers(List<Kos> users){this.users = users;}

    public void setMessage(String message){this.message = message;}
}
