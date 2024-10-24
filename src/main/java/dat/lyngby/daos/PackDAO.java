package dat.lyngby.daos;

import dat.lyngby.dtos.CardDTO;
import dat.lyngby.dtos.PackDTO;
import dat.lyngby.entities.Card;
import dat.lyngby.entities.Pack;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou
 */
@NoArgsConstructor(access = lombok.AccessLevel.PUBLIC)
public class PackDAO implements IDAO<PackDTO,Integer> {
    private static PackDAO instance;

    private static EntityManagerFactory emf;

    public PackDAO (EntityManagerFactory emf){
        PackDAO.emf = emf;
    }

    public static PackDAO getInstance(EntityManagerFactory factory) {
        if (instance == null) {
            instance = new PackDAO();
            emf = factory;
        }
        return instance;
    }

    @Override
    public PackDTO create(PackDTO packDTO) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Pack pack = packDTO.toEntity();
            em.persist(pack);
            em.getTransaction().commit();
            return new PackDTO(pack);
        }
    }

    @Override
    public PackDTO getById(int id) {
        try(var em = emf.createEntityManager()){
            Pack pack = em.find(Pack.class, id);
            return new PackDTO(pack);
        }
    }

    @Override
    public PackDTO update(Integer integer, PackDTO packDTO) {
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            Pack pack = em.find(Pack.class, integer);
            pack.setName(packDTO.getName());
            pack.setDescription(packDTO.getDescription());
            Pack mergedPack = em.merge(pack);
            em.getTransaction().commit();
            return mergedPack != null ? new PackDTO(mergedPack) : null;
        }

    }

    @Override
    public void delete(int id) {
//        try (var em = emf.createEntityManager()) {
//            em.getTransaction().begin();
//            Pack pack = em.find(Pack.class, id);
//            if (pack != null) {
//                // Remove references to the Pack entity from the Card entities
//                pack.getCards().forEach(card -> {
//                    card.getPacks().remove(pack);
//                    em.merge(card); // Update the Card entity to reflect the changes
//                });
//                em.remove(pack);
//            }
//            em.getTransaction().commit();

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Pack pack = em.find(Pack.class, id);
            if (pack != null) {
                em.remove(pack);
            }
            em.getTransaction().commit();
        }
        //Virker ikke da man skal slette fra join table først
    }


    @Override
    public List<PackDTO> getAll() {
        try(var em = emf.createEntityManager()){
            TypedQuery<PackDTO> query = em.createQuery("SELECT new dat.lyngby.dtos.PackDTO(p) FROM Pack p", PackDTO.class);
            return query.getResultList();
        }
    }


    public List<Card> getCardsFromPack(int id) {
        //Vælg packe udfra pack_id
        //Få alle kort fra packen
        //Smid kortene i en liste
        try (var em = emf.createEntityManager()) {
            String jpql = "SELECT c FROM Pack p JOIN p.cards c WHERE p.id = : packId";
            return em.createQuery(jpql, Card.class)
                    .setParameter("packId", id)
                    .getResultList()
                    .stream().collect(Collectors.toList());
        }

    }
}
