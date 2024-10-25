package dat.lyngby.entities;

import dat.lyngby.dtos.PackDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Rouvi
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "packs")
public class Pack {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "packs_cards",
            joinColumns = @JoinColumn(name = "pack_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private Set<Card> cards = new HashSet<>();


    public Pack(PackDTO packDTO) {
        this.id = packDTO.getId();
        this.name = packDTO.getName();
        this.description = packDTO.getDescription();
        this.cards = packDTO.getCards();
    }




    public Pack(String name, String description, Set<Card> cards) {
        this.name = name;
        this.description = description;
        this.cards = cards;
    }

    public Pack(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Pack(Pack pack) {
        this.id = pack.getId();
        this.name = pack.getName();
        this.description = pack.getDescription();
        this.cards = pack.getCards();
    }

    public void addCard(Card card){
        if(card != null) {
            this.cards.add(card);
            card.setPacks(Set.of(this));
        }

    }

    public void removeCard(Card card) {
        if (card != null) {
            this.cards.remove(card);
            card.getPacks().remove(this);
        }
    }



}
