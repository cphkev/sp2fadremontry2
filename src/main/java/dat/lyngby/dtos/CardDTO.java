package dat.lyngby.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dat.lyngby.entities.Card;
import dat.lyngby.entities.Inventory;
import dat.lyngby.entities.Pack;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardDTO {
    private Integer id;
    private String cardName;
    private String rarity;
    private boolean isShiny;
    private int attack;
    private int defense;
    private int price;
    private int chance;
    private String description;
    private int aura;
    private int evolutionStage;
    private Set<Pack> packs = new HashSet<>();
    private Inventory inventory;

    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardName = card.getCardName();
        this.rarity = card.getRarity();
        this.isShiny = card.isShiny();
        this.attack = card.getAttack();
        this.defense = card.getDefense();
        this.price = card.getPrice();
        this.chance = card.getChance();
        this.description = card.getDescription();
        this.aura = card.getAura();
        this.packs = card.getPacks().stream().map(Pack::new).collect(Collectors.toSet());
        this.evolutionStage = card.getEvolutionStage();
    }


    public static List<CardDTO> toCardDTOList(List<Card> cards) {
        return List.of(cards.stream().map(CardDTO::new).toArray(CardDTO[]::new));
    }


    public Card toEntity() {
        Card card = new Card(
                this.cardName,
                this.description,
                this.rarity,
                this.price,
                this.isShiny,
                this.attack,
                this.defense,
                this.chance,
                this.aura,
                this.evolutionStage,
                this.packs,
                this.inventory
        );
        return card;
    }




}
