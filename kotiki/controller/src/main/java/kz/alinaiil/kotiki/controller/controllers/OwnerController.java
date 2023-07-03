package kz.alinaiil.kotiki.controller.controllers;

import jakarta.validation.Valid;
import kz.alinaiil.kotiki.controller.creators.OwnerCreateDto;
import kz.alinaiil.kotiki.service.dto.KittyDto;
import kz.alinaiil.kotiki.service.dto.OwnerDto;
import kz.alinaiil.kotiki.service.services.OwnerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners/")
public class OwnerController {
    private final OwnerServiceImpl ownerServiceImpl;

    @Autowired
    public OwnerController(OwnerServiceImpl ownerServiceImpl) {
        this.ownerServiceImpl = ownerServiceImpl;
    }

    @PostMapping()
    public OwnerDto createOwner(@Valid @RequestBody OwnerCreateDto ownerDto) {
        return ownerServiceImpl.createOwner(ownerDto.name(), ownerDto.birthDate());
    }

    @GetMapping("{id}")
    public OwnerDto getOwnerById(@PathVariable int id) {
        return ownerServiceImpl.getOwnerById(id);
    }

    @GetMapping("kitties/{id}")
    public List<KittyDto> findAllKitties(@PathVariable int id) {
        return ownerServiceImpl.findAllKitties(id);
    }

    @GetMapping()
    public List<OwnerDto> findAllOwners() {
        return ownerServiceImpl.findAllOwners();
    }

    @DeleteMapping("{id}")
    public String removeOwner(@PathVariable int id) {
        ownerServiceImpl.removeOwner(id);
        return "Owner with id " + id + " was deleted";
    }
}
