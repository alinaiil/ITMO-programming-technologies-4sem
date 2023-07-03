package kz.alinaiil.kotiki.data.repositories;

import kz.alinaiil.kotiki.data.models.Owner;
import kz.alinaiil.kotiki.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByOwner(Owner owner);
}
