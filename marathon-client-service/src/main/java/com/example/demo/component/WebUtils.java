package com.example.demo.component;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class WebUtils {

    public static String fetch(String url, String username, String password) throws Exception {

        OkHttpClient httpClient = createAuthenticatedClient(username, password);
        // execute request
        return doRequest(httpClient, url);
    }

    public static String post(String url) throws Exception {
//        String url = "http://test:secret@localhost:8899/get?param=test";
        Pattern r = Pattern.compile("^(?<protocol>.+?//)(?<username>.+?):(?<password>.+?)@(?<address>.+)$");
        Matcher m = r.matcher(url);
        final String username;
        final String password;

        if (m.find()) {
            log.info("all value: {}", m.group(0));
            log.info("protocol value: {}", m.group(1));
            log.info("username value: {}", m.group(2));
            log.info("password value: {}", m.group(3));
            log.info("address value: {}", m.group(4));

            username = m.group(2);
            password = m.group(3);
        } else {
            log.info("NO MATCH");
            return "";
        }

        final MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, "");

        Request request = new Request.Builder()
                .header("Authorization", Credentials.basic(username, password))
                .url(url)
                .post(body)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    private static OkHttpClient createAuthenticatedClient(final String username,
                                                          final String password) {
        // build client with authentication information.
        OkHttpClient httpClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
            public Request authenticate(Route route, Response response) throws IOException {
                String credential = Credentials.basic(username, password);
                if (responseCount(response) >= 3) {
                    return null;
                }
                return response.request().newBuilder().header("Authorization", credential).build();
            }
        }).build();
        return httpClient;
    }

    private static String doRequest(OkHttpClient httpClient, String anyURL) throws Exception {
        Request request = new Request.Builder().url(anyURL).build();
        Response response = httpClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        return response.body().string();
    }

    private static int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }

}