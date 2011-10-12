package org.neo4j.app.trivialt.graph;

import java.io.File;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.neo4j.app.trivialt.graph.model.TrivialtWorld;

/**
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Q: what am I doing?" );

        File defaultGraphDir = new File("trivialt.graphdb");
        OptionParser optParser = new OptionParser( );
        OptionSpec<File> graphdir =
            optParser.accepts( "graphdir" ).withRequiredArg().ofType( File.class ).defaultsTo( defaultGraphDir );

        OptionSet opts = optParser.parse(args);

        TrivialtWorld trivialt = new TrivialtWorld(graphdir.value(opts));

        trivialt.shutdown();

        System.out.println( "A: I dunno, but it's over now." );
    }
}
