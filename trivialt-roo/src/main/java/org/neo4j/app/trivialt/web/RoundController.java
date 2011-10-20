package org.neo4j.app.trivialt.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.neo4j.app.trivialt.model.FramedQuestion;
import org.neo4j.app.trivialt.model.Match;
import org.neo4j.app.trivialt.model.Round;
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

@RooWebScaffold(path = "api/rounds", formBackingObject = Round.class)
@RequestMapping("/api/rounds")
@Controller
public class RoundController {

	@Autowired GraphDatabaseContext gdc;
	
    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Round round, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest,
    		@RequestParam(value = "id", required = true) Long nodeId) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("round", round);
            return "api/rounds/update";
        }
        Node node = gdc.getNodeById(nodeId);
        round.setPersistentState(node);
        uiModel.asMap().clear();
        round.save();
        return "redirect:/api/rounds/" + encodeUrlPathSegment(round.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}/frames", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> getMatchRounds(@PathVariable("id") Long id) {
        Round round = Round.findRound(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (round == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(FramedQuestion.toJsonArray(round.getFramedQuestions()), headers, HttpStatus.OK);
    }

}
