package com.god.activemq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author goodtime
 * @create 2020-02-23 5:02 下午
 */


@SpringBootTest(classes = MainApp.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class producerTest {

    @Resource
    private  producer producer;

    @Test
    public void testSend() throws Exception{
        producer.produceMsg();
    }
}