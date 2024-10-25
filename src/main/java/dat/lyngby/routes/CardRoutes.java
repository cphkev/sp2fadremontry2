package dat.lyngby.routes;


import dat.lyngby.config.HibernateConfig;
import dat.lyngby.controllers.CardController;
import dat.lyngby.daos.CardDAO;
import dat.lyngby.security.controllers.SecurityController;
import dat.lyngby.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;
public class CardRoutes {

    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    private final CardDAO cardDAO = new CardDAO(emf);

    private final CardController cardController = new CardController(cardDAO);

    SecurityController securityController = SecurityController.getInstance();

    protected EndpointGroup getRoutes(){
        return () -> {
            before(securityController.authenticate());
            post("/create", cardController::createCard, Role.ANYONE);
            get("/getallcards", cardController::getAllCards,Role.ANYONE);
            get("/{id}", cardController::getCardById, Role.ANYONE);
            put("/{id}", cardController::updateCard, Role.ADMIN);
            delete("/{id}", cardController::deleteCard, Role.ADMIN);//OBS VIRKER IKKE!!!!

            get("/price/range", cardController::getByMinAndMaxPrice, Role.ANYONE);
            get("/price/max", cardController::getByMaxPrice, Role.ANYONE);
            get("/price/min", cardController::getByMinPrice, Role.ANYONE);

            get("/attack/range", cardController::getByMinAndMaxAttack, Role.ANYONE);
            get("/attack/max", cardController::getByMaxAttack, Role.ANYONE);
            get("/attack/min", cardController::getByMinAttack, Role.ANYONE);

            put("/addcardtopack/{cardId}/{packId}", cardController::addCardToPack, Role.ANYONE);

        };
    }


}
