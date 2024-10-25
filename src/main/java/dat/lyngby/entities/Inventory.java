package dat.lyngby.entities;

import dat.lyngby.security.entities.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "inventories")
public class Inventory {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "username")
    private User user;

    @OneToMany(mappedBy = "inventory", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    public Inventory(User user) {
        this.user = user;
    }
}
