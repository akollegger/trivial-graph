package edu.mit.simile.rdfizer.pom2rdf;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

public class MavenRepositoryConverter extends AbstractConverter {

	private Logger logger = Logger.getLogger(MavenRepositoryConverter.class);

	public MavenRepositoryConverter (String local_path, File path, String format) {
		super(local_path, path, format);
	}

	private void recurse (Writer out, File path) {
		File[] files = path.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory()) {
				processDirectory (out, file);
				recurse (out, file);
			} else {
				process(out, file);			
			}
		}
	}
	
	private void processDirectory (Writer out, File file) {
		logger.info("Processing " + file.getAbsolutePath() + " directory...");
		
		FileSystemConverter fs2rdf = new FileSystemConverter(getFile().getAbsolutePath(), file, getFormat());
		fs2rdf.convert(out);
	}
	
	private void process (Writer out, File file) {
		logger.debug("Processing " + file.getName() + " file...");
		
		String filename = file.getName();
		// System.out.println("Processing " + file.getAbsolutePath());
		if (filename.endsWith(".sha1")) {
			// this is actually managed by POMConverter, I do not like too much this -- castagna
		} else if (filename.endsWith(".md5")) {
			// this is actually managed by POMConverter, I do not like too much this -- castagna
		} else if (filename.endsWith(".jar")) {
			// TODO: use java2rdf here! -- castagna
		} else if (filename.endsWith(".pom")) {
			POMConverter pom2rdf = new POMConverter(getFile().getAbsolutePath(), file, getFormat());
			pom2rdf.convert(out);
		} else if (filename.equals("maven-metadata.xml")) {
			MetadataConverter metadata2rdf = new MetadataConverter(getFile().getAbsolutePath(), file, getFormat());
			metadata2rdf.convert(out);
		}
	}
	
	/* (non-Javadoc)
	 * @see edu.mit.simile.rdfizer.pom2rdf.AbstractConverter#convert(java.io.Writer)
	 */
	public void convert(Writer out) {
		StringWriter sw = new StringWriter();
		try {
			VelocityContext context = getVelocityContext();
			out.write(sw.toString());
			getVelocityEngine().mergeTemplate("header.vt", context, sw);
			out.write(sw.toString());
			recurse (out, getFile());
			sw = new StringWriter();
			getVelocityEngine().mergeTemplate("footer.vt", context, sw);
			out.write(sw.toString());
		} catch (ResourceNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (ParseErrorException e) {
			logger.error(e.getMessage(), e);
		} catch (MethodInvocationException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see edu.mit.simile.rdfizer.pom2rdf.AbstractConverter#getVelocityContext()
	 */
	public VelocityContext getVelocityContext() throws Exception {
		return new VelocityContext();
	}

	/* (non-Javadoc)
	 * @see edu.mit.simile.rdfizer.pom2rdf.AbstractConverter#getTemplatePrefix()
	 */
	public String getTemplatePrefix() {
		return null;
	}

}
