package dat.lyngby.daos;

import dat.lyngby.config.HibernateConfig;
import dat.lyngby.dtos.CardDTO;
import dat.lyngby.entities.Card;
import dat.lyngby.entities.Inventory;
import dat.lyngby.entities.Pack;
import dat.lyngby.security.entities.User;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CardDAOTest {

    private static EntityManagerFactory emf;
    private static CardDAO cardDAO;

    private static User testUser;
    private static Inventory testInventory;
    private static Set<Card> testCards;
    private static Set<Pack> testPacks;

    @BeforeAll
    void setUp() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        cardDAO = CardDAO.getInstance(emf);

    }

    @BeforeEach
    void setUpEach(){
        testUser = new User("testUser","testPassword");
        testInventory = new Inventory(testUser);
        testCards = new HashSet<>();
        testPacks = new HashSet<>();
        Pack testPack = new Pack("TestPack","test",testCards);
        testPacks.add(testPack);
        Card card1 = new Card("Card1", "Card1 description","Common",5,false,10,10,50,100,1,testPacks,testInventory);
        Card card2 = new Card("Card2", "Card2 description","Legendary",100,true,1,1,1,100,1,testPacks,testInventory);
        Card card3 = new Card("Card3", "Card3 description","Rare",10,false,1,1,50,100,1,testPacks,testInventory);

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(testUser);
            em.persist(testInventory);
            em.persist(testPack);
            em.persist(card1);
            em.persist(card2);
            em.persist(card3);
            em.getTransaction().commit();
        }
    }

    @AfterEach
    void tearDownEach(){
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Card").executeUpdate();
            em.createQuery("DELETE FROM Pack").executeUpdate();
            em.createQuery("DELETE FROM Inventory").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @AfterAll
    static void tearDown() {
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    void create() {
        Card card = new Card("Card4", "Card4 description","Common",10,false,10,10,50,100,1,testPacks,testInventory);
        cardDAO.create(new CardDTO(card));
        assertEquals(4, cardDAO.getAll().size());
    }

    @Test
    void getById() {
        CardDTO actual = cardDAO.getById(1);
        assertEquals("Card1", actual.getCardName());
    }

    @Test
    void update() {
        Card card = new Card("Card4", "Card4 description", "Common", 10, false, 10, 10, 50, 100, 1, testPacks, testInventory);
        CardDTO cardDTO = cardDAO.create(new CardDTO(card));

        cardDTO.setCardName("UpdatedCard");
        cardDAO.update(cardDTO.getId(), cardDTO);


        try (var em = emf.createEntityManager()) {
            Card updatedCard = em.find(Card.class, cardDTO.getId());
            assertNotNull(updatedCard);
            assertEquals("UpdatedCard", updatedCard.getCardName());
        }
    }

    @Test
    void delete() {
        cardDAO.delete(1);
        assertEquals(2, cardDAO.getAll().size());
    }

    @Test
    void getAll() {
        assertEquals(3, cardDAO.getAll().size());
    }

    @Test
    void getByRarity() {
        assertNotNull(cardDAO.getByRarity("Common"));
    }

    @Test
    void getByMinPrice() {
        List<Card> cards = cardDAO.getByMinPrice(10);
        assertNotNull(cards);
        assertFalse(cards.isEmpty());
        assertTrue(cards.stream().allMatch(card -> card.getPrice() >= 10));
    }

    @Test
    void getByMaxPrice() {
        List<Card> cards = cardDAO.getByMaxPrice(99);
        assertNotNull(cards);
        assertFalse(cards.isEmpty());
        assertTrue(cards.stream().allMatch(card -> card.getPrice() <= 10));
    }

    @Test
    void getByMinAndMaxPrice() {
        List<Card> cards = cardDAO.getByMinAndMaxPrice(10, 50);
        assertNotNull(cards);
        assertFalse(cards.isEmpty());
        assertTrue(cards.stream().allMatch(card -> card.getPrice() >= 10 && card.getPrice() <= 50));
    }



}