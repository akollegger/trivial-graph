package org.neo4j.app.trivialt.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.neo4j.app.trivialt.model.Media;
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

@RooWebScaffold(path = "api/medias", formBackingObject = Media.class)
@RequestMapping("/api/medias")
@Controller
public class MediaController {

	@Autowired GraphDatabaseContext gdc;

    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Media media, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest,
    		@RequestParam(value = "id", required = true) Long nodeId) {

        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("media", media);
            return "api/medias/update";
        }
        Node node = gdc.getNodeById(nodeId);
        media.setPersistentState(node);
        uiModel.asMap().clear();
        media.save();
        return "redirect:/api/medias/" + encodeUrlPathSegment(media.getId().toString(), httpServletRequest);
    }
}
