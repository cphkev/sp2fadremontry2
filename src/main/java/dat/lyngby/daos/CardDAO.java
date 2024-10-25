package dat.lyngby.daos;

import dat.lyngby.dtos.CardDTO;
import dat.lyngby.dtos.PackDTO;
import dat.lyngby.entities.Card;
import dat.lyngby.entities.Inventory;
import dat.lyngby.entities.Pack;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou
 */

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CardDAO implements IDAO<CardDTO, Integer> {
    private static CardDAO instance;

    private static EntityManagerFactory emf;

    public static CardDAO getInstance(EntityManagerFactory factory) {
        if (instance == null) {
            emf = factory;
            instance = new CardDAO();

        }
        return instance;
    }

    public CardDAO (EntityManagerFactory emf){
        CardDAO.emf = emf;
    }


    @Override
    public CardDTO create(CardDTO cardDTO) {
        try (EntityManager em = emf.createEntityManager()) {

            Inventory inventory = cardDTO.getInventory();
            if (inventory != null) {
                em.persist(inventory);
            }
            em.getTransaction().begin();
            Card card = cardDTO.toEntity();
            em.persist(card);
            em.getTransaction().commit();
            return new CardDTO(card);
        }
    }

    @Override
    public CardDTO getById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            Card card = em.find(Card.class, id);
            return new CardDTO(card);
        }
    }

    @Override
    public CardDTO update(Integer integer, CardDTO cardDTO) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Card card = em.find(Card.class, integer);
            card.setCardName(cardDTO.getCardName());
            card.setDescription(cardDTO.getDescription());
            card.setRarity(cardDTO.getRarity());
            card.setPrice(cardDTO.getPrice());
            card.setShiny(cardDTO.isShiny());
            card.setAttack(cardDTO.getAttack());
            card.setDefense(cardDTO.getDefense());
            card.setChance(cardDTO.getChance());
            card.setAura(cardDTO.getAura());
            card.setEvolutionStage(cardDTO.getEvolutionStage());
            Card mergedCard = em.merge(card);
            em.getTransaction().commit();
            return mergedCard != null ? new CardDTO(mergedCard) : null;
        }

    }

//    @Override
//    public void delete(int id) {
//        try (var em = emf.createEntityManager()) {
//            em.getTransaction().begin();
//            Card card = em.find(Card.class, id);
//            if (card != null){
//                card.getPacks().forEach(pack -> pack.getCards().remove(card));
//                em.remove(card);
//            }
//            em.getTransaction().commit();
//        } finally {
//            emf.close();
//        }
//
//    }

//    @Override
//    public void delete(int id) {
//        try (var em = emf.createEntityManager()) {
//            em.getTransaction().begin();
//            Card card = em.find(Card.class, id);
//            if (card != null) {
//                // Remove the card from all associated packs
//                for (Pack pack : card.getPacks()) {
//                    pack.removeCard(card);
//                    em.merge(pack); // Update the Pack entity to reflect the changes
//                }
//                em.remove(card);
//            }
////                // Remove the card from the inventory if it exists
////                if (card.getInventory() != null) {
////                    card.getInventory().getCards().remove(card);
////                    em.merge(card.getInventory()); // Update the Inventory entity to reflect the changes
////                }
////                em.remove(card);
////            }
//            em.getTransaction().commit();
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new RuntimeException("Failed to delete card with id:" + id, e);
//        }
//    }

    @Override
    public void delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Card card = em.find(Card.class, id);
            if (card != null) {
                em.remove(card);
                for (Pack pack:card.getPacks()) {
                    pack.removeCard(card);
                    em.merge(pack);

                }
                em.remove(card);
            }
            em.getTransaction().commit();
        }
    }
    @Override
    public List<CardDTO> getAll() {
        try (var em = emf.createEntityManager()) {
            TypedQuery<CardDTO> query = em.createQuery("SELECT new dat.lyngby.dtos.CardDTO(c) FROM Card c", CardDTO.class);
            return query.getResultList();
        }
    }


    public PackDTO addCardToPack(int packId, CardDTO cardDTO) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Card card = em.merge( new Card(cardDTO));
            Pack pack = em.find(Pack.class, packId);
            pack.addCard(card);
            em.persist(card);
            Pack mergedPack = em.merge(pack);
            em.getTransaction().commit();
            return new PackDTO(mergedPack);
        }
    }
    





    public Card getByRarity(String rarity) {
        try (var em = emf.createEntityManager()) {
            return em.createQuery("SELECT c FROM Card c WHERE c.rarity = :rarity", Card.class).setParameter("rarity", rarity).getSingleResult();
        }
    }

