package com.example.demo;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.Loader;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.net.URL;



@SpringBootApplication
public class DemoApplication {
    private static ResourceLoader resourceLoader = null;
    static Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) throws  IOException{

        SpringApplication.run(DemoApplication.class, args);
        resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:CustomLogback.xml");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String line;
            System.out.println(reader.readLine());
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final File tempFile;
        try {
            tempFile = File.createTempFile("CustomLogback", ".xml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(resource.getInputStream(),out);
        }
        JoranConfigurator configurator = new JoranConfigurator();
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        configurator.setContext(context);
        context.reset();
        try {
            configurator.doConfigure(tempFile);
        } catch (JoranException e) {
            throw new RuntimeException(e);
        }
        logger.info("***************");

        if(false)
            System.out.println(false);
        if(true)
            System.out.println(true);
    }

}





