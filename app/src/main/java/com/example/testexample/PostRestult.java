package com.example.testexample;

import com.google.gson.annotations.SerializedName;

// DTO 모델 - PostResult class선업
public class PostRestult {
    //    JSON 데이터의 속성명과 변수명 + 타입(ex String,Int,Boolean) 일치 필수
    //      : JSON - @SerializedName("속성명")으로 속성명 일치시켜주면 변수명 다르게도 가능
    //
    //    XML - @Element(name="속성명") XML은 @Element 애노테이션 사용


    @SerializedName("name")
    private String name;
    // @SerializedName으로 일치시켜 주지않을 경우엔 클래스 변수명이 일치해야함

    // @SerializedName()로 변수명을 입치시켜주면 클래스 변수명이 달라도 알아서 매핑시켜줌

    @SerializedName("city")
    private String city;

    // toString()을 Override 해주지 않으면 객체 주소값을 출력함
    @Override
    public String toString(){
        return "PostResult{" +
                "name" + name +
                "city" + city +
                "}";
    }
}
