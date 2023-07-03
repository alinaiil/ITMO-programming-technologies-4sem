package kz.alinaiil.kotiki.data.repositories;

import kz.alinaiil.kotiki.data.models.Breed;
import kz.alinaiil.kotiki.data.models.Colour;
import kz.alinaiil.kotiki.data.models.Kitty;
import kz.alinaiil.kotiki.data.models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KittyRepository extends JpaRepository<Kitty, Integer> {
}
