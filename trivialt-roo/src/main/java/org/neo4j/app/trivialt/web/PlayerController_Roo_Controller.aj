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
import org.neo4j.app.trivialt.model.Player;
import org.neo4j.app.trivialt.model.Team;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect PlayerController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String PlayerController.create(@Valid Player player, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("player", player);
            return "api/players/create";
        }
        uiModel.asMap().clear();
        player.save();
        return "redirect:/api/players/" + encodeUrlPathSegment(player.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String PlayerController.createForm(Model uiModel) {
        uiModel.addAttribute("player", new Player());
        return "api/players/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String PlayerController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("player", Player.findPlayer(id));
        uiModel.addAttribute("itemId", id);
        return "api/players/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String PlayerController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("players", Player.findPaged(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Player.count() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("players", Player.findAll());
        }
        return "api/players/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String PlayerController.update(@Valid Player player, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("player", player);
            return "api/players/update";
        }
        uiModel.asMap().clear();
        player.save();
        return "redirect:/api/players/" + encodeUrlPathSegment(player.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String PlayerController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("player", Player.findPlayer(id));
        return "api/players/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String PlayerController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Player.findPlayer(id).delete();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/api/players";
    }
    
    @ModelAttribute("players")
    public Collection<Player> PlayerController.populatePlayers() {
        return Player.findAll();
    }
    
    @ModelAttribute("teams")
    public Collection<Team> PlayerController.populateTeams() {
        return Team.findAll();
    }
    
    String PlayerController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
