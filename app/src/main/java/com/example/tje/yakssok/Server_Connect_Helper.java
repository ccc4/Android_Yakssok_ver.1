package com.example.tje.yakssok;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

// generic 타입으로 들어온 값은
// 아래에서 Gson 을 이용한 객체화 과정에서 Type 을 받을 때 쓰인다.
public class Server_Connect_Helper <T> {

    private static final String LOG_TAG = "Yakssok";

    Type returnType = new TypeToken<T>(){}.getType();

    String about;
    HttpURLConnection myConnection;

    // 0 - 생성자. 매개변수로 어떤 연결에 대한 것인지 String 값 주기
    public Server_Connect_Helper(String about) {
        this.about = about;
        Log.d(LOG_TAG, about + " 에 대한 Server_Connect_Helper start");
    }

    // 1 -  연결할 주소 문자열을 넣어 myConnection 생성.
    public void connect(String address) {
        try {
            URL endPoint = new URL(address);
            myConnection = (HttpURLConnection)endPoint.openConnection();
        } catch (Exception e) {
            Log.d(LOG_TAG, about + " - connect : openConnect catch 발생");
            e.printStackTrace();
        }
    }

    // 1.1 - 연결방법 선택 (GET or POST). GET 일 경우 해당 메소드 안불려도 무방..
    public void selectMethod(String method) {
        try {
            if(method == null || !(method.equals("GET") || method.equals("POST"))) {
                Log.d(LOG_TAG, about + " - selectMethod : 잘못된 method 값이 선택되었습니다.");
            }
            // 전달 방법 선택
            myConnection.setRequestMethod(method);
        } catch (Exception e) {
            Log.d(LOG_TAG, about + " - selectMethod : catch 발생");
            e.printStackTrace();
        }

    }

    // 1.2 - 전달할 값이 있는 경우.
    //       매개변수는 -> "id=%s&pw=%s, id, pw"
    //       와 같은 형태로 전달된다.
    public void setValue(String paramStr) {
        try {
            String requestParam = String.format(paramStr);
            // 서버에서 읽기모드
            myConnection.setDoOutput(true);
            // 서버로 쓰기
            myConnection.getOutputStream().write(requestParam.getBytes());

            Log.d(LOG_TAG, about + " - setValue : 서버로 requestParam 전달");
        } catch (Exception e) {
            Log.d(LOG_TAG, about + " - setValue : catch 발생");
            e.printStackTrace();
        }
    }

    // 2 - 최종적으로 연결 및 값 전달 후 받는 값 리턴시키기
    //     매개변수로는 반납받을 타입지정
    public Object getResult() {

        try {

            if(myConnection.getResponseCode() == 200) {
                Log.d(LOG_TAG, about + " - getResult : 200 성공");

                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(
                                        myConnection.getInputStream()));
                StringBuffer buffer = new StringBuffer();
                String temp = null;
                while((temp = in.readLine()) != null) {
                    buffer.append(temp);
                }

                // 받은 String 문자열을 Gson 을 이용해 객체화 시키기 위한 준비.
                // 객체의 멤버필드중 날짜정보가 있을 시 에러발생하는데 아래와 같이 생성해주면 피할 수 있다.
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

                // 타입 설정
                //Type ReturnType = new TypeToken<T>(){}.getType();

                return gson.fromJson(buffer.toString(), returnType);

            } else {
                Log.d(LOG_TAG, about + " - getResult : 서버연결 실패");
                Log.d(LOG_TAG, about + " - " + myConnection.getResponseCode() + " 코드 발생");
            }

        } catch (Exception e) {
            Log.d(LOG_TAG, about + " - getResult : catch 발생");
            e.printStackTrace();
        }
        return null;
    }
}
