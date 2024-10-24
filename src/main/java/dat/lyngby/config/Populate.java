package dat.lyngby.config;


import dat.lyngby.daos.CardDAO;
import dat.lyngby.dtos.CardDTO;
import dat.lyngby.entities.Card;
import dat.lyngby.entities.Inventory;
import dat.lyngby.entities.Pack;
import dat.lyngby.security.entities.Role;
import dat.lyngby.security.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.jetbrains.annotations.NotNull;


import java.util.Set;

public class Populate {
   public static Set<Pack> jonesPack;
   public static Inventory playerInventory;
    public static void Populate() {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        User user = new User("username1","password1");
        User adminUser = new User("admin","admin123");
        Role adminRole = new Role("ADMIN");
        adminUser.addRole(adminRole);
        Set<Card> jonesCards = getJonesCards();
        CardDAO cardDAO = new CardDAO(emf);


        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(adminRole);
            em.persist(user);
            em.persist(adminUser);
            for (Card card : jonesCards) {
               em.persist(card);
            }
            Pack jonesPack = new Pack("Jones Pack","lort", jonesCards);
            jonesPack.setCards(jonesCards);
            playerInventory = new Inventory(user);
            em.persist(playerInventory);
            for (Card card : jonesCards) {
                card.setInventory(playerInventory);
            }

            em.persist(jonesPack);
            em.getTransaction().commit();

        }
    }

    @NotNull
    private static Set<Card> getJonesCards() {
       Card c1 = new Card("Jones", "Jones er mystisk og modig, der altid er klar til kamp. Med sin lynhurtige slagteknik og jernvilje kan den tage imod selv de stærkeste modstandere.","Legendary",100,false,20,20,1,-20,3,jonesPack,playerInventory);
         Card c2 = new Card("Fred1", "Fred1 er en rolig og analytisk Pokémon, kendt for at tage sine modstandere med overraskende strategier. Dens evne til at forudsige modstanderens næste træk gør den til en uforudsigelig kraft i kamp.","Rare",120,true,30,40,1,100,2,jonesPack,playerInventory);
            Card c3 = new Card("Fred2", "Fred2 er en afslappet Pokémon, der altid har tid til at nyde en god drink. Hans kæmpe tørst efter væske giver ham en unik fordel i kamp, da han finder styrke i de mest uventede øjeblikke.","Normal",20,false,10,10,1,50,1,jonesPack,playerInventory);

            Card[] cardArray = {c1, c2, c3};
            return Set.of(cardArray);
    }

//    @NotNull
//    private static Set<Card> getMagnusCards() {
//       return null;
//    }
}