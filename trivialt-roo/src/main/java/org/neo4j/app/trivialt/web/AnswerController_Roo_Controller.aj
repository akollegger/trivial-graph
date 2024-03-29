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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect AnswerController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String AnswerController.create(@Valid Answer answer, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("answer", answer);
            return "api/answers/create";
        }
        uiModel.asMap().clear();
        answer.save();
        return "redirect:/api/answers/" + encodeUrlPathSegment(answer.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String AnswerController.createForm(Model uiModel) {
        uiModel.addAttribute("answer", new Answer());
        return "api/answers/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String AnswerController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("answer", Answer.findAnswer(id));
        uiModel.addAttribute("itemId", id);
        return "api/answers/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String AnswerController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("answers", Answer.findPaged(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Answer.count() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("answers", Answer.findAll());
        }
        return "api/answers/list";
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String AnswerController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("answer", Answer.findAnswer(id));
        return "api/answers/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String AnswerController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Answer.findAnswer(id).delete();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/api/answers";
    }
    
    @ModelAttribute("answers")
    public Collection<Answer> AnswerController.populateAnswers() {
        return Answer.findAll();
    }
    
    String AnswerController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
