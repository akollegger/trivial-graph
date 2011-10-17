package org.neo4j.app.trivialt.web;

import org.neo4j.app.trivialt.model.Team;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "api/teams", formBackingObject = Team.class)
@RequestMapping("/api/teams")
@Controller
public class TeamController {
}
