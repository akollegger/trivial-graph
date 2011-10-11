package edu.mit.simile.rdfizer.pom2rdf;

import java.io.File;

import org.apache.velocity.VelocityContext;

public class FileSystemConverter extends AbstractConverter {

	public FileSystemConverter (String local_path, File file, String format) {
		super(local_path, file, format);
	}

	/* (non-Javadoc)
	 * @see edu.mit.simile.rdfizer.pom2rdf.AbstractConverter#getVelocityContext()
	 */
	public VelocityContext getVelocityContext() throws Exception {
		VelocityContext context = new VelocityContext();
		ISO8601Formatter date = new ISO8601Formatter();

		context.put("local_path", getLocalPath());
		context.put("repository_path", getFile().getAbsolutePath().substring(getLocalPath().length()) + "/");
		context.put("directory", getFile());
		context.put("date", date);

		return context;
	}

	/* (non-Javadoc)
	 * @see edu.mit.simile.rdfizer.pom2rdf.AbstractConverter#getTemplate(java.lang.String)
	 */
	public String getTemplatePrefix() {
		return "fs";
	}
}