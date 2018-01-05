package com.example.demo.component;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class WebUtils {

    public static String fetch(String url, String username, String password) throws Exception {

        OkHttpClient httpClient = createAuthenticatedClient(username, password);
        // execute request
        return doRequest(httpClient, url);

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