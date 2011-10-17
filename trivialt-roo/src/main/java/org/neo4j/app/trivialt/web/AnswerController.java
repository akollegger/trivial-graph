package org.neo4j.app.trivialt.web;

import org.neo4j.app.trivialt.model.Answer;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "answers", formBackingObject = Answer.class)
@RequestMapping("/answers")
@Controller
public class AnswerController {
}
