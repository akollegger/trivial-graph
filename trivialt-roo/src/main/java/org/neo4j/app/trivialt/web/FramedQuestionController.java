package org.neo4j.app.trivialt.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    
}
