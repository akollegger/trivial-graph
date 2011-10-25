package org.neo4j.app.trivialt.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.neo4j.app.trivialt.model.Answer;
import org.neo4j.app.trivialt.model.Category;
import org.neo4j.app.trivialt.model.Match;
import org.neo4j.app.trivialt.model.Question;
import org.neo4j.app.trivialt.service.Indicator;
import org.neo4j.app.trivialt.service.Trivia;
import org.neo4j.app.trivialt.service.TrivialtMatchPlay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/api/trivialt")
@Controller
public class TrivialtController {
	
	@Autowired TrivialtMatchPlay trivialt;

    @RequestMapping(method = RequestMethod.GET)
    public void get(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    }

    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/text; charset=utf-8");
        return new ResponseEntity<String>("{ \"trivialt\": true }", headers, HttpStatus.OK);
    }
    
    /**
     * Bulk import of Questions, Answers and Categories, in the form of plain text Trivia.
     * 
     * @param json
     * @return
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        for (Trivia trivia: Trivia.fromJsonArrayToTrivias(json)) {
        	Category category = trivialt.uniqueCategory(trivia.getCategory());
        	Question question = trivialt.uniqueQuestion(trivia.getQuestion());
        	Answer answer = trivialt.uniqueAnswer(trivia.getAnswer());
        	if (question.getCategory() == null) {
        		question.setCategory(category);
        	}
        	if (question.getAnswer() == null) {
        		question.setAnswer(answer);
        	}
        	category.save();
        	question.save();
        	answer.save();
        }
        HttpHeaders headers= new HttpHeaders();
        headers.add("Content-Type", "application/text");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "{id}")
    public void post(@PathVariable Long id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    }

    @RequestMapping
    public String index() {
        return "trivialt/index";
    }
}
