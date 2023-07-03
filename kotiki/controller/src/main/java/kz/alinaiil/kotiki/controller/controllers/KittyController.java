package kz.alinaiil.kotiki.controller.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
@RequestMapping("/kitties/")
public class KittyController {
    private final KafkaTemplate<String, KittyCreateDto> createKitty;
    private final KafkaTemplate<String, Integer> getKitty;
    private final KafkaTemplate<String, FriendsDto> friends;
    private final KafkaTemplate<String, FilterDto> filters;
    private final Consumer<String, KittyListDto> consumerKitties;
    private final Consumer<String, KittyListDto> consumerFriends;
    private final Consumer<String, KittyListDto> consumerFiltered;
    private final Consumer<String, KittyDto> consumer;

    @Autowired
    public KittyController(KafkaTemplate<String, KittyCreateDto> createKitty, KafkaTemplate<String, Integer> getKitty, KafkaTemplate<String, FriendsDto> friends, KafkaTemplate<String, FilterDto> filters, @Qualifier("CKF") Consumer<String, KittyListDto> consumerKitties, @Qualifier("CKFF") Consumer<String, KittyListDto> consumerFriends, @Qualifier("CFF") Consumer<String, KittyListDto> consumerFiltered, Consumer<String, KittyDto> consumer) {
        this.createKitty = createKitty;
        this.getKitty = getKitty;
        this.friends = friends;
        this.filters = filters;
        this.consumerKitties = consumerKitties;
        this.consumerFriends = consumerFriends;
        this.consumerFiltered = consumerFiltered;
        this.consumer = consumer;
    }

    @PostMapping()
    public void createKitty(@Valid @RequestBody KittyCreateDto kittyDto) {
        createKitty.send("create_kitty", kittyDto);
    }

    @GetMapping("{id}")
    public KittyDto getKittyById(@PathVariable int id) throws InterruptedException {
        getKitty.send("get_by_id_kitty", id);
        TimeUnit.MILLISECONDS.sleep(1000);
        ConsumerRecords<String, KittyDto> consumerRecords = consumer.poll(Duration.ofMillis(2000));
        Iterator<ConsumerRecord<String, KittyDto>> iterator = consumerRecords.iterator();
        if (iterator.hasNext()) {
            KittyDto value = iterator.next().value();
            consumer.commitSync();
            return value;
        } else {
            return getKittyById(id);
        }
    }

    @GetMapping("friends/{id}")
    public List<KittyDto> findAllFriends(@PathVariable int id) throws InterruptedException {
        getKitty.send("get_by_id_friends", id);
        TimeUnit.MILLISECONDS.sleep(1000);
        ConsumerRecords<String, KittyListDto> consumerRecords = consumerFriends.poll(Duration.ofMillis(2000));
        Iterator<ConsumerRecord<String, KittyListDto>> iterator = consumerRecords.iterator();
        if (iterator.hasNext()) {
            KittyListDto value = iterator.next().value();
            consumerFriends.commitSync();
            return value.kitties();
        } else {
            return findAllFriends(id);
        }
    }

    @GetMapping()
    public List<KittyDto> findAllKitties() throws InterruptedException {
        getKitty.send("get_kitties", 0);
        TimeUnit.MILLISECONDS.sleep(1000);
        ConsumerRecords<String, KittyListDto> consumerRecords = consumerKitties.poll(Duration.ofMillis(2000));
        Iterator<ConsumerRecord<String, KittyListDto>> iterator = consumerRecords.iterator();
        if (iterator.hasNext()) {
            KittyListDto value = iterator.next().value();
            consumerKitties.commitSync();
            return value.kitties();
        } else {
            return findAllKitties();
        }
    }

    @DeleteMapping("{id}")
    public String removeKitty(@PathVariable int id) {
        getKitty.send("remove_kitty", id);
        return "Kitty with id " + id + " was deleted";
    }

    @GetMapping("get")
    public List<KittyDto> findKittyBy(@NotBlank(message = "Breed should not be blank") @RequestParam(name = "breed", defaultValue = "empty") String breed, @RequestParam(name = "colour", defaultValue = "empty") @NotBlank(message = "Colour should not be blank") String colour) throws InterruptedException {
        if (colour.equals("empty")) {
            filters.send("get_kitties_by_breed", new FilterDto(breed, colour));
        } else if (breed.equals("empty")) {
            filters.send("get_kitties_by_colour", new FilterDto(breed, colour));
        } else {
            filters.send("get_kitties_by_filters", new FilterDto(breed, colour));
        }
        TimeUnit.MILLISECONDS.sleep(1000);
        ConsumerRecords<String, KittyListDto> consumerRecords = consumerFiltered.poll(Duration.ofMillis(2000));
        Iterator<ConsumerRecord<String, KittyListDto>> iterator = consumerRecords.iterator();
        if (iterator.hasNext()) {
            KittyListDto value = iterator.next().value();
            consumerFiltered.commitSync();
            return value.kitties();
        } else {
            return findKittyBy(breed, colour);
        }
    }

    @PutMapping("befriend")
    public String makeFriends(@RequestParam(name = "kitty") int kittyId1, @RequestParam(name = "friend") int kittyId2) {
        friends.send("befriend", new FriendsDto(kittyId1, kittyId2));
        return "Kitties " + kittyId1 + " and " + kittyId2 + " are friends now!";
    }

    @PutMapping("unfriend")
    public String unfriend(@RequestParam(name = "kitty") int kittyId1, @RequestParam(name = "ex-friend") int kittyId2) {
        friends.send("unfriend", new FriendsDto(kittyId1, kittyId2));
        return "Kitties " + kittyId1 + " and " + kittyId2 + " are not friends";
    }
}
