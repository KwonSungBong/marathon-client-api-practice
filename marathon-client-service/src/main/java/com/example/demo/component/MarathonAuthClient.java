package com.example.demo.component;

import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.ErrorDecoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import lombok.extern.slf4j.Slf4j;
import mesosphere.marathon.client.Marathon;
import mesosphere.marathon.client.utils.MarathonException;
import mesosphere.marathon.client.utils.ModelUtils;

@Slf4j
public class MarathonAuthClient {

    static class MarathonHeadersInterceptor implements RequestInterceptor {
        @Override
        public void apply(RequestTemplate template) {
            template.header("Accept", "application/json");
            template.header("Content-Type", "application/json");
        }
    }

    static class MarathonErrorDecoder implements ErrorDecoder {
        @Override
        public Exception decode(String methodKey, Response response) {
            return new MarathonException(response.status(), response.reason());
        }
    }

    public static Marathon getInstance(String endpoint, String userName, String password) {
        GsonDecoder decoder = new GsonDecoder(ModelUtils.GSON);
        GsonEncoder encoder = new GsonEncoder(ModelUtils.GSON);
        return Feign.builder().encoder(encoder).decoder(decoder)
                .errorDecoder(new MarathonAuthClient.MarathonErrorDecoder())
                .requestInterceptor(new MarathonAuthClient.MarathonHeadersInterceptor())
                .requestInterceptor(new BasicAuthRequestInterceptor(userName, password))
                .target(Marathon.class, endpoint);
    }
}
