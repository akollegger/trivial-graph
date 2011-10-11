package org.neo4j.trivialt.sandbox.dbpedia;

import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.helpers.StatementCollector;
import org.openrdf.rio.ntriples.NTriplesParser;
import org.openrdf.rio.rdfxml.RDFXMLParser;
import org.openrdf.rio.turtle.TurtleParser;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Loads an RDF file, displaying the parsed contents.
 */
public class LoadRDF
{
    private String filename;

    public LoadRDF()
    {
    }

    public void load( String filename )
    {
        RDFParser rdfParser = parserFor( filename );
        rdfParser.setRDFHandler( new StatementReporter() );
        try
        {
            rdfParser.parse( new FileInputStream( filename ), "http://abk/" );
        } catch ( IOException e )
        {
            e.printStackTrace();
        } catch ( RDFParseException e )
        {
            e.printStackTrace();
        } catch ( RDFHandlerException e )
        {
            e.printStackTrace();
        }
    }

    private RDFParser parserFor( String filename )
    {
        if ( filename.endsWith( "nt" ) )
        {
            return new NTriplesParser();
        }
        else if ( filename.endsWith( "ttl" ) )
        {
            return new TurtleParser();
        } else if (filename.endsWith( "rdf")) {
            return new RDFXMLParser();
        }
        throw new RuntimeException("No parser found for: " + filename);
    }

    public static void main( String[] args )
    {
        LoadRDF loadr = new LoadRDF();
        //loadr.load( "/Users/akollegger/Developer/neo/githubs/trivial-graph/trivial-graph.code/sandbox/dbpedia/data/instance_types_en.nt" );
        // abk-persondata has modified dates, where any february date > 28 was revised to 28
        //loadr.load( "/Users/akollegger/Developer/neo/githubs/trivial-graph/trivial-graph.code/sandbox/dbpedia/data/abk-persondata_en.nt" );
        //loadr.load( "/Users/akollegger/Developer/neo/githubs/trivial-graph/trivial-graph.code/sandbox/dbpedia/data/mappingbased_properties_en_a.nt" );
        //loadr.load( "/Users/akollegger/Developer/neo/githubs/trivial-graph/trivial-graph.code/sandbox/dbpedia/data/mappingbased_properties_en_b.nt" );
        //loadr.load( "/Users/akollegger/Developer/neo/githubs/trivial-graph/trivial-graph.code/sandbox/dbpedia/data/mappingbased_properties_en_c.nt" );
        //loadr.load( "/Users/akollegger/Developer/neo/githubs/trivial-graph/trivial-graph.code/sandbox/dbpedia/data/mappingbased_properties_en_d.nt" );
        //loadr.load( "/Users/akollegger/Developer/neo/githubs/trivial-graph/trivial-graph.code/sandbox/dbpedia/data/mappingbased_properties_en_e.nt" );
        //loadr.load( "/Users/akollegger/Developer/neo/githubs/trivial-graph/trivial-graph.code/sandbox/dbpedia/data/mappingbased_properties_en_f.nt" );
        //loadr.load( "/Users/akollegger/Developer/neo/githubs/trivial-graph/trivial-graph.code/sandbox/dbpedia/data/mappingbased_properties_en_g.nt" );
        //loadr.load( "/Users/akollegger/Developer/neo/githubs/trivial-graph/trivial-graph.code/sandbox/dbpedia/data/mappingbased_properties_en_h.nt" );
        //loadr.load( "/Users/akollegger/Developer/neo/githubs/trivial-graph/trivial-graph.code/sandbox/dbpedia/data/mappingbased_properties_en_i.nt" );
        //loadr.load( "/Users/akollegger/Developer/neo/githubs/trivial-graph/trivial-graph.code/sandbox/dbpedia/data/mappingbased_properties_en_j.nt" );
        //loadr.load( "/Users/akollegger/Developer/neo/githubs/trivial-graph/trivial-graph.code/sandbox/dbpedia/data/mappingbased_properties_en_k.nt" );
        //loadr.load( "/Users/akollegger/Developer/neo/githubs/trivial-graph/trivial-graph.code/sandbox/dbpedia/data/mappingbased_properties_en_l.nt" );
        //loadr.load( "/Users/akollegger/Developer/neo/githubs/trivial-graph/trivial-graph.code/sandbox/dbpedia/data/mappingbased_properties_en_m.nt" );
        //loadr.load( "/Users/akollegger/Developer/neo/githubs/trivial-graph/trivial-graph.code/sandbox/dbpedia/data/mappingbased_properties_en_n.nt" );
        //loadr.load( "/Users/akollegger/Developer/neo/githubs/trivial-graph/trivial-graph.code/sandbox/dbpedia/data/mappingbased_properties_en_o.nt" );
        //loadr.load( "/Users/akollegger/Developer/neo/githubs/trivial-graph/trivial-graph.code/sandbox/dbpedia/data/mappingbased_properties_en_p.nt" );
        //loadr.load( "/Users/akollegger/Developer/neo/githubs/trivial-graph/trivial-graph.code/sandbox/dbpedia/data/mappingbased_properties_en_q.nt" );
        //loadr.load( "/Users/akollegger/Developer/neo/githubs/trivial-graph/trivial-graph.code/sandbox/dbpedia/data/mappingbased_properties_en_r.nt" );
        //loadr.load( "/Users/akollegger/Developer/neo/githubs/trivial-graph/trivial-graph.code/sandbox/dbpedia/data/specific_mappingbased_properties_en.nt" );
        loadr.load( "/Users/akollegger/Developer/neo/githubs/trivial-graph/trivial-graph.code/sandbox/dbpedia/data/geospecies.rdf" );

    }

}
