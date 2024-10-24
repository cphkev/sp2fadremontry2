package dat.lyngby.controllers;

import dat.lyngby.daos.CardDAO;
import dat.lyngby.dtos.CardDTO;
import dat.lyngby.entities.Card;
import dat.lyngby.entities.Message;
import dat.lyngby.security.exceptions.ApiException;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CardController {
    private final CardDAO cardDAO;
    private final Logger log = LoggerFactory.getLogger(CardController.class);

    public CardController(CardDAO cardDAO) {
        this.cardDAO = cardDAO;
    }

    public void createCard(Context ctx) {
        try {
            CardDTO cardDTO = ctx.bodyAsClass(CardDTO.class);

            if (cardDTO == null) {
                ctx.status(400);
                ctx.json(new Message(400, "Invalid request"));
                return;
            }
//            Card card = cardDTO.toEntity();
            CardDTO newCard = cardDAO.create(cardDTO);

            if (newCard == null) {
                ctx.status(400);
                ctx.json(new Message(400, "Invalid card"));
                return;
            }

            ctx.res().setStatus(201);
            ctx.json(newCard);


        } catch (Exception e) {
            log.error("400 {}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    public void updateCard(Context ctx) {
        Integer id = Integer.valueOf(ctx.pathParam("id"));
        CardDTO cardDTO = ctx.bodyAsClass(CardDTO.class);

        if (cardDTO == null) {
            ctx.status(HttpStatus.BAD_REQUEST).result("Card data is required.");
            return;
        }
        CardDTO updatedCard = cardDAO.update(id, cardDTO);

        if (updatedCard == null) {
            ctx.status(HttpStatus.NOT_FOUND).result("Card not found.");
        } else {
            ctx.json(updatedCard);
        }
    }

    public void getAllCards(Context ctx) {
        List<CardDTO> cardDTOList = cardDAO.getAll();
        ctx.res().setStatus(200);
        ctx.json(cardDTOList, CardDTO.class);
    }

    public void getCardById(Context ctx){
       int id = Integer.parseInt(ctx.pathParam("id"));
       CardDTO cardDTO = cardDAO.getById(id);
       if (cardDTO != null) {
           ctx.status(200);
           ctx.json(new Message(200, "Card found"), CardDTO.class);
           ctx.json(cardDTO, CardDTO.class);
           return;
       } else {
           ctx.status(404);
           ctx.json(new Message(404, "Card not found"), CardDTO.class);
           return;
       }


    }

    public void deleteCard(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));

        CardDTO cardDTO = cardDAO.getById(id);


        if (cardDTO == null){
            ctx.status(404);
            ctx.json(new Message(404, "Card not found"), CardDTO.class);
            return;
        }
        try {
            cardDAO.delete(id);
            ctx.status(204);
        }catch (Exception e){
            ctx.status(500);
            ctx.json(new Message(500, "Internal server error"), CardDTO.class);
        }


    }



    public void getByMinAndMaxPrice(Context ctx) {
        String minPriceParam = ctx.queryParam("minPrice");
        String maxPriceParam = ctx.queryParam("maxPrice");

        if (minPriceParam == null || maxPriceParam == null) {
            ctx.status(HttpStatus.BAD_REQUEST).result("minPrice and maxPrice are required.");
            return;
        }
        try {
            int minPrice = Integer.parseInt(minPriceParam);
            int maxPrice = Integer.parseInt(maxPriceParam);
            List<Card> cards = cardDAO.getByMinAndMaxPrice(minPrice, maxPrice);

            if (cards.isEmpty()) {
                ctx.status(HttpStatus.NOT_FOUND).result("No cards found.");
            } else {
                ctx.json(CardDTO.toCardDTOList(cards));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("minPrice and maxPrice must be integers.");
        }
    }

    public void getByMaxPrice(Context ctx) {
        String maxPriceParam = ctx.queryParam("maxPrice");

        if (maxPriceParam == null) {
            ctx.status(HttpStatus.BAD_REQUEST).result("maxPrice is required.");
            return;
        }
        try {
            int maxPrice = Integer.parseInt(maxPriceParam);
            List<Card> cards = cardDAO.getByMaxPrice(maxPrice);

            if (cards.isEmpty()) {
                ctx.status(HttpStatus.NOT_FOUND).result("No cards found.");
            } else {
                ctx.json(CardDTO.toCardDTOList(cards));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("maxPrice must be an integer.");
        }
    }

    public void getByMinPrice(Context ctx) {
        String minPriceParam = ctx.queryParam("minPrice");

        if (minPriceParam == null) {
            ctx.status(HttpStatus.BAD_REQUEST).result("minPrice is required.");
            return;
        }
        try {
            int minPrice = Integer.parseInt(minPriceParam);
            List<Card> cards = cardDAO.getByMinPrice(minPrice);

            if (cards.isEmpty()) {
                ctx.status(HttpStatus.NOT_FOUND).result("No cards found.");
            } else {
                ctx.json(CardDTO.toCardDTOList(cards));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("minPrice must be an integer.");
        }
    }

    public void getByMinAttack(Context ctx) {
        String minAttackParam = ctx.queryParam("minAttack");

        if (minAttackParam == null) {
            ctx.status(HttpStatus.BAD_REQUEST).result("minAttack is required.");
            return;
        }
        try {
            int minAttack = Integer.parseInt(minAttackParam);
            List<Card> cards = cardDAO.getByMinAttack(minAttack);

            if (cards.isEmpty()) {
                ctx.status(HttpStatus.NOT_FOUND).result("No cards found.");
            } else {
                ctx.json(CardDTO.toCardDTOList(cards));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("minAttack must be an integer.");
        }
    }

    public void getByMaxAttack(Context ctx) {
        String maxAttackParam = ctx.queryParam("maxAttack");

        if (maxAttackParam == null) {
            ctx.status(HttpStatus.BAD_REQUEST).result("maxAttack is required.");
            return;
        }
        try {
            int maxAttack = Integer.parseInt(maxAttackParam);
            List<Card> cards = cardDAO.getByMaxAttack(maxAttack);

            if (cards.isEmpty()) {
                ctx.status(HttpStatus.NOT_FOUND).result("No cards found.");
            } else {
                ctx.json(CardDTO.toCardDTOList(cards));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("maxAttack must be an integer.");
        }
    }

    public void getByMinAndMaxAttack(Context ctx) {
        String minAttackParam = ctx.queryParam("minAttack");
        String maxAttackParam = ctx.queryParam("maxAttack");

        if (minAttackParam == null || maxAttackParam == null) {
            ctx.status(HttpStatus.BAD_REQUEST).result("minAttack and maxAttack are required.");
            return;
        }
        try {
            int minAttack = Integer.parseInt(minAttackParam);
            int maxAttack = Integer.parseInt(maxAttackParam);
            List<Card> cards = cardDAO.getByMinAndMaxAttack(minAttack, maxAttack);

            if (cards.isEmpty()) {
                ctx.status(HttpStatus.NOT_FOUND).result("No cards found.");
            } else {
                ctx.json(CardDTO.toCardDTOList(cards));
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("minAttack and maxAttack must be integers.");
        }
    }
}
