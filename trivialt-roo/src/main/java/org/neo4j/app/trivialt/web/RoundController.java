package org.neo4j.app.trivialt.web;

import org.neo4j.app.trivialt.model.Round;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "api/rounds", formBackingObject = Round.class)
@RequestMapping("/api/rounds")
@Controller
public class RoundController {
}
