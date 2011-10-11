package edu.mit.simile.rdfizer.pom2rdf;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Encoder {

	public String encode(String url) throws UnsupportedEncodingException {
		if ((url.contains("$")) || (url.contains(" ")) || (url.contains("[")) || (url.contains("]")) || (url.contains("(")) || (url.contains(")")) ) {
			return URLEncoder.encode(url, "UTF-8").replaceAll("%3A", ":");
		} else {
			return url;
		}
	}

}