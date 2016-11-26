package com.dev9.ensor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertTrue;


@WebAppConfiguration
@SpringBootTest(classes = ProtobufSerializationApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ProtobufSerializationApplicationTests {

    @Test
    public void contextLoads() {
        assertTrue(true);
    }

}
