package org.neo4j.app.trivialt.service;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooToString
@RooJavaBean
@RooJson
public class Trivia {

	private String category;
	private String question;
	private String answer;
	
}
