package org.neo4j.app.trivialt.web;

import org.neo4j.app.trivialt.model.FramedQuestion;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "api/framedquestions", formBackingObject = FramedQuestion.class)
@RequestMapping("/api/framedquestions")
@Controller
public class FramedQuestionController {
}
