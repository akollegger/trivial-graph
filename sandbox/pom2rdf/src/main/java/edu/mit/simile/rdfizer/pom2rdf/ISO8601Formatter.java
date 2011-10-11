package edu.mit.simile.rdfizer.pom2rdf;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ISO8601Formatter {

	private final String format = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	private SimpleDateFormat formatter = null;

	public ISO8601Formatter() {
		formatter = new SimpleDateFormat(format);
	}

	public String format(long l) {
		Date date = new Date(l);
		return formatter.format(date);
	}

}