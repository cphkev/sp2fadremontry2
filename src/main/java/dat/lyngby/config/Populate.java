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


import java.util.HashSet;
import java.util.Set;

public class Populate {
//   public static Set<Pack> jonesPack;


    public static void Populate() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        Set<Card> cards = getCards();
        Set<Card> jonesCards = getJonesCards();
        Set<Card> magnusCards = getMagnusCards();
        Set<Card> underviserCards = getUnderviserCards();


        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Persist each Card instance first
            for (Card card : cards) {
                em.persist(card);
            }
            for (Card card : jonesCards) {
                em.persist(card);
            }
            for (Card card : magnusCards) {
                em.persist(card);
            }
            for (Card card : underviserCards) {
                em.persist(card);
            }


            Pack pack1 = new Pack("Jones pack", "En pakke til jonas");
            Pack pack2 = new Pack("pack", "En pakke til paack");
            Pack pack3 = new Pack("magnus pack", "En pakke til magnus");
            Pack pack4 = new Pack("Underviser pack", "En pakke til undervisr");

            pack1.setCards(cards);
            pack2.setCards(jonesCards);
            pack3.setCards(magnusCards);
            pack4.setCards(underviserCards);

            em.persist(pack1);
            em.persist(pack2);
            em.persist(pack3);
            em.persist(pack4);


            em.getTransaction().commit();
        }
    }




//    public static void Populate () {
//        Inventory playerInventory;
//        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
//        User user = new User("username1", "password1");
//        User adminUser = new User("admin", "admin123");
//        Role adminRole = new Role("ADMIN");
//        adminUser.addRole(adminRole);
//
//
//        Set<Card> jonesCards = new HashSet<>();
////        CardDAO cardDAO = new CardDAO(emf);
//
//
//        try (var em = emf.createEntityManager()) {
//            em.getTransaction().begin();
//            em.persist(adminRole);
//            em.persist(user);
//            em.persist(adminUser);
////            for (Card card : jonesCards) {
////               em.persist(card);
////            }
//
//            Pack jonesPack = new Pack("Jones Pack", "lort", jonesCards);
//            jonesPack.setCards(jonesCards);
//            playerInventory = new Inventory(user);
//            em.persist(playerInventory);
//            for (Card card : jonesCards) {
//                card.setInventory(playerInventory);
//            }
//
//            em.persist(jonesPack);
//            em.getTransaction().commit();
//
//        }
//    }




    @NotNull
    private static Set<Card> getCards () {
        Card c1 = new Card("jones", "Mystisk kort", "Common", 100, false, 20, 10, 1, 3, 1);
        Card c2 = new Card("fred", "Fred er kold.", "Rare", 120, true, 30, 40, 1, 100, 2);
        Card c3 = new Card("magnus", "Magnus er en kæmpe.", "Legendary", 200, true, 50, 50, 1, 200, 3);

        Card[] cardArray = {c1, c2, c3};
        return Set.of(cardArray);
    }

    @NotNull
    private static Set<Card> getJonesCards() {
        Card c1 = new Card("Jones", "TEST", "Legendary", 900, true, 30, 40, 1, 100, 1);
        Card c2 = new Card("Fred1", "Fred1 er en rolig og analytisk Pokémon, kendt for at tage sine modstandere med overraskende strategier. Dens evne til at forudsige modstanderens næste træk gør den til en uforudsigelig kraft i kamp.", "Rare", 120, true, 30, 40, 1, 100, 1);
        Card c3 = new Card("Fred2", "Fred2 er en afslappet Pokémon, der altid har tid til at nyde en god drink. Hans kæmpe tørst efter væske giver ham en unik fordel i kamp, da han finder styrke i de mest uventede øjeblikke.", "Normal", 20, false, 10, 10, 1, 50, 1);
        Card c4 = new Card("Marcus", "Marcus er en stærk og modig Pokémon, der altid er klar til at tage kampen op. Med sin store styrke og modige hjerte er han en uovervindelig kraft i kamp.", "Legendary", 100, false, 20, 20, 1, -20, 1);
        Card[] cardArray = {c1, c2, c3,c4};
        return Set.of(cardArray);
    }


    @NotNull
    private static Set<Card> getMagnusCards() {

        Card c5 = new Card("Gustav", "Gustav er en rolig og analytisk Pokémon, kendt for at tage sine modstandere med overraskende strategier. Dens evne til at forudsige modstanderens næste træk gør den til en uforudsigelig kraft i kamp.", "Rare", 120, true, 30, 40, 1, 100, 1);
        Card c6 = new Card("Christian", "Christian er en afslappet Pokémon, der altid har tid til at nyde en god drink. Hans kæmpe tørst efter væske giver ham en unik fordel i kamp, da han finder styrke i de mest uventede ��jeblikke.", "Normal", 20, false, 10, 10, 1, 50, 1);
        Card c7 = new Card("Anton", "Anton er en stærk og modig Pokémon, der altid er klar til at tage kampen op. Med sin store styrke og modige hjerte er han en uovervindelig kraft i kamp.", "Legendary", 100, false, 20, 20, 1, -20, 1);
        Card c8 = new Card("Magnus", "Magnus er en rolig og analytisk Pokémon, kendt for at tage sine modstandere med overraskende strategier. Dens evne til at forudsige modstanderens næste træk gør den til en uforudsigelig kraft i kamp.", "Rare", 120, true, 30, 40, 1, 100, 1);

        Card[] cardArray = {c5, c6, c7,c8};
        return Set.of(cardArray);
    }

    @NotNull
    private static Set<Card>getUnderviserCards(){
        Card c9 = new Card("Thomas", "Thomas er en afslappet Pokémon, der altid har tid til at nyde en god drink. Hans kæmpe tørst efter væske giver ham en unik fordel i kamp, da han finder styrke i de mest uventede øjeblikke.", "Normal", 20, false, 10, 10, 1, 50, 1);
        Card c10 = new Card("Jon", "Jon er en stærk og modig Pokémon, der altid er klar til at tage kampen op. Med sin store styrke og modige hjerte er han en uovervindelig kraft i kamp.", "Legendary", 100, false, 20, 20, 1, -20, 1);
        Card c11 = new Card("Jørg", "Jørg er en rolig og analytisk Pokémon, kendt for at tage sine modstandere med overraskende strategier. Dens evne til at forudsige modstanderens næste træk gør den til en uforudsigelig kraft i kamp.", "Rare", 120, true, 30, 40, 1, 100, 1);
        Card[] cardArray = {c9, c10, c11};
        return Set.of(cardArray);
    }






}








