package org.neo4j.app.trivialt.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.neo4j.app.trivialt.model.Category;
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

@RooWebScaffold(path = "api/categories", formBackingObject = Category.class)
@RequestMapping("/api/categories")
@Controller
public class CategoryController {

	@Autowired GraphDatabaseContext gdc;
	
    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Category category, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest,
    		@RequestParam(value = "id", required = true) Long nodeId) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("category", category);
            return "api/categories/update";
        }
        Map<String, Object> uiModelMap = uiModel.asMap();
        for (String key : uiModelMap.keySet()) {
        	Object val = uiModelMap.get(key);
        	System.out.println(key + " "  + val + " <" + val.getClass() + ">");
        }
        Node node = gdc.getNodeById(nodeId);
        category.setPersistentState(node);
        uiModel.asMap().clear();
        category.save();
        return "redirect:/api/categories/" + encodeUrlPathSegment(category.getId().toString(), httpServletRequest);
    }


}
