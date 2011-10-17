package org.neo4j.app.trivialt.web;

import org.neo4j.app.trivialt.model.Deck;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "decks", formBackingObject = Deck.class)
@RequestMapping("/decks")
@Controller
public class DeckController {
}
