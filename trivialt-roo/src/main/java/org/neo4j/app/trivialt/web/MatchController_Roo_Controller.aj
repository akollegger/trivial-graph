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
import org.neo4j.app.trivialt.model.Match;
import org.neo4j.app.trivialt.model.Player;
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

privileged aspect MatchController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String MatchController.create(@Valid Match match, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("match", match);
            return "api/matches/create";
        }
        uiModel.asMap().clear();
        match.save();
        return "redirect:/api/matches/" + encodeUrlPathSegment(match.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String MatchController.createForm(Model uiModel) {
        uiModel.addAttribute("match", new Match());
        return "api/matches/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String MatchController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("match", Match.findMatch(id));
        uiModel.addAttribute("itemId", id);
        return "api/matches/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String MatchController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("matches", Match.findPaged(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Match.count() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("matches", Match.findAll());
        }
        return "api/matches/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String MatchController.update(@Valid Match match, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("match", match);
            return "api/matches/update";
        }
        uiModel.asMap().clear();
        match.save();
        return "redirect:/api/matches/" + encodeUrlPathSegment(match.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String MatchController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("match", Match.findMatch(id));
        return "api/matches/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String MatchController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Match.findMatch(id).delete();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/api/matches";
    }
    
    @ModelAttribute("matches")
    public Collection<Match> MatchController.populateMatches() {
        return Match.findAll();
    }
    
    @ModelAttribute("players")
    public Collection<Player> MatchController.populatePlayers() {
        return Player.findAll();
    }
    
    @ModelAttribute("rounds")
    public Collection<Round> MatchController.populateRounds() {
        return Round.findAll();
    }
    
    String MatchController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
