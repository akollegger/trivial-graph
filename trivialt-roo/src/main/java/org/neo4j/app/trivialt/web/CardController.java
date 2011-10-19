package org.neo4j.app.trivialt.web;

import org.neo4j.app.trivialt.model.Card;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "api/cards", formBackingObject = Card.class)
@RequestMapping("/api/cards")
@Controller
public class CardController {
}