package edu.mit.simile.rdfizer.pom2rdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class pom2rdf {

    public static final String NAME = "POM2RDF RDFizer";
    public static final String CLINAME = "pom2rdf";
    public static final String VERSION = "0.1";
	
    protected Logger logger;
    
    public pom2rdf() {
        Logger.getRootLogger().removeAllAppenders();
        PropertyConfigurator.configure(this.getClass().getResource("/log4j.properties"));
        logger = Logger.getLogger(pom2rdf.class);
    }

    public void process(String path, String filename, String format) throws IOException {
        System.out.println(NAME + " " + VERSION);
        
		MavenRepositoryConverter mvn2rdf = new MavenRepositoryConverter(path, new File(path), format);
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileWriter(filename));
			mvn2rdf.convert(out);
		} catch (FileNotFoundException e) {
			fatal (e.getMessage(), e);
		} finally {
			if (out != null) out.close();
		}
			
    }
    
    void fatal(String str, Exception e) {
        logger.error(str, e);
        e.printStackTrace(System.err);
        System.exit(1);
    }
    
    void fatal(String str) {
        logger.error(str);
        System.exit(1);
    }
    
    void log(String str) {
        logger.info(str);
    }

    private static void usage() {
        System.out.println(NAME + " command line tool, version " + VERSION);
        System.out.println("usage: pom2rdf </path/to/maven/repository> [filename] [format]");
        System.out.println();
        System.out.println("pom2rdf is a tool extract metadata from a Maven2 repository.");
        System.out.println("For additional information, see http://simile.mit.edu/repository/RDFizers/pom2rdf");
    }
    
    
	public static void main(String[] args) throws Exception {
        if (args.length == 0 || args.length > 3 || "help".equals(args[0])) {
        	usage();
         } else if ("--version".equals(args[0])) {
             System.out.println(NAME + " " + VERSION);
         } else {
             pom2rdf rdfizer = new pom2rdf();
        	 String path = args[0];
        	 
        	 File file = new File(path); 
        	 if (!file.exists()) {
        		 rdfizer.fatal("The directory " + path + " does not exist.");
        	 } else if (!file.canRead()) {
        		 rdfizer.fatal("It is not possible to read files from the " + path + " directory.");        		 
        	 }

        	 String filename = "./maven-repository.ttl";
        	 if (args.length > 1) {
        		 filename = args[1];
        	 }

        	 file = new File(filename);
        	 if (file.exists()) {
        		 rdfizer.fatal("The file " + filename + " already exists, please delete it.");        		 
        	 }

        	 String format = Format.TURTLE;
        	 if (args.length > 2 ) {
        		 format = args[2];
        	 }

        	 if (!format.equals(Format.N3) && !format.equals(Format.TURTLE) && !format.equals(Format.XML)) {
        		 rdfizer.fatal(format + " is not a valid format, please use '" + Format.TURTLE + "' or '" + Format.XML + "'.");        	        		 
        	 }

        	 rdfizer.process(path, filename, format);
         }
	}

}