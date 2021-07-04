package com.techleads.app.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MyDispatcherTest {
    private static final Logger logger = LoggerFactory.getLogger(MyDispatcherTest.class);

    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            InputStream inputStream = new FileInputStream(AppConfig.kafkaConfigFileLocation);
            props.load(inputStream);
            props.put(ProducerConfig.CLIENT_ID_CONFIG, AppConfig.applicationID);
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        KafkaProducer<Integer, String> producer = new KafkaProducer<>(props);
        Thread[] myDispatchers = new Thread[AppConfig.eventFiles.length];
        logger.info("Starting MyDispatcher Threads");
        for (int i = 0; i < AppConfig.eventFiles.length; i++) {
            myDispatchers[i] = new Thread(new MyDispatcher(AppConfig.eventFiles[i], AppConfig.topicName, producer));
            myDispatchers[i].start();

        }


        try {
            for (Thread t : myDispatchers) {
                t.join();
            }
        } catch (InterruptedException e) {
            logger.error("{}-> Main thread interrupted");
            e.printStackTrace();
        } finally {
            producer.close();
            logger.info("Finished MyDispatcher Test");
        }


    }
}
