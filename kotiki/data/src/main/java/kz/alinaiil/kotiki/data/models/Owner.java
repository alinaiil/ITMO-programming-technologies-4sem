package kz.alinaiil.kotiki.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "birthDate")
    private LocalDate birthDate;
    @OneToMany(mappedBy = "owner", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Kitty> kitties;

    public Owner(String name, LocalDate birthDate, List<Kitty> kitties) {
        this.name = name;
        this.birthDate = birthDate;
        this.kitties = kitties;
    }

    public void addKitty(Kitty kitty) {
        Objects.requireNonNull(kitty);
        if (!kitties.contains(kitty)) {
            kitties.add(kitty);
        }
    }

    public void removeKitty(Kitty kitty) {
        Objects.requireNonNull(kitty);
        kitties.removeIf(k -> k.getId() == kitty.getId());
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
