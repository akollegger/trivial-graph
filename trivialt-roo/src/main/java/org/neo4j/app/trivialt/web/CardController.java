package org.neo4j.app.trivialt.web;

import org.neo4j.app.trivialt.model.Card;
import org.neo4j.app.trivialt.model.Proposal;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RooWebScaffold(path = "api/cards", formBackingObject = Card.class)
@RequestMapping("/api/cards")
@Controller
public class CardController {
    
    @RequestMapping(value = "/{id}/proposals", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> getCardProposals(@PathVariable("id") Long id) {
        Card card = Card.findCard(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (card == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(Proposal.toJsonArray(card.getProposals()), headers, HttpStatus.OK);
    }
    
}
