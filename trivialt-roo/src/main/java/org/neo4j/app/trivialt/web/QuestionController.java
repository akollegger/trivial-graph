package org.neo4j.app.trivialt.web;

import org.neo4j.app.trivialt.model.Question;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "api/questions", formBackingObject = Question.class)
@RequestMapping("/api/questions")
@Controller
public class QuestionController {
}
