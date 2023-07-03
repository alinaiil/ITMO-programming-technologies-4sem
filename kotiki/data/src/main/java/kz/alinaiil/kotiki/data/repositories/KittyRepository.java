package kz.alinaiil.kotiki.data.repositories;

import kz.alinaiil.kotiki.data.models.Kitty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KittyRepository extends JpaRepository<Kitty, Integer> {
}