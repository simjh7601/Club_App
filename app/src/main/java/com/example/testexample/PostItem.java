package com.example.testexample;


import java.util.List;
// nodejs rest api에서 생성한 데이터에 키와벨류 가 있는데
// 안드로이드에서 그 서버에 연결에서 받을때 원하는 데이터를 가져오고 싶으면
// nodejs rest api에서 생성한 키값의 이름과 같아야 받아 올수 있다.

public class PostItem {
    private String name;
    private String city;
    private String apiKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
