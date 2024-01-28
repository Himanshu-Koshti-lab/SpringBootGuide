package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@SpringBootApplication
public class DemoApplication {
    static Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);
        logger.info(Arrays.toString("ab".getBytes()));
        logger.info(Arrays.toString("b".getBytes()));
        logger.info(Arrays.toString("c".getBytes()));
        logger.info(Arrays.toString("d".getBytes()));
        logger.info(Arrays.toString("e".getBytes()));
        List<String> names = Arrays.asList("Alice", "Bob", "David", "Charlie");
        System.out.println(names);
        Collections.sort(names, String::compareTo);
        System.out.println(names);
    }
}





