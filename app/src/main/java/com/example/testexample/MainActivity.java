package com.example.testexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final  String TAG = getClass().getSimpleName();

    // server의 url을 적어준다 localhost 로 적으면 안된다. 바뀌지 않는 부분
    private final String BASE_URL = "http://192.168.0.16:3001";
    private MyAPI mMyAPI;

    // 이벤트 후 보여주는 부분
    private TextView mListTv;
    
    // 버튼 생성
    private Button mGetButton;
    private Button mGetButton_GJ;
    private Button mPostButton;
    private Button mPatchButton;
    private Button mDeleteButton_seoul;
    private Button mDeleteButton_querys;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListTv = findViewById(R.id.result1);
        // get 버튼
        mGetButton = findViewById(R.id.button1); // nodejs rest api 에 있는 seoul 값 보기위한 버튼
        mGetButton.setOnClickListener(this);
        mGetButton_GJ = findViewById(R.id.button_GJ); // GJ 값 보기위한 버튼
        mGetButton_GJ.setOnClickListener(this);

        // post 버튼
        mPostButton = findViewById(R.id.button2);
        mPostButton.setOnClickListener(this);
        // patch 버튼
        mPatchButton = findViewById(R.id.button3);
        mPatchButton.setOnClickListener(this);
        // delete 버튼
        mDeleteButton_seoul = findViewById(R.id.button4_seoul);
        mDeleteButton_seoul.setOnClickListener(this);
        mDeleteButton_querys = findViewById(R.id.button4_test_querys);
        mDeleteButton_querys.setOnClickListener(this);

        // 접속할 URL 주소 를 보내준다.
        initMyAPI(BASE_URL);
    }
    private void initMyAPI(String baseUrl){

        Log.d(TAG,"initMyAPI : " + baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                 // JSON을 변환해줄 Gson 변환기 등록
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mMyAPI = retrofit.create(MyAPI.class);
    }

    // GET 방식
    @Override
    public void onClick(View v) {
        if( v == mGetButton | v == mGetButton_GJ){
            Log.d(TAG,"GET");
            
            // 다중 쿼리 시 사용 할 때
//            Map<String, String> querys = new HashMap<>();
//            querys.put("userId", "10");
//            querys.put("id", "96");

            String GJ = "GJ";
            String test_querys = "test_querys";
            String querys = "data1";

            // 버튼 두개 생성  누른 버튼 값 가져오기 위함
            Call<List<PostItem>> getCall;
            if(v == mGetButton){
                getCall = mMyAPI.get_posts();
            }else{
                getCall = mMyAPI.get_posts_GJ(GJ);
            }
            // array타입으로 받기위해 call을사용해 array타입으로 받기
            // cllback 를 해서 불러오기
            getCall.enqueue(new Callback<List<PostItem>>() {
                @Override
                public void onResponse(Call<List<PostItem>> call, Response<List<PostItem>> response) {
                    //Log.d(TAG, response.body().toString());
                    if( response.isSuccessful()){
                        List<PostItem> mList = response.body();

                        String result ="";

                        for( PostItem item : mList){
                            result += "title : " + item.getName() + " text: " + item.getCity() + "\n";
                        }
                        mListTv.setText(result);
                    }else {
                        Log.d(TAG,"Status Code : " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<PostItem>> call, Throwable t) {
                    Log.d(TAG,"Fail msg : " + t.getMessage());
                }
            });

        }
        // POST 방식
        else if(v == mPostButton){
            Log.d(TAG,"POST");

            PostItem item = new PostItem();
            // nodejs 에 보내주고 싶은 정보 ( 비교 및 입력 )
            item.setName("hyun");
            item.setCity("gwj");
            item.setApiKey("0PRPXS4-5EK42KS-G4AK0PY-8Y8R0CR");

            Call<List<PostItem>> postCall = mMyAPI.post_posts(item);

            postCall.enqueue(new Callback<List<PostItem>>() {
                @Override
                public void onResponse(Call<List<PostItem>> call, Response<List<PostItem>> response) {
                    Log.d(TAG, response.toString());
                    if(response.isSuccessful()){
                        Log.d(TAG,"POST - 등록 완료");
//                        값 잘 가져온다. (값도 같이 넘어오는지 테스트용)
//                        List<PostItem> mList = response.body();
//
//                        String result ="";
//
//                        for( PostItem item : mList){
//                            result += "title : " + item.getName() + " text: " + item.getCity() + "\n";
//                        }
//                        Log.d(TAG, result);

                    }else {
                        Log.d(TAG, "잘?");
                        Log.d(TAG,"Status Code : " + response.code());
                        Log.d(TAG,response.errorBody().toString());
                        Log.d(TAG,call.request().body().toString());
                    }
                }
                @Override
                public void onFailure(Call<List<PostItem>> call, Throwable t) {
                    Log.d(TAG,"Fail msg : " + t.getMessage());

                }
            });

            // 리스트형태가 아닌 값 가져올때 사용
//            Call<PostItem> postCall = mMyAPI.post_posts(item);
//            postCall.enqueue(new Callback<PostItem>() {
//                @Override
//                public void onResponse(Call<PostItem> call, Response<PostItem> response) {
//                    Log.d(TAG, response.toString());
//                    if(response.isSuccessful()){
//                        Log.d(TAG,"등록 완료");
//
//                    }else {
//                        Log.d(TAG, "잘?");
//                        Log.d(TAG,"Status Code : " + response.code());
//                        Log.d(TAG,response.errorBody().toString());
//                        Log.d(TAG,call.request().body().toString());
//                    }
//                };
//
//                @Override
//                public void onFailure(Call<PostItem> call, Throwable t) {
//
//                    Log.d(TAG,"Fail msg : " + t.getMessage());
//                }
//            });

        }
        //patch
        else if( v == mPatchButton){
            Log.d(TAG,"PATCH");
            PostItem item = new PostItem();
            item.setName("android patch title");
            item.setCity("android patch text");
            //pk 값은 임의로 하드코딩하였지만 동적으로 setting 해서 사용가능
            Call<PostItem> patchCall = mMyAPI.patch_posts(1,item);
            patchCall.enqueue(new Callback<PostItem>() {
                @Override
                public void onResponse(Call<PostItem> call, Response<PostItem> response) {
                    if(response.isSuccessful()){
                        Log.d(TAG,"patch 성공");
                    }else{
                        Log.d(TAG,"Status Code : " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<PostItem> call, Throwable t) {
                    Log.d(TAG,"Fail msg : " + t.getMessage());
                }
            });


        }
        // 삭제하기
        else if( v == mDeleteButton_seoul || v == mDeleteButton_querys){
            Log.d(TAG,"DELETE");
            // pk 값은 임의로 변경가능
            String pk_01 = null;
            String seoul = "seoul";
            String test_querys = "GJ";
            if (v == mDeleteButton_seoul){
                pk_01 = seoul;
            }else{
                pk_01 = test_querys;
            }
            Call<PostItem> deleteCall = mMyAPI.delete_posts(pk_01);
            deleteCall.enqueue(new Callback<PostItem>() {
                @Override
                public void onResponse(Call<PostItem> call, Response<PostItem> response) {
                    if(response.isSuccessful()){
                        Log.d(TAG,"삭제 완료");
                    }else {
                        Log.d(TAG,"Status Code : " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<PostItem> call, Throwable t) {
                    Log.d(TAG,"Fail msg : " + t.getMessage());
                }
            });
        }
    }

    class MainAsyncTesk extends AsyncTask<String, Void, String> {

        String receiveMsg;

        @Override
        // doInBackground의 매개변수 값이 여러개일 경우를 위해 배열로
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL("http://192.168.0.16:3001/api/users/0PRPXS4-5EK42KS-G4AK0PY-8Y8R0CR/seoul");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET"); //전송방식
                conn.setDoOutput(false);      //데이터를 쓸 지 설정
                conn.setDoInput(true);        //데이터를 읽어올지 설정

                if (conn.getResponseCode() == conn.HTTP_OK) {
                    String str;
                    InputStream is = conn.getInputStream();
                    StringBuffer buffer = new StringBuffer();

                    BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                    while ((str = br.readLine()) != null) {
                        buffer.append(str);
                    }

                    receiveMsg = buffer.toString();

                } else {
                    //통신이 실패한 이유를 찍기위한 로그
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //서버에서 보낸 값을 리턴
            return receiveMsg;
        }
    }

}