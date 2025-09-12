package com.eventhub.order_service.messaging;

import com.eventhub.order_service.messaging.dto.InventoryReserveResult;
import com.eventhub.order_service.messaging.dto.PaymentProcessResult;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
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

    private final KafkaProperties props;
    public KafkaConfig(KafkaProperties props) { this.props = props; }

    // ---- Consumer: inventory.reserve.result ----
    @Bean
    public ConsumerFactory<String, InventoryReserveResult> inventoryResultConsumerFactory() {
        Map<String, Object> cfg = new HashMap<>(props.buildConsumerProperties(null));
        JsonDeserializer<InventoryReserveResult> json = new JsonDeserializer<>(InventoryReserveResult.class);
        json.addTrustedPackages("*");
        cfg.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        cfg.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(cfg, new StringDeserializer(), json);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, InventoryReserveResult> inventoryResultKafkaListenerContainerFactory() {
        var f = new ConcurrentKafkaListenerContainerFactory<String, InventoryReserveResult>();
        f.setConsumerFactory(inventoryResultConsumerFactory());
        return f;
    }

    // ---- Consumer: payment.process.result ----
    @Bean
    public ConsumerFactory<String, PaymentProcessResult> paymentResultConsumerFactory() {
        Map<String, Object> cfg = new HashMap<>(props.buildConsumerProperties(null));
        JsonDeserializer<PaymentProcessResult> json = new JsonDeserializer<>(PaymentProcessResult.class);
        json.addTrustedPackages("*");
        cfg.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        cfg.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(cfg, new StringDeserializer(), json);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentProcessResult> paymentResultKafkaListenerContainerFactory() {
        var f = new ConcurrentKafkaListenerContainerFactory<String, PaymentProcessResult>();
        f.setConsumerFactory(paymentResultConsumerFactory());
        return f;
    }

    // ---- Producer: payment.process.request ----
    @Bean
    public ProducerFactory<String, Object> orderProducerFactory() {
        var cfg = new HashMap<>(props.buildProducerProperties(null));
        cfg.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        cfg.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaProducerFactory<>(cfg);
    }

    @Bean
    public KafkaTemplate<String, Object> orderKafkaTemplate() {
        return new KafkaTemplate<>(orderProducerFactory());
    }
}