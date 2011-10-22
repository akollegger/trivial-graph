package org.neo4j.app.trivialt.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.neo4j.app.trivialt.model.Match;
import org.neo4j.app.trivialt.model.Round;
import org.neo4j.app.trivialt.service.Score;
import org.neo4j.app.trivialt.service.TrivialtMatchPlay;
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

import flexjson.JSONSerializer;

@RooWebScaffold(path = "api/matches", formBackingObject = Match.class)
@RequestMapping("/api/matches")
@Controller
public class MatchController {

	@Autowired GraphDatabaseContext gdc;

	@Autowired private TrivialtMatchPlay trivialt;
	    
    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Match match, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest,
    		@RequestParam(value = "id", required = true) Long nodeId) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("match", match);
            return "api/matches/update";
        }
        Node node = gdc.getNodeById(nodeId);
        match.setPersistentState(node);
        uiModel.asMap().clear();
        match.save();
        return "redirect:/api/matches/" + encodeUrlPathSegment(match.getId().toString(), httpServletRequest);
    }
	
    @RequestMapping(value = "/{id}/rounds", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> getMatchRounds(@PathVariable("id") Long id) {
        Match match = Match.findMatch(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (match == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(Round.toJsonArray(match.getRounds()), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/scores", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> getMatchScores(@PathVariable("id") Long id) {
        Match match = Match.findMatch(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (match == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
//        Collection<Score> scores = trivialt.getScores(match);
        List<Score> scores = new ArrayList<Score>();
        scores.add(new Score(1, "me", 10));
        scores.add(new Score(2, "you", 500));
        

        return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").serialize(scores), headers, HttpStatus.OK);
    }
}
