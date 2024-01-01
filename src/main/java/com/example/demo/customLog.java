package com.example.demo;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class customLog {
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
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(resource.getInputStream(), out);
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
    }

}
