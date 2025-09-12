package com.eventhub.payment_service.messaging;

import com.eventhub.payment_service.messaging.dto.PaymentProcessRequest;
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

    // ---- CONSUMER: payment.process.request ----
    @Bean
    public ConsumerFactory<String, PaymentProcessRequest> paymentConsumerFactory() {
        Map<String, Object> cfg = new HashMap<>(props.buildConsumerProperties(null));
        JsonDeserializer<PaymentProcessRequest> json = new JsonDeserializer<>(PaymentProcessRequest.class);
        json.addTrustedPackages("*"); // dilersen "com.eventhub.payment_service.messaging.dto" yaz
        cfg.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        cfg.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(cfg, new StringDeserializer(), json);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentProcessRequest> paymentKafkaListenerContainerFactory() {
        var f = new ConcurrentKafkaListenerContainerFactory<String, PaymentProcessRequest>();
        f.setConsumerFactory(paymentConsumerFactory());
        return f;
    }

    // ---- PRODUCER: payment.process.result ----
    @Bean
    public ProducerFactory<String, Object> paymentProducerFactory() {
        var cfg = new HashMap<>(props.buildProducerProperties(null));
        cfg.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        cfg.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaProducerFactory<>(cfg);
    }

    @Bean
    public KafkaTemplate<String, Object> paymentKafkaTemplate() {
        return new KafkaTemplate<>(paymentProducerFactory());
    }
}
