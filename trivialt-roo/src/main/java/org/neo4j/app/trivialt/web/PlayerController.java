package org.neo4j.app.trivialt.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.neo4j.app.trivialt.model.Player;
import org.neo4j.app.trivialt.model.Role;
import org.neo4j.app.trivialt.model.Team;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RooWebScaffold(path = "api/players", formBackingObject = Player.class)
@RequestMapping("/api/players")
@Controller
public class PlayerController {


}
