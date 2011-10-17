package org.neo4j.app.trivialt.web;

import org.neo4j.app.trivialt.model.Media;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "medias", formBackingObject = Media.class)
@RequestMapping("/medias")
@Controller
public class MediaController {
}
