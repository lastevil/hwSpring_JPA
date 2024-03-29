package com.hw.spring.cart.configurations;


import com.hw.spring.cart.dto.CartDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    String server;
    @Value("${spring.kafka.consumer.group-id}")
    String groupId;

    @Bean
    public Map<String, Object> producerConfig() {
        Map<String, Object> produce = new HashMap<>();
        produce.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        produce.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        produce.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return produce;
    }

    @Bean
    public ProducerFactory<Long, CartDto> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean(name = "KafkaTest")
    public KafkaTemplate<Long, CartDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
