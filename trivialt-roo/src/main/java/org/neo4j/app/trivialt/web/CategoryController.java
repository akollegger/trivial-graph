package org.neo4j.app.trivialt.web;

import org.neo4j.app.trivialt.model.Category;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "categorys", formBackingObject = Category.class)
@RequestMapping("/categorys")
@Controller
public class CategoryController {
}
