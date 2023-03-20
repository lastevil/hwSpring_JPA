package com.hw.orders.configurations;

import com.hw.orders.dto.CartDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

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
    public Map<String, Object> consumerConfig() {
        Map<String, Object> produce = new HashMap<>();
        produce.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        produce.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        produce.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        produce.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        produce.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return produce;
    }
    @Bean
    public ConsumerFactory<Long, CartDto> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }
    @Bean
    public KafkaListenerContainerFactory<?> userKafkaContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<Long,CartDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
