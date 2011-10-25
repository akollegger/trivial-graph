package org.neo4j.app.trivialt.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.neo4j.app.trivialt.model.Answer;
import org.neo4j.app.trivialt.model.FramedQuestion;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RooWebScaffold(path = "api/framedquestions", formBackingObject = FramedQuestion.class)
@RequestMapping("/api/framedquestions")
@Controller
public class FramedQuestionController {

	@Autowired GraphDatabaseContext gdc;

    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid FramedQuestion framedQuestion, BindingResult bindingResult, Model uiModel, 
    		HttpServletRequest httpServletRequest,
    		@RequestParam(value = "id", required = true) Long nodeId) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("framedQuestion", framedQuestion);
            return "api/framedquestions/update";
        }
        Node node = gdc.getNodeById(nodeId);
        framedQuestion.setPersistentState(node);
        uiModel.asMap().clear();
        framedQuestion.save();
        return "redirect:/api/framedquestions/" + encodeUrlPathSegment(framedQuestion.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}/answers/{answer}", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> addRoundToMatch(@PathVariable("id") Long frameId, @PathVariable("answer") Long answerId, @RequestBody String json) {
        FramedQuestion frame = FramedQuestion.findFramedQuestion(frameId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (frame == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        Answer possibleAnswer = Answer.findAnswer(answerId);
        if (possibleAnswer == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        frame.add(possibleAnswer);
        frame.save();
        possibleAnswer.save();
        return new ResponseEntity<String>(possibleAnswer.toJson(), headers, HttpStatus.CREATED);
    }

}
