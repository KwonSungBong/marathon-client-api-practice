package com.example.demo.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

import java.util.Map;

/**
 * Created by whilemouse on 18. 1. 2.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MarathonComponentTests {

    @Autowired
    MarathonComponent marathonComponent;

    @Autowired
    HttpService httpService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test1() {
        String restApiServiceUrl = marathonComponent.getUrl("rest-api-service");

        log.info("RESULT : {}", restApiServiceUrl);
    }

    @Test
    public void test2() throws Exception {
        String url = "http://localhost:8899";
        String result = httpService.requestGet(url);

        log.info("TEST");
    }

    @Test
    public void test3() throws Exception {
        String url = "http://localhost:8899/migration";

        String paramJson = "serviceName=test&reset=true";

        String result = httpService.requestPostByString(url, paramJson);

        log.info("TEST");
    }

    @Test
    public void test4() throws Exception {
        String url = "http://localhost:8899/migration";

        Map<String, Object> param = Maps.newHashMap();
        param.put("serviceName", "test");
        param.put("reset", true);
        String paramJson = mapper.writeValueAsString(param);

        String result = httpService.requestPostByJson(url, paramJson);

        log.info("TEST");
    }

    @Test
    public void test5() throws Exception {
        String url = "http://localhost:8899";
        String serviceName = "testService";
        boolean reset = true;
        requestMigration(url, serviceName, reset);
    }

    private interface MediaServiceApi {
        @GET("/get")
        String get();

        @FormUrlEncoded
        @POST(("/migration"))
        Response migration(@Field("serviceName") String serviceName,
                           @Field("resset") boolean resset);
    }

    public void requestMigration(String url, String serviceName, boolean resset) {
        Response response = getRestApi(url).migration(serviceName, resset);
        log.debug(response.getBody().toString());
        log.debug(response.getHeaders().toString());
        log.debug(response.getReason());
        log.debug("status : {}", response.getStatus());

        if (response.getStatus() == 200) {
        } else {
        }
    }

    private MediaServiceApi getRestApi(String url) {
        return new RestAdapter.Builder().setEndpoint(url).build().create(MediaServiceApi.class);
    }

    @Test
    public void test6() throws Exception {
        String url = "http://localhost:8899/migration/multiple";

        String paramString = "multipleServiceName=test1,test2,test3&reset=true";

        String result = httpService.requestPostByString(url, paramString);

        log.info("TEST");
    }

    @Test
    public void test7() {
        final String[] multipleServiceName = {"test1Service", "test2Service", "test3Service", "test4Service"};
        final String paramString = "multipleServiceName="+String.join(",", multipleServiceName)+"&reset=false";
        log.info("TEST {}", paramString);
    }

    @Test
    public void test8() throws Exception {
        String restApiServiceUrl = marathonComponent.getUrl("rest-api-service");
        String api = "/post";
        String url = restApiServiceUrl + api;

        final String paramString = "param=post";

        String result = httpService.requestPostByString(url, paramString);

        log.info("TEST {}", result);
    }

    @Test
    public void test9() throws Exception {
        String restApiServiceUrl = marathonComponent.getUrl("rest-api-service");

        log.info("TEST {}", restApiServiceUrl);
    }

}
