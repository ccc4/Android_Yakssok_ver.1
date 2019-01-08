package com.example.tje.yakssok;

import android.util.Log;
import android.webkit.CookieManager;

import com.example.tje.yakssok.model.Board;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

// generic 타입으로 들어온 값은
// 아래에서 Gson 을 이용한 객체화 과정에서 Type 을 받을 때 쓰인다.
// -> 제네릭 타입을 이용한 방법을 실패.. string 이 객체화가 안된다.. 내일 다시 테스트해보기
public class Server_Connect_Helper {

    private static final String LOG_TAG = "Yakssok";

    String about;
    HttpURLConnection myConnection;

    // 로그인 관련 쿠키 저장할 때 쓰이는 값
    String cookieString;

    // 0 - 생성자. 매개변수로 어떤 연결에 대한 것인지 String 값 주기
    public Server_Connect_Helper(String about) {
        this.about = about;
        Log.d(LOG_TAG, about + " - Server_Connect_Helper start");
    }

    // 1 -  연결할 주소 문자열을 넣어 myConnection 생성.
    public void connect(String address) {
        try {
            URL endPoint = new URL(address);
            myConnection = (HttpURLConnection)endPoint.openConnection();
            Log.d(LOG_TAG, about + " - connect : " + address);
        } catch (Exception e) {
            Log.d(LOG_TAG, about + " - connect : openConnect catch 발생");
            e.printStackTrace();
        }
    }

    // 1.1 - JSESSIONID 관련 메소드 - 1
    //       로그인처럼 쿠키를 저장해야 하는 경우
    //       매개변수로 ~~:8080//Yakssok 에 해당하는 주소값을 받아옴.
    public void setCookie_1(String server_address) {
        cookieString = CookieManager.getInstance().getCookie(server_address);
        if (cookieString != null) {
            myConnection.setRequestProperty("Cookie", cookieString);
            Log.d(LOG_TAG, about + " - setCookie_1 pass");
        }
    }

    // 1.2 - 연결방법 선택 (GET or POST). GET 일 경우 해당 메소드 안불려도 무방..
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

    // 1.3 - 전달할 값이 있는 경우.
    //       매개변수는 -> String.format("id=%s&pw=%s, id, pw")
    //       과 같은 형태로 전달된다.
    public void setValue(String requestParam) {
        try {
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

    // 1.4 - JSESSIONID 관련 메소드 - 2
    public void setCookie_2(String server_address) {
        Map<String, List<String>> headerFields = myConnection.getHeaderFields();
        String COOKIES_HEADER = "Set-Cookie";
        List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

        if (cookiesHeader != null) {
            for(String cookie : cookiesHeader) {
                String cookieName = HttpCookie.parse(cookie).get(0).getName();
                String cookieValue = HttpCookie.parse(cookie).get(0).getValue();

                cookieString = cookieName + "=" + cookieValue;
                CookieManager.getInstance().setCookie(server_address, cookieString);
            }
        }
        Log.d(LOG_TAG, about + " - setCookie_2 pass");
    }

    // 2 - 최종적으로 연결 및 값 전달 후 받는 값 리턴시키기
    //     매개변수로는 반납받을 타입지정 ( 밖에서 Type 값을 만들어 받아와야한다.)
    //     ex) Type resultType = new TypeToken<List<Board>>(){}.getType();
    //     해서 나온 type 변수.
    public Object getResult(Type resultType) {

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
                // 1)
                // 이렇게 받으려면 controller 에서도 당연히
                // Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                // 와 같이 만들어서 보내주어야 한다.
                // 2)
                // 날짜를 쓰지 않는 경우
                // Gson gson = new Gson();
                // 으로 보내줘도 괜찮은 것 확인!

                // Type 설정
                // Type ReturnType = new TypeToken<T>(){}.getType();
                // 을 밖에서 해준뒤에 매개변수로 전달


                // JSON 확인용
                // 필요에 따라 주석 풀어서 사용할 것.

//                Log.d(LOG_TAG, "test -" + buffer.toString());



                return gson.fromJson(buffer.toString(), resultType);      // 객체화 시켜서 리턴

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
