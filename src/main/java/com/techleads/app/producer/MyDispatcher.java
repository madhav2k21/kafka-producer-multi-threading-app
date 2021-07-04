package com.techleads.app.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Scanner;

public class MyDispatcher implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(MyDispatcher.class);
    private String fileLocation;
    private String topicName;
    private KafkaProducer<Integer, String> producer;

    public MyDispatcher(String fileLocation, String topicName, KafkaProducer<Integer, String> producer) {
        this.fileLocation = fileLocation;
        this.topicName = topicName;
        this.producer = producer;
    }

    @Override
    public void run() {
        logger.info("Start processing " + fileLocation);
        File file = new File(fileLocation);
        int counter = 0;
        try (Scanner sc = new Scanner(file)) {

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                producer.send(new ProducerRecord<>(topicName, null, line));
                counter++;
            }
            logger.info("Finished sending " + counter + " message from " + fileLocation);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
