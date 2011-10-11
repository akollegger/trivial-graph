package edu.mit.simile.rdfizer.pom2rdf;

public class StringUtils {

	public static String substring (String str, String start, String end) {
		if (str == null) {
			return null;
		}
		int startIndex = str.indexOf(start);
		int endIndex = str.indexOf(end, startIndex + start.length());
		if ((startIndex >= 0) && (endIndex > startIndex)) {
			return str.substring(startIndex + start.length(), endIndex);
		} else {
			return null;
		}
	}
	
	public static String escape (String str) {
		return str.replace("\\", "\\\\").replace("\"", "\\\"");
	}

}