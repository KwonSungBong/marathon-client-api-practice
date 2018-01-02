package com.example.demo.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        log.info("RESULT : {}", marathonComponent.getUrl("test-service").toString());

        log.info("TEST");
    }

    @Test
    public void test2() throws Exception {
        String url = "http://localhost:8888";
        String result = httpService.requestGet(url);

        log.info("TEST");
    }

    @Test
    public void test3() throws Exception {
        String url = "http://localhost:8888/migration";

        String paramJson = "serviceName=test&reset=true";

        String result = httpService.requestPostByString(url, paramJson);

        log.info("TEST");
    }

    @Test
    public void test4() throws Exception {
        String url = "http://localhost:8888/migration";

        Map<String, Object> param = Maps.newHashMap();
        param.put("serviceName", "test");
        param.put("reset", true);
        String paramJson = mapper.writeValueAsString(param);

        String result = httpService.requestPostByJson(url, paramJson);

        log.info("TEST");
    }


}
