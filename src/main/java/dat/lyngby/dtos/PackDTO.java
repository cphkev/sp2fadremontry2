package dat.lyngby.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dat.lyngby.entities.Card;
import dat.lyngby.entities.Pack;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PackDTO {
    private Integer id;
    private String name;
    private String description;
    private Set<Card> cards;

    public PackDTO(Pack pack) {
        this.id = pack.getId();
        this.name = pack.getName();
        this.description = pack.getDescription();
        this.cards = pack.getCards();
    }

    public static List<PackDTO> toPackDTOList(List<Pack> packs) {
        return packs.stream().map(PackDTO::new).toList();
    }


    public Pack toEntity() {
        return new Pack(
                this.name,
                this.description,
                this.cards
        );


    }
}
