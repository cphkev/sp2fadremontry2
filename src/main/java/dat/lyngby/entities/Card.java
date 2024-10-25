package dat.lyngby.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import dat.lyngby.dtos.CardDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "cards")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)//Mega scuffed løsning
public class Card {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @EqualsAndHashCode.Include//Mega scuffed løsning
    private int id;

    private String cardName;
    private String description;
    private String rarity;
    private int price;
    private boolean isShiny;
    private int attack;
    private int defense;
    private int chance;
    private int aura;
    private int evolutionStage;

    @ManyToMany(mappedBy = "cards",fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude//Mega scuffed løsning
    @JsonIgnore
    private Set<Pack> packs = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "inventory_cards",
            joinColumns = @JoinColumn(name = "card_id"),
            inverseJoinColumns = @JoinColumn(name = "inventory_id"))
    private Inventory inventory;

    public Card(String cardName, String description, String rarity, int price, boolean isShiny, int attack, int defense, int chance, int aura, int evolutionStage, Set<Pack> pack,Inventory inventory) {
        this.cardName = cardName;
        this.description = description;
        this.rarity = rarity;
        this.price = price;
        this.isShiny = isShiny;
        this.attack = attack;
        this.defense = defense;
        this.chance = chance;
        this.aura = aura;
        this.evolutionStage = evolutionStage;
        this.packs = pack;
        this.inventory = inventory;
    }

    public Card(String cardName, String description, String rarity, int price, boolean isShiny, int attack, int defense, int aura, int evolutionStage, Set<Pack> pack) {
        this.cardName = cardName;
        this.description = description;
        this.rarity = rarity;
        this.price = price;
        this.isShiny = isShiny;
        this.attack = attack;
        this.defense = defense;
        this.aura = aura;
        this.evolutionStage = evolutionStage;
        this.packs = pack;
       // this.inventory = inventory;
    }


    public Card(String cardName, String description, String rarity, int price, boolean isShiny, int attack, int defense, int chance, int aura, int evolutionStage) {
        this.cardName = cardName;
        this.description = description;
        this.rarity = rarity;
        this.price = price;
        this.isShiny = isShiny;
        this.attack = attack;
        this.defense = defense;
        this.chance = chance;
        this.aura = aura;
        this.evolutionStage = evolutionStage;
    }

    public Card(CardDTO cardDTO) {
        this.id = cardDTO.getId();
        this.cardName = cardDTO.getCardName();
        this.description = cardDTO.getDescription();
        this.rarity = cardDTO.getRarity();
        this.price = cardDTO.getPrice();
        this.isShiny = cardDTO.isShiny();
        this.attack = cardDTO.getAttack();
        this.defense = cardDTO.getDefense();
      this.chance = cardDTO.getChance();
        this.aura = cardDTO.getAura();
        this.evolutionStage = cardDTO.getEvolutionStage();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return price == card.price &&
                isShiny == card.isShiny &&
                attack == card.attack &&
                defense == card.defense &&
                chance == card.chance &&
                aura == card.aura &&
                evolutionStage == card.evolutionStage &&
                Objects.equals(cardName, card.cardName) &&
                Objects.equals(description, card.description) &&
                Objects.equals(rarity, card.rarity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardName, description, rarity, price, isShiny, attack, defense, chance, aura, evolutionStage);
    }



}
