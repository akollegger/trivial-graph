// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.neo4j.app.trivialt.web;

import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.neo4j.app.trivialt.model.Answer;
import org.neo4j.app.trivialt.model.Category;
import org.neo4j.app.trivialt.model.Question;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect QuestionController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String QuestionController.create(@Valid Question question, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("question", question);
            return "api/questions/create";
        }
        uiModel.asMap().clear();
        question.save();
        return "redirect:/api/questions/" + encodeUrlPathSegment(question.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String QuestionController.createForm(Model uiModel) {
        uiModel.addAttribute("question", new Question());
        return "api/questions/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String QuestionController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("question", Question.findQuestion(id));
        uiModel.addAttribute("itemId", id);
        return "api/questions/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String QuestionController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("questions", Question.findPaged(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Question.count() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("questions", Question.findAll());
        }
        return "api/questions/list";
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String QuestionController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("question", Question.findQuestion(id));
        return "api/questions/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String QuestionController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Question.findQuestion(id).delete();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/api/questions";
    }
    
    @ModelAttribute("answers")
    public Collection<Answer> QuestionController.populateAnswers() {
        return Answer.findAll();
    }
    
    @ModelAttribute("categorys")
    public Collection<Category> QuestionController.populateCategorys() {
        return Category.findAll();
    }
    
    @ModelAttribute("questions")
    public Collection<Question> QuestionController.populateQuestions() {
        return Question.findAll();
    }
    
    String QuestionController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        }
        catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
