package com.example.demo;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
@Profile("dev")
public class customLog {
    Logger logger = LoggerFactory.getLogger("custom log");
    @Bean
    private void setup() throws IOException {
        ResourceLoader resourceLoader;
        resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:CustomLogback.xml");

        final File tempFile;
        try {
            tempFile = File.createTempFile("CustomLogback", ".xml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        tempFile.deleteOnExit();
        System.out.println(tempFile.length());
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(resource.getInputStream(), out);
        }
        System.out.println(tempFile);

        JoranConfigurator configurator = new JoranConfigurator();
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        configurator.setContext(context);
        context.reset();
        try {
            configurator.doConfigure(tempFile);
        } catch (JoranException e) {
            throw new RuntimeException(e);
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String line;
//            System.out.println(reader.readLine());
            while ((line = reader.readLine()) != null) {
                logger.info(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
