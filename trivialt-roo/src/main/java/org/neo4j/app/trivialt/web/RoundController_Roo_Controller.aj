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
import org.neo4j.app.trivialt.model.FramedQuestion;
import org.neo4j.app.trivialt.model.Round;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect RoundController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String RoundController.create(@Valid Round round, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("round", round);
            return "api/rounds/create";
        }
        uiModel.asMap().clear();
        round.save();
        return "redirect:/api/rounds/" + encodeUrlPathSegment(round.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String RoundController.createForm(Model uiModel) {
        uiModel.addAttribute("round", new Round());
        return "api/rounds/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String RoundController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("round", Round.findRound(id));
        uiModel.addAttribute("itemId", id);
        return "api/rounds/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String RoundController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("rounds", Round.findPaged(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Round.count() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("rounds", Round.findAll());
        }
        return "api/rounds/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String RoundController.update(@Valid Round round, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("round", round);
            return "api/rounds/update";
        }
        uiModel.asMap().clear();
        round.save();
        return "redirect:/api/rounds/" + encodeUrlPathSegment(round.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String RoundController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("round", Round.findRound(id));
        return "api/rounds/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String RoundController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Round.findRound(id).delete();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/api/rounds";
    }
    
    @ModelAttribute("framedquestions")
    public Collection<FramedQuestion> RoundController.populateFramedQuestions() {
        return FramedQuestion.findAll();
    }
    
    @ModelAttribute("rounds")
    public Collection<Round> RoundController.populateRounds() {
        return Round.findAll();
    }
    
    String RoundController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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