package org.neo4j.app.trivialt.web;

import org.neo4j.app.trivialt.model.Deck;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "api/decks", formBackingObject = Deck.class)
@RequestMapping("/api/decks")
@Controller
public class DeckController {
}
