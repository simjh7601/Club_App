package com.example.testexample;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyAPI{

    //@POST("/api/users/0PRPXS4-5EK42KS-G4AK0PY-8Y8R0CR/seoul")
    //Call<List<PostItem>>  post_posts(@Body String name);
    @POST("/api/users/test") // BODY 는 nodejs로 보내주는 값 , call 은 리턴 값
    Call<List<PostItem>> post_posts(@Body PostItem post);

    @PATCH("/posts/{pk}/")
    Call<PostItem> patch_posts(@Path("pk") int pk, @Body PostItem post);

    @DELETE("/api/users/0PRPXS4-5EK42KS-G4AK0PY-8Y8R0CR/{pk}")
    Call<PostItem> delete_posts(@Path("pk") String pk);


    // 받을 타입을 만들기
    // 연결할 주소 인데 변하지 않는 주소 입력 seoul은 get할때 바뀌도 록 바꿔보자
    //@GET("/posts/")
    // @GET( EndPoint-자원위치(URI) )
    // Nodejs Rest Api에 있는 seoul 의 값 가져오기
    @GET("/api/users/0PRPXS4-5EK42KS-G4AK0PY-8Y8R0CR/seoul")
    Call<List<PostItem>> get_posts();
    // Nodejs Rest Api에 있는 GJ 의 값 가져오기
    @GET("/api/users/0PRPXS4-5EK42KS-G4AK0PY-8Y8R0CR/GJ")
    Call<List<PostItem>> get_posts_GJ();
    // 매개변수를 받는경우
    @GET("/api/users/0PRPXS4-5EK42KS-G4AK0PY-8Y8R0CR/{post}")
    Call<List<PostItem>> get_posts_GJ(@Path("post") String post);



//    @GET("/posts/{pk}/")
//    Call<PostItem> get_post_pk(@Path("pk") Map<String , String > querys);
}