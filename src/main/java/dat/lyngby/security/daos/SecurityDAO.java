package dat.lyngby.security.daos;


import dat.lyngby.security.entities.Role;
import dat.lyngby.security.entities.User;
import dat.lyngby.security.exceptions.ApiException;
import dat.lyngby.security.exceptions.ValidationException;

import dk.bugelhartmann.UserDTO;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Purpose: To handle security in the API
 * Author: Thomas Hartmann
 */
public class SecurityDAO implements ISecurityDAO {

    private static dat.lyngby.security.daos.ISecurityDAO instance;
    private static EntityManagerFactory emf;

    public SecurityDAO(EntityManagerFactory _emf) {
        emf = _emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public UserDTO getVerifiedUser(String username, String password) throws ValidationException {
        try (EntityManager em = getEntityManager()) {
            User user = em.find(User.class, username);
            if (user == null)
                throw new EntityNotFoundException("No user found with username: " + username); //RuntimeException
            user.getRoles().size(); // force roles to be fetched from db
            if (!user.verifyPassword(password))
                throw new ValidationException("Wrong password");
            return new UserDTO(user.getUsername(), user.getRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toSet()));
        }
    }

    @Override
    public User createUser(String username, String password) {
        try (EntityManager em = getEntityManager()) {
            User userEntity = em.find(User.class, username);
            if (userEntity != null)
                throw new EntityExistsException("User with username: " + username + " already exists");
            userEntity = new User(username, password);
            em.getTransaction().begin();
            Role userRole = em.find(Role.class, "user");
           Role adminRole = em.find(Role.class, "admin");
            if (username.equals("admin")) {
                // Check if admin role exists, if not create it
                if (adminRole == null) {
                    adminRole = new Role("ADMIN");
                    em.persist(adminRole);
                }
                userEntity.addRole(adminRole);
            } else {
                // Check if user role exists, if not create it
                if (userRole == null) {
                    userRole = new Role("user");
                    em.persist(userRole);
                }
                userEntity.addRole(userRole);
            }

            em.persist(userEntity);
            em.getTransaction().commit();
            return userEntity;
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiException(400, e.getMessage());
        }
    }


    /*@Override
    public User createUserOG(String username, String password) {
        try (EntityManager em = getEntityManager()) {
            User userEntity = em.find(User.class, username);
            if (userEntity != null)
                throw new EntityExistsException("User with username: " + username + " already exists");
            userEntity = new User(username, password);
            em.getTransaction().begin();
            Role userRole = em.find(Role.class, "user");
            if (userRole == null)
                userRole = new Role("user");
            em.persist(userRole);
            userEntity.addRole(userRole);
            em.persist(userEntity);
            em.getTransaction().commit();
            return userEntity;
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiException(400, e.getMessage());
        }
    }*/

    @Override
    public User getUserByID(int id) {
        try (EntityManager em = getEntityManager()) {
            return em.find(User.class, id);
        }
    }

    @Override
    public List<User> getAll() {
        try (var em = emf.createEntityManager()) {
            return em.createQuery("SELECT u FROM User u", User.class).getResultList();
        }
    }
}

