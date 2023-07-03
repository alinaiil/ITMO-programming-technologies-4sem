package kz.alinaiil.kotiki.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table (name = "kitties")
public class Kitty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "birthdate")
    private LocalDate birthDate;

    @Column(name = "breed")
    private Breed breed;

    @Column(name = "colour")
    private Colour colour;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner")
    private Owner owner;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Kitty> friends;

    public Kitty(String name, LocalDate birthDate, Breed breed, Colour colour, Owner owner, List<Kitty> friends) {
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.colour = colour;
        this.owner = owner;
        this.friends = friends;
    }

    public void addFriend(Kitty friend) {
        Objects.requireNonNull(friend);
        if (!friends.contains(friend)) {
            friends.add(friend);
        }
    }

    public void unfriend(Kitty exfriend) {
        Objects.requireNonNull(exfriend);
        friends.remove(exfriend);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kitty kitty = (Kitty) o;
        return id == kitty.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate, breed, colour, owner, friends);
    }

    @Override
    public String toString() {
        return "Kitty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
