package com.eventhub.inventory_service.messaging;

import com.eventhub.inventory_service.messaging.dto.InventoryReserveRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    private final KafkaProperties kafkaProperties;

    public KafkaConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    // --- CONSUMER ---
    @Bean
    public ConsumerFactory<String, InventoryReserveRequest> consumerFactory() {
        // spring.kafka.consumer.* + bootstrap.servers dâhil tüm ayarları al
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties(null));
        // tip ve trusted paketleri belirle
        JsonDeserializer<InventoryReserveRequest> json = new JsonDeserializer<>(InventoryReserveRequest.class);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), json);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, InventoryReserveRequest> kafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, InventoryReserveRequest>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    // --- PRODUCER ---
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        // spring.kafka.producer.* + bootstrap.servers dâhil tüm ayarları al
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties(null));
        // serializerlar
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}