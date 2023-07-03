package kz.alinaiil.kotiki.controller.config;

import kz.alinaiil.kotiki.data.dto.KittyDto;
import kz.alinaiil.kotiki.data.dto.KittyListDto;
import kz.alinaiil.kotiki.data.dto.OwnerDto;
import kz.alinaiil.kotiki.data.dto.OwnerListDto;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<String, OwnerDto> consumerOwnerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, ErrorHandlingDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, OwnerDto.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public Consumer<String, OwnerDto> createOwnerFactory(ConsumerFactory<String, OwnerDto> consumerOwnerFactory) {
        Consumer<String, OwnerDto> consumer = consumerOwnerFactory.createConsumer("groupIdCOF", "clientIdCOF");
        consumer.subscribe(List.of("got_by_id_owner"));
        return consumer;
    }

    @Bean
    public ConsumerFactory<String, OwnerListDto> consumerOwnersFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, ErrorHandlingDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, OwnerListDto.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public Consumer<String, OwnerListDto> createOwnersFactory(ConsumerFactory<String, OwnerListDto> consumerOwnersFactory) {
        Consumer<String, OwnerListDto> consumer = consumerOwnersFactory.createConsumer("groupIdCOsF", "clientIdCOsF");
        consumer.subscribe(List.of("got_owners"));
        return consumer;
    }

    @Bean
    public ConsumerFactory<String, KittyListDto> consumerKittiesFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, ErrorHandlingDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, KittyListDto.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean(name = "COsKF")
    public Consumer<String, KittyListDto> createOwnersKittiesFactory(ConsumerFactory<String, KittyListDto> consumerKittiesFactory) {
        Consumer<String, KittyListDto> consumer = consumerKittiesFactory.createConsumer("groupIdCOsKF", "clientIdCOsKF");
        consumer.subscribe(List.of("got_by_id_owners_kitties"));
        return consumer;
    }

    @Bean(name = "CKFF")
    public Consumer<String, KittyListDto> createKittiesFriendsFactory(ConsumerFactory<String, KittyListDto> consumerKittiesFactory) {
        Consumer<String, KittyListDto> consumer = consumerKittiesFactory.createConsumer("groupIdCKFF", "clientIdCKFF");
        consumer.subscribe(List.of("got_by_id_friends"));
        return consumer;
    }

    @Bean(name = "CKF")
    public Consumer<String, KittyListDto> createKittiesFactory(ConsumerFactory<String, KittyListDto> consumerKittiesFactory) {
        Consumer<String, KittyListDto> consumer = consumerKittiesFactory.createConsumer("groupIdCKF", "clientIdCKF");
        consumer.subscribe(List.of("got_kitties"));
        return consumer;
    }

    @Bean(name = "CFF")
    public Consumer<String, KittyListDto> createFilteredFactory(ConsumerFactory<String, KittyListDto> consumerKittiesFactory) {
        Consumer<String, KittyListDto> consumer = consumerKittiesFactory.createConsumer("groupIdCFF", "clientIdCFF");
        consumer.subscribe(List.of("got_kitties_by_filters"));
        return consumer;
    }

    @Bean
    public ConsumerFactory<String, KittyDto> consumerKittyFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, ErrorHandlingDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, KittyDto.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public Consumer<String, KittyDto> createKittyFactory(ConsumerFactory<String, KittyDto> consumerKittyFactory) {
        Consumer<String, KittyDto> consumer = consumerKittyFactory.createConsumer("groupId", "clientId");
        consumer.subscribe(List.of("got_by_id_kitty"));
        return consumer;
    }
}
