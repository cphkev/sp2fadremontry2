package dat.lyngby.routes;

import dat.lyngby.config.HibernateConfig;
import dat.lyngby.controllers.PackController;
import dat.lyngby.daos.CardDAO;
import dat.lyngby.daos.PackDAO;
import dat.lyngby.security.controllers.SecurityController;
import dat.lyngby.security.enums.Role;
import jakarta.persistence.EntityManagerFactory;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou
 */
public class PackRoutes {

    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final PackDAO packDAO = new PackDAO(emf);
    private final CardDAO cardDAO = new CardDAO(emf);
    private final PackController packController = new PackController(packDAO, cardDAO);
    SecurityController securityController = SecurityController.getInstance();

    public EndpointGroup getRoutes() {
        return () -> {

            get("/", packController::getAllPacks, Role.ANYONE);
            get("/{id}", packController::getPackById, Role.ANYONE);
            before(securityController.authenticate()); // check if there is a valid token in the header
            post("/", packController::createPack, Role.ADMIN);
            put("/{id}", packController::updatePack, Role.ADMIN);
            delete("/{id}", packController::deletePack, Role.ADMIN);
        };
    }


}
