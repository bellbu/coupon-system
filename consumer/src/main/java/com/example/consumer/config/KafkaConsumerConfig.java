package com.example.consumer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {  // Consumer 작업을 하기 위한 설정

    @Bean
    public ConsumerFactory<String, Long> consumerFactory() {  // Kafka 소비자 인스턴스를 생성하는 역할
        Map<String, Object> config = new HashMap<>(); // 연결 정보와 소비자에 대한 설정을 지정

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // 서버의 정보를 추가
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_1"); // 소비자가 속한 그룹 ID를 지정
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // 메시지 키를 역직렬화
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class); // 메시지 값을 역직렬화

        return new DefaultKafkaConsumerFactory<>(config); // config 맵을 이용해 DefaultKafkaConsumerFactory 객체를 생성하고 반환
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Long> kafkaListenerContainerFactory() { // Kafka 리스너 컨테이너 생성
        ConcurrentKafkaListenerContainerFactory<String, Long> factory = new ConcurrentKafkaListenerContainerFactory<>(); // Kafka 메시지를 비동기적으로 처리할 수 있는 리스너 컨테이너를 생성
        factory.setConsumerFactory(consumerFactory()); // 위에서 정의한 consumerFactory를 설정하여, 이 설정을 기반으로 Kafka와 연결하고 메시지를 가져옴

        return factory;
    }
}
