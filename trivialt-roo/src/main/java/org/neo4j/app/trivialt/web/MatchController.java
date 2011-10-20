package org.neo4j.app.trivialt.web;

import java.util.Collection;

import org.neo4j.app.trivialt.model.Card;
import org.neo4j.app.trivialt.model.Deck;
import org.neo4j.app.trivialt.model.Match;
import org.neo4j.app.trivialt.model.Round;
import org.neo4j.app.trivialt.service.Score;
import org.neo4j.app.trivialt.service.TrivialtMatchPlay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import flexjson.JSONSerializer;

@RooWebScaffold(path = "api/matches", formBackingObject = Match.class)
@RequestMapping("/api/matches")
@Controller
public class MatchController {

	@Autowired private TrivialtMatchPlay trivialt;
	
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
        Collection<Score> scores = trivialt.getScores(match);

        return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").serialize(scores), headers, HttpStatus.OK);
    }
}
