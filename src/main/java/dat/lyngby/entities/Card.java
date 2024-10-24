package dat.lyngby.entities;


import dat.lyngby.dtos.CardDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
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
    private Set<Pack> packs;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "inventory_cards",
            joinColumns = @JoinColumn(name = "card_id"),
            inverseJoinColumns = @JoinColumn(name = "inventory_id"))
    private Inventory inventory;

    public Card(String cardName, String description, String rarity, int price, boolean isShiny, int attack, int defense, int chance, int aura, int evolutionStage, Set<Pack> pack, Inventory inventory) {
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

    public Card(String cardName, String description, String rarity, int price, boolean isShiny, int attack, int defense, int aura, int evolutionStage, Set<Pack> pack, Inventory inventory) {
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
        this.inventory = inventory;
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
}
