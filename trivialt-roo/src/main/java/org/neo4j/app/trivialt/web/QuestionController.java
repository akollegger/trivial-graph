package org.neo4j.app.trivialt.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.neo4j.app.trivialt.model.Question;
import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.GraphDatabaseContext;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RooWebScaffold(path = "api/questions", formBackingObject = Question.class)
@RequestMapping("/api/questions")
@Controller
public class QuestionController {

	@Autowired GraphDatabaseContext gdc;

    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Question question, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest,
    		@RequestParam(value = "id", required = true) Long nodeId) {

        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("question", question);
            return "api/questions/update";
        }
        Node node = gdc.getNodeById(nodeId);
        question.setPersistentState(node);
        uiModel.asMap().clear();
        question.save();
        return "redirect:/api/questions/" + encodeUrlPathSegment(question.getId().toString(), httpServletRequest);
    }

}
