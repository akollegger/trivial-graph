package org.neo4j.app.trivialt.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.neo4j.app.trivialt.model.Card;
import org.neo4j.app.trivialt.model.Deck;
import org.neo4j.app.trivialt.model.Team;
import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.GraphDatabaseContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RooWebScaffold(path = "api/decks", formBackingObject = Deck.class)
@RequestMapping("/api/decks")
@Controller
public class DeckController {

	@Autowired GraphDatabaseContext gdc;
	
    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Deck deck, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest,
    		@RequestParam(value = "id", required = true) Long nodeId) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("deck", deck);
            return "api/decks/update";
        }
        Node node = gdc.getNodeById(nodeId);
        deck.setPersistentState(node);
        uiModel.asMap().clear();
        deck.save();
        return "redirect:/api/decks/" + encodeUrlPathSegment(deck.getId().toString(), httpServletRequest);
    }

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
