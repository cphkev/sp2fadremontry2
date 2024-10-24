package dat.lyngby.services;

import dat.lyngby.daos.PackDAO;
import dat.lyngby.entities.Card;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou
 */
public class PackService {

    private static EntityManagerFactory emf;
    private final PackDAO packDAO = new PackDAO(emf);


    //Metode der tager fra en liste af kort og returnerer en ny liste af 5 tilfældige kort

    public List<Card> drawCards(List<Card> cards) {
        packDAO.getCardsFromPack(1);

        for (int i = 0; i < 5; i++){
            int random = (int) (Math.random() * cards.size());

            cards.add(cards.get(random));
        }
        return cards;
    }



}
