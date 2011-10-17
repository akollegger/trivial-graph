package org.neo4j.app.trivialt.web;

import org.neo4j.app.trivialt.model.Match;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "api/matches", formBackingObject = Match.class)
@RequestMapping("/api/matches")
@Controller
public class MatchController {
}
