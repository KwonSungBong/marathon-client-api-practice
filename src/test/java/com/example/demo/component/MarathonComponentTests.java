package com.example.demo.component;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by whilemouse on 18. 1. 2.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MarathonComponentTests {

    @Autowired
    MarathonComponent marathonComponent;

    @Test
    public void test() {
        log.info("RESULT : {}", marathonComponent.getUrl("test-service").toString());

        log.info("TEST");
    }

}
