package org.neo4j.app.trivialt.web;

import org.neo4j.app.trivialt.model.Card;
import org.neo4j.app.trivialt.model.Deck;
import org.neo4j.app.trivialt.model.Team;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RooWebScaffold(path = "api/decks", formBackingObject = Deck.class)
@RequestMapping("/api/decks")
@Controller
public class DeckController {

    @RequestMapping(value = "/{id}/cards", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> getDeckCards(@PathVariable("id") Long id) {
        Deck deck = Deck.findDeck(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (deck == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(Card.toJsonArray(deck.getCards()), headers, HttpStatus.OK);
    }
}