//    public Card getByMinPrice(int price) {
//        try (var em = emf.createEntityManager()) {
//            return em.createQuery("SELECT c FROM Card c WHERE c.price >= :price", Card.class).setParameter("price", price).getSingleResult();
//        }
public List<Card> getByMinPrice(int minPrice) {
    try (var em = emf.createEntityManager()) {
        String jpql = "SELECT c FROM Card c WHERE c.price >= :minPrice";
        return em.createQuery(jpql, Card.class)
                .setParameter("minPrice", minPrice)
                .getResultList();
    }
    }


public List<Card> getByMaxPrice(int maxPrice) {
    try (var em = emf.createEntityManager()) {
        String jpql = "SELECT c FROM Card c WHERE c.price <= :maxPrice";
        return em.createQuery(jpql, Card.class)
                .setParameter("maxPrice", maxPrice)
                .getResultList();
    }
    }



    public List<Card> getByMinAndMaxPrice(int minPrice, int maxPrice) {
        try (var em = emf.createEntityManager()) {
            System.out.println("==================================");
            System.out.println("Creating query for cards between price " + minPrice + " and " + maxPrice);
            System.out.println("==================================");
            String jpql = "SELECT c FROM Card c WHERE c.price >= :minPrice AND c.price <= :maxPrice";
            List<Card> cards = null;
            try {
               cards = em.createQuery(jpql, Card.class)
                        .setParameter("minPrice", minPrice)
                        .setParameter("maxPrice", maxPrice)
                        .getResultList();
                System.out.println("==================================");
                System.out.println("Query executed successfully, number of cards found: " + cards.size());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error getting cards between price " + minPrice + " and " + maxPrice, e);
            }
            System.out.println("Cards found between price " + minPrice + " and " + maxPrice + ":");
            for (Card card : cards) {
                System.out.println("Card ID: " + card.getId() + ", Name: " + card.getCardName() + ", Price: " + card.getPrice());
            }

            return cards;
        }
        }


    public List<Card> getByMinAttack(int minAttack) {
        try (var em = emf.createEntityManager()) {
            String jpql = "SELECT c FROM Card c WHERE c.attack >= :minAttack";
            return em.createQuery(jpql, Card.class)
                    .setParameter("minAttack", minAttack)
                    .getResultList();
        }
    }

    public List<Card> getByMaxAttack(int maxAttack) {
        try (var em = emf.createEntityManager()) {
            String jpql = "SELECT c FROM Card c WHERE c.attack <= :maxAttack";
            return em.createQuery(jpql, Card.class)
                    .setParameter("maxAttack", maxAttack)
                    .getResultList();
        }
    }

    public List<Card> getByMinAndMaxAttack(int minAttack, int maxAttack) {
        try (var em = emf.createEntityManager()) {
            String jpql = "SELECT c FROM Card c WHERE c.attack >= :minAttack AND c.attack <= :maxAttack";
            return em.createQuery(jpql, Card.class)
                    .setParameter("minAttack", minAttack)
                    .setParameter("maxAttack", maxAttack)
                    .getResultList();
        }
    }




//    public void deleteCardFromPack(int packId, int cardId) {
//        try (var em = emf.createEntityManager()) {
//            em.getTransaction().begin();
//            Pack pack = em.find(Pack.class, packId);
//            Card card = em.find(Card.class, cardId);
//            System.out.println("==================================");
//            System.out.println("Pack: " + pack);
//            System.out.println("Card: " + card);
//            if (pack != null && card != null) {
//                pack.removeCard(card);
//                em.merge(pack);
//                em.getTransaction().commit();
//            } else {
//                em.getTransaction().rollback();
//                throw new RuntimeException("Pack or Card not found");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error deleting card from pack", e);
//        }


public void deleteCardFromPack(int packId, int cardId) {
    try (var em = emf.createEntityManager()) {
        em.getTransaction().begin();
        Pack pack = em.find(Pack.class, packId);
        Card card = em.find(Card.class, cardId);
        if (pack != null && card != null) {
            pack.removeCard(card);
            em.merge(pack);
        }
        em.getTransaction().commit();
    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Error deleting card from pack", e);
    }
}


    public void deleteCardFromInventory(int inventoryId, int cardId) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Inventory inventory = em.find(Inventory.class, inventoryId);
            Card card = em.find(Card.class, cardId);
            if (inventory != null && card != null) {
                inventory.getCards().remove(card);
                em.merge(inventory);
                em.getTransaction().commit();
            } else {
                em.getTransaction().rollback();
                throw new RuntimeException("Inventory or Card not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting card from inventory", e);
        }
    }
}
