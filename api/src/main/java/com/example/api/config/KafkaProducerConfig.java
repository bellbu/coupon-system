package com.example.api.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig { // Producer 작업을 하기위한 설정

    @Bean
    public ProducerFactory<String, Long> producerFactory() { // ProducerFactory: Kafka 메시지를 생성하는데 필요한 설정을 제공
        Map<String, Object> config = new HashMap<>(); // Kafka 프로듀서의 설정 값들을 담기위한 맵을 변수로 정의

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // 서버의 정보를 추가
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // 메시지 키를 직렬화
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, LongSerializer.class); // 메시지의 값을 직렬화

        return new DefaultKafkaProducerFactory<>(config); // config 맵을 이용해 DefaultKafkaProducerFactory 객체를 생성하고 반환
    }

    @Bean
    public KafkaTemplate<String, Long> kafkaTemplate() { // Kafka로 메시지를 전송할 때 사용되는 템플릿 역할
        return new KafkaTemplate<>(producerFactory()); // KafkaTemplate은 producerFactory() 메서드를 통해 생성된 ProducerFactory를 사용하여 생성
    }

}
