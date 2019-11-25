package com.lwy.bootws;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        PropertySourcesPlaceholderConfigurer.class
})
@SpringBootTest
class BootWsApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println("fdsf");
    }

}
