package edu.mit.simile.rdfizer.pom2rdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.velocity.VelocityContext;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

public class POMConverter extends AbstractConverter {

	private Logger logger = Logger.getLogger(POMConverter.class);
	private Model model = null;

	public POMConverter (String local_path, File file, String format) {
		super(local_path, file, format);

		MavenXpp3Reader reader = new MavenXpp3Reader();
		try {
			model = reader.read(new FileReader(getFile()));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (XmlPullParserException e) {
			logger.error(e.getMessage(), e);
			addErrorMessage(e.getMessage());
		}
	}

	private String getHash(String filename) {
		String hash = null;
		BufferedReader in = null;
		try {
			StringBuffer content = new StringBuffer();
			in = new BufferedReader(new FileReader(filename));
			String str;
			while ((str = in.readLine()) != null) {
				content.append(str).append("\n");
			}
			
			StringTokenizer st = new StringTokenizer(content.toString());
			if (st.hasMoreTokens()) {
				hash = st.nextToken();
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

		return StringEscapeUtils.escapeJava(hash);
	}

	/* (non-Javadoc)
	 * @see edu.mit.simile.rdfizer.pom2rdf.AbstractConverter#getVelocityContext()
	 */
	public VelocityContext getVelocityContext() throws Exception {
		VelocityContext context = new VelocityContext();
		context.put("local_path", getLocalPath());
		context.put("repository_path", getFile().getParent().substring(getLocalPath().length()) + "/");
		context.put("pom_path", getFile().getAbsolutePath().substring(getLocalPath().length()));
		context.put("pom_filename", getFile().getName());
		context.put("pom_md5", getHash(getFile().getAbsolutePath() + ".md5"));
		context.put("pom_sha1", getHash(getFile().getAbsolutePath() + ".sha1"));
		context.put("artifact_path", getFile().getAbsolutePath().replaceAll(".pom", ".jar").substring(getLocalPath().length()));
		context.put("artifact_filename", getFile().getName().replaceAll(".pom", ".jar"));
		context.put("artifact_md5", getHash(getFile().getAbsolutePath().replaceAll(".pom", ".jar") + ".md5")); // can we do something beetter here? -- castagna
		context.put("artifact_sha1", getHash(getFile().getAbsolutePath().replaceAll(".pom", ".jar") + ".sha1"));
		context.put("model", model);
		context.put("pom", getContent());
		if (model != null) {
			context.put("dependencies", model.getDependencies());
		}
		
		return context;
	}

	/* (non-Javadoc)
	 * @see edu.mit.simile.rdfizer.pom2rdf.AbstractConverter#getTemplate(java.lang.String)
	 */
	public String getTemplatePrefix() {
		return "pom";
	}

}