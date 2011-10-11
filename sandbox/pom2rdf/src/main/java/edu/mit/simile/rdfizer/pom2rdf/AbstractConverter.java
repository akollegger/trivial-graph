package edu.mit.simile.rdfizer.pom2rdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;

public abstract class AbstractConverter {

	private Logger logger = Logger.getLogger(AbstractConverter.class);
	private static VelocityEngine velocity = null;
	private File file = null;
	private String format = null;
	private String local_path = null;
	private ArrayList errors = null;


	public AbstractConverter(String local_path, File file, String format) {
		this.local_path = local_path;
		this.file = file;
		this.format = format;
		this.errors = new ArrayList();

		// I need to do this in order to avoid a strage error: 
		// "log4j:ERROR Attempted to append to closed appender named [null]." 
		// ... i suspect it is something related to Velocity + Log4J -- castagna
		if (velocity == null) {
			initVelocity();
		}
	}
	
	protected void initVelocity() {
        velocity = new VelocityEngine();
        velocity.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
        velocity.setProperty(RuntimeConstants.RUNTIME_LOG, "./target/velocity.log");
        velocity.addProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, "src/main/resources/templates/" + getFormat());
        velocity.addProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, Boolean.FALSE);
        velocity.addProperty(RuntimeConstants.VM_LIBRARY_AUTORELOAD, Boolean.TRUE);
        velocity.addProperty(RuntimeConstants.OUTPUT_ENCODING, "UTF-8");
        velocity.addProperty(RuntimeConstants.INPUT_ENCODING, "UTF-8");
        velocity.addProperty(RuntimeConstants.ENCODING_DEFAULT, "UTF-8");
        
        try {
			velocity.init();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}		
	}

	public abstract VelocityContext getVelocityContext() throws Exception;

	public abstract String getTemplatePrefix();

	public File getFile() {
		return file;
	}
	
	public String getLocalPath() {
		return local_path;
	}
	
	public String getFormat() {
		if (format.equals(Format.TURTLE)) {
			return Format.N3;
		}
		return format;
	}
	
	public VelocityEngine getVelocityEngine() {
		return velocity;
	}
	
	public String getContent() throws MalformedURLException, IOException {
		StringBuffer content = new StringBuffer();
		BufferedReader in = new BufferedReader(new FileReader(getFile()));
		String str;
		while ((str = in.readLine()) != null) {
			content.append(str).append("\n");
		}
		in.close();
		
		return content.toString();
	}
	
	public void addErrorMessage(String msg) {
		errors.add(msg);
	}
	
	public List getErrorMessages() {
		return errors;
	}
	
	public void convert(Writer out) {
		StringWriter sw = new StringWriter();
		try {
			VelocityContext context = getVelocityContext();
			context.put("errors", getErrorMessages());
			context.put("str", new StringUtils()); 
			context.put("encoder", new Encoder());
			String template = getTemplatePrefix() + "2" + getFormat() + ".vt";
			velocity.mergeTemplate(template, context, sw);
			out.write(sw.toString());
			out.flush();
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
	
}
