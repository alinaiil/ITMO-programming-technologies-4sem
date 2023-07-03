package kz.alinaiil.kotiki.controller.controllers;

import kz.alinaiil.kotiki.data.dto.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/owners/")
public class OwnerController {
    private final KafkaTemplate<String, OwnerCreateDto> createOwner;
    private final KafkaTemplate<String, Integer> getOwner;
    private final Consumer<String, OwnerDto> consumer;
    private final Consumer<String, OwnerListDto> consumerOwners;
    private final Consumer<String, KittyListDto> consumerKitties;

    @Autowired
    public OwnerController(KafkaTemplate<String, OwnerCreateDto> createOwner, KafkaTemplate<String, Integer> getOwner, Consumer<String, OwnerDto> consumer, Consumer<String, OwnerListDto> consumerOwners, @Qualifier("COsKF") Consumer<String, KittyListDto> consumerKitties) {
        this.createOwner = createOwner;
        this.getOwner = getOwner;
        this.consumer = consumer;
        this.consumerOwners = consumerOwners;
        this.consumerKitties = consumerKitties;
    }

    @PostMapping()
    public void createOwner(@RequestBody OwnerCreateDto ownerDto) {
        createOwner.send("create_owner", ownerDto);
    }

    @GetMapping("{id}")
    public OwnerDto getOwnerById(@PathVariable Integer id) throws InterruptedException {
        getOwner.send("get_by_id_owner", id);
        TimeUnit.MILLISECONDS.sleep(1000);
        ConsumerRecords<String, OwnerDto> consumerRecords = consumer.poll(Duration.ofMillis(2000));
        Iterator<ConsumerRecord<String, OwnerDto>> iterator = consumerRecords.iterator();
        if (iterator.hasNext()) {
            OwnerDto value = iterator.next().value();
            consumer.commitSync();
            return value;
        } else {
            return getOwnerById(id);
        }
    }

    @GetMapping("kitties/{id}")
    public List<KittyDto> findAllKitties(@PathVariable int id) throws InterruptedException {
        getOwner.send("get_by_id_owners_kitties", id);
        TimeUnit.MILLISECONDS.sleep(1000);
        ConsumerRecords<String, KittyListDto> consumerRecords = consumerKitties.poll(Duration.ofMillis(2000));
        Iterator<ConsumerRecord<String, KittyListDto>> iterator = consumerRecords.iterator();
        if (iterator.hasNext()) {
            KittyListDto value = iterator.next().value();
            consumerKitties.commitSync();
            return value.kitties();
        } else {
            return findAllKitties(id);
        }
    }

    @GetMapping()
    public List<OwnerDto> findAllOwners() throws InterruptedException {
        getOwner.send("get_owners", 0);
        TimeUnit.MILLISECONDS.sleep(1000);
        ConsumerRecords<String, OwnerListDto> consumerRecords = consumerOwners.poll(Duration.ofMillis(2000));
        Iterator<ConsumerRecord<String, OwnerListDto>> iterator = consumerRecords.iterator();
        if (iterator.hasNext()) {
            OwnerListDto value = iterator.next().value();
            consumerOwners.commitSync();
            return value.owners();
        } else {
            return findAllOwners();
        }
    }

    @DeleteMapping("{id}")
    public String removeOwner(@PathVariable Integer id) {
        getOwner.send("remove_owner", id);
        return "Owner with id " + id + " was deleted";
    }
}
