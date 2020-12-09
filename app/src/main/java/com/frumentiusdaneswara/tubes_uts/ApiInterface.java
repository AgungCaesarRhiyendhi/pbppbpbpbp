package com.frumentiusdaneswara.tubes_uts;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("transaksi")
    Call<TransaksiResponse> getAllTransaksi(@Query("data") String data);

    @GET("transaksi/{id}")
    Call<TransaksiResponse2> getTransaksiById(@Path("id") String id,
                                       @Query("data") String data);

    @POST("transaksi")
    @FormUrlEncoded
    Call<TransaksiResponse> createTransaksi(@Field("namapemesan") String namapemesan,
                                      @Field("nohppemesan") String nohppemesan,
                                      @Field("metodepembayaran") String metodepembayaran,
                                      @Field("hargakos") String hargakos);

    @POST("transaksiupdate/{id}")
    @FormUrlEncoded
    Call<TransaksiResponse> updateTransaksi(@Path("id") String id,
                                            @Field("namapemesan") String namapemesan,
                                            @Field("nohppemesan") String nohppemesan,
                                            @Field("metodepembayaran") String metodepembayaran,
                                            @Field("hargakos") String hargakos);

    @POST("transaksidelete/{id}")
    Call<TransaksiResponse> deleteTransaksi(@Path("id") String id);




    @GET("kos")
    Call<KosResponse> getAllKos(@Query("data") String data);

    @GET("kos/{id}")
    Call<KosResponse2> getKosById(@Path("id") String id,
                                 @Query("data") String data);

    @POST("kos")
    @FormUrlEncoded
    Call<KosResponse> createKos(@Field("namakos") String namakos,
                                @Field("alamatkos") String alamatkos,
                                @Field("hargakos") String hargakos,
                                @Field("nohpkos") String nohpkos,
                                @Field("imageID") String imageID,
                                @Field("latitude") double latitude,
                                @Field("longitude") double longitude);

    @POST("kosupdate/{id}")
    @FormUrlEncoded
    Call<KosResponse> updateKos(@Path("id") String id,
                                @Field("namakos") String namakos,
                                @Field("alamatkos") String alamatkos,
                                @Field("hargakos") String hargakos,
                                @Field("nohpkos") String nohpkos,
                                @Field("imageID") String imageID,
                                @Field("latitude") double latitude,
                                @Field("longitude") double longitude);

    @POST("kosdelete/{id}")
    Call<KosResponse> deleteKos(@Path("id") String id);




}
