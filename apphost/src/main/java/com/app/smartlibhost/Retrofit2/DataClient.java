package com.app.smartlibhost.Retrofit2;

import com.app.smartlibhost.model.Menu_main;
import com.app.smartlibhost.model.Sach;
import com.app.smartlibhost.model.Users;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface DataClient {
    @Multipart
    @POST("uploadanh.php")
    Call<String> uploadphoto(@Part MultipartBody.Part photo);



    @FormUrlEncoded
    @POST("insert_books.php")
    Call<String> InsertData(@Field("tensach") String tensach,
                            @Field("barcode") String barcode,
                            @Field("tentacgia") String tentacgia,
                            @Field("Mota") String Mota,
                            @Field("id_ngonngu")String id_ngonngu,
                            @Field("id_theloai") String id_theloai,
                            @Field("NXB") String NXB,
                            @Field("soluong") String soluong,
                            @Field("img_sach") String img_sach);

   @FormUrlEncoded
    @POST("insert_user.php")
    Call<String> InsertUser(@Field("id_fb") String id_fb,
                            @Field("name_user") String name_user,
                            @Field("email_user") String email_user,
                            @Field("fb_img") String fb_img);


    @GET("delete_book.php")
    Call<String> DeleteData(@Query("barcode") String barcode,
                            @Query("img_sach") String img_sach);

    @GET("getsachmoi.php")
    Call<List<Sach>> getString();

    @GET("get_users.php")
    Call<List<Users>> getUsers();

    @GET("getmenu.php")
    Call<List<Menu_main>> getMenuu();

    @Streaming
    @GET
    Call<ResponseBody> downloadfile(@Url String fileUrl);

}
