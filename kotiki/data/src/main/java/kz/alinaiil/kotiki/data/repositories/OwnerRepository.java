package kz.alinaiil.kotiki.data.repositories;

import kz.alinaiil.kotiki.data.models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
}
