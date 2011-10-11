package edu.mit.simile.rdfizer.pom2rdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.apache.velocity.VelocityContext;

public class MetadataConverter extends AbstractConverter {

	public MetadataConverter (String local_path, File file, String format) {
		super(local_path, file, format);
	}

	/* (non-Javadoc)
	 * @see edu.mit.simile.rdfizer.pom2rdf.AbstractConverter#getVelocityContext()
	 */
	public VelocityContext getVelocityContext() throws MalformedURLException, IOException {
		String groupId = null;
		String artifactId = null;
		String currentVersion = null;
		String lastUpdated = null;
		ArrayList versions = new ArrayList();
		boolean current = true;
		VelocityContext context = new VelocityContext();
		
		context.put("local_path", getLocalPath());
		context.put("repository_path", getFile().getParent().substring(getLocalPath().length()) + "/");
		context.put("metadata_filename", getFile().getName());
		context.put("metadata", getContent());
		
		// TODO: here use an XML parser!!! -- castagna
		BufferedReader in = new BufferedReader(new FileReader(getFile()));
		String str;
		while ((str = in.readLine()) != null) {
			if (str.contains("<groupId>")) {
				groupId = StringUtils.substring(str, "<groupId>", "</groupId>");
			} else if (str.contains("<artifactId>")) {
				artifactId = StringUtils.substring(str, "<artifactId>", "</artifactId>");
			} else if (str.contains("<version>")) {
				String version = StringUtils.substring(str, "<version>", "</version>");
				if (current) {
					currentVersion = version;
					current = false;
				} else {
					versions.add(version);
				}
			} else if (str.contains("<lastUpdated>")) {
				lastUpdated = StringUtils.substring(str, "<lastUpdated>", "</lastUpdated>");
			}
		}
		in.close();

		context.put("group_id", groupId);
		context.put("artifact_id", artifactId);
		context.put("version", currentVersion);
		context.put("last_updated", lastUpdated);
		context.put("versions", versions);
		
		return context;
	}

	/* (non-Javadoc)
	 * @see edu.mit.simile.rdfizer.pom2rdf.AbstractConverter#getTemplate(java.lang.String)
	 */
	public String getTemplatePrefix() {
		return "metadata";
	}

}