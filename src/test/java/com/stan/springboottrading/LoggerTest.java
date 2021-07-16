package com.stan.springboottrading;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class LoggerTest {
//    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test1(){
        log.debug("debug.."); // debug's level is lower than info, there is no output on console
        log.info("info.."); //default level is Info
        log.error("error.."); //error's level is higher than inf
        //level 越高表示事态越严重.

        String name = "stan";
        String pwd = "134";
        log.info("name:{},pwd:{}",name,pwd);
    }
}
