package com.example.demo.component;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class HttpService {

    private final OkHttpClient okHttpClient = new OkHttpClient();
//    timeout처리
//    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                                                .connectTimeout(30L,TimeUnit.SECONDS)
//                                                .writeTimeout(30L, TimeUnit.SECONDS)
//                                                .readTimeout(30L, TimeUnit.SECONDS)
//                                                .build();

    public String requestGet(String url) throws Exception {
        Request request = new Request.Builder()
//                .header("Authorization", Credentials.basic("", ""))
                .url(url)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    public String requestPostByString(String url, String bodyContent) throws Exception {
        final MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, bodyContent);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    public String requestPostByJson(String url, String bodyContent) throws Exception {
        final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, bodyContent);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

}
