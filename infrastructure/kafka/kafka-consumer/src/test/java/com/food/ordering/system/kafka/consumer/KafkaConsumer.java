package com.food.ordering.system.kafka.consumer;
import org.apache.avro.specific.SpecificRecordBase;

import java.util.List;

public interface KafkaConsumer <K extends SpecificRecordBase>{

    void receive(List<K> messages,List<Long> keys,List<Integer> partitions,List<Integer> offsets);
}
