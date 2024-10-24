package dat.lyngby.daos;

import dat.lyngby.config.HibernateConfig;
import dat.lyngby.dtos.PackDTO;
import dat.lyngby.entities.Card;
import dat.lyngby.entities.Inventory;
import dat.lyngby.entities.Pack;
import dat.lyngby.security.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PackDAOTest {

    private static  EntityManagerFactory emf;


    //packDAO =PackDAO.getInstance(emf);
    private static  PackDAO packDAO;
//private static EntityManagerFactory emf;

    private static User testUser;
    private static Inventory testInventory;
    private static Set<Card> testCards;
    private static Set<Pack> testPacks;
    private static Set<Card> allCardsTestPack;
/*
    @BeforeAll
    void setUp() {
      emf =  HibernateConfig.getEntityManagerFactoryForTest();
        packDAO = PackDAO.getInstance(emf);
    }

    @BeforeEach
    void setUpEach() {

        try (EntityManager em = emf.createEntityManager()) {
            testUser = new User("testUser", "testPassword");
            testInventory = new Inventory(testUser);
            testCards = new HashSet<>();
            testPacks = new HashSet<>();
            Pack testPack = new Pack("testPack", "test", testCards);
            testPacks.add(testPack);
            Card card1 = new Card("Card1", "Card1 description", "Common", 5, false, 10, 10, 50, 100, 1, testPacks, testInventory);
            Card card2 = new Card("Card2", "Card2 description", "Legendary", 100, true, 1, 1, 1, 100, 1, testPacks, testInventory);
            Card card3 = new Card("Card3", "Card3 description", "Rare", 10, false, 1, 1, 50, 100, 1, testPacks, testInventory);

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
    void tearDownEach() {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Card").executeUpdate();
            em.createQuery("DELETE FROM Pack").executeUpdate();
            em.createQuery("DELETE FROM Inventory").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @AfterAll
    void tearDown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }

    }

    @Test
    void create() {
        Pack actual = new Pack("testPack", "test", testCards);
        packDAO.create(new PackDTO(actual));
        assertEquals(2, packDAO.getAll().size());
    }

    @Test
    void getById() {
        PackDTO actual = packDAO.getById(1);
        assertEquals("testPack", actual.getName());
    }

    @Test
    void update() {
        PackDTO actual = packDAO.getById(1);
        actual.setName("updatedPack");
        packDAO.update(1, actual);
        assertEquals("updatedPack", packDAO.getById(1).getName());
    }
/*
    @Test
    void delete() {

        packDAO.delete(1);

        assertNull(packDAO.getById(1));

            //Virker ikke, da der er en foreign key constraint på card, så man skal slette alle cards først.
        }



    @Test
    void getAll() {
        List<PackDTO> actual = packDAO.getAll();
        assertEquals(1, actual.size());
    }
    */

}