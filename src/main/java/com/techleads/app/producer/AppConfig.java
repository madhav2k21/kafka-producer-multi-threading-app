package com.techleads.app.producer;

public interface AppConfig {
    String applicationID = "Multi-Threaded-Producer";
    String topicName = "nse-eod-topic";
    String kafkaConfigFileLocation = "kafka.properties";
    String[] eventFiles = {"data/NSE05NOV2018BHAV.csv", "data/NSE06NOV2018BHAV.csv"};
}
