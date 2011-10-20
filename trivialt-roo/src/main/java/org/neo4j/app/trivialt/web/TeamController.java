package org.neo4j.app.trivialt.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.neo4j.app.trivialt.model.Deck;
import org.neo4j.app.trivialt.model.Match;
import org.neo4j.app.trivialt.model.Team;
import org.neo4j.app.trivialt.repository.MatchRepository;
import org.neo4j.app.trivialt.repository.TeamRepository;
import org.neo4j.app.trivialt.service.AvailabilityStatus;
import org.neo4j.app.trivialt.service.TrivialtMatchPlay;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.helpers.collection.ClosableIterable;
import org.neo4j.kernel.Traversal;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import flexjson.JSONSerializer;

@RooWebScaffold(path = "api/teams", formBackingObject = Team.class)
@RequestMapping("/api/teams")
@Controller
public class TeamController {

	@Autowired GraphDatabaseContext gdc;
	
	@Autowired private TrivialtMatchPlay trivialt;
	
    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Team team, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest,
    		@RequestParam(value = "id", required = true) Long nodeId) {

        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("team", team);
            return "api/teams/update";
        }
        Node node = gdc.getNodeById(nodeId);
        team.setPersistentState(node);
        uiModel.asMap().clear();
        team.save();
        return "redirect:/api/teams/" + encodeUrlPathSegment(team.getId().toString(), httpServletRequest);
    }


    @RequestMapping(value="/availability", method=RequestMethod.GET)
    public @ResponseBody AvailabilityStatus getAvailability(@RequestParam String name) {
    	if (trivialt.teamNameExists(name))
    		return AvailabilityStatus.notAvailable(name);
    	else 
    		return AvailabilityStatus.available(name);
    }
    
    @RequestMapping(value = "/{id}/decks", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> getTeamDecks(@PathVariable("id") Long id) {
        Team team = Team.findTeam(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (team == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(Deck.toJsonArray(team.getDecks()), headers, HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "/{id}/decks/current", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> getTeamCurrentDeck(@PathVariable("id") Long id) {
        Team team = Team.findTeam(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if ((team == null) || (team.getCurrentDeck() == null)) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        Deck currentDeck = team.getCurrentDeck();
        return new ResponseEntity<String>(currentDeck.toJson(), headers, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        Team requestedTeam = Team.fromJsonToTeam(json);
    	if (trivialt.teamNameExists(requestedTeam.getName())) {
            return new ResponseEntity<String>(AvailabilityStatus.notAvailable(requestedTeam.getName()).toJson(), HttpStatus.FORBIDDEN);
        }
    	trivialt.initiate(requestedTeam);
        HttpHeaders headers= new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(requestedTeam.toJson(), headers, HttpStatus.CREATED);
    }

}
