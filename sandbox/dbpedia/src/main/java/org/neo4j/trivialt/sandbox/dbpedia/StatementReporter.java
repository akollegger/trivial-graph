package org.neo4j.trivialt.sandbox.dbpedia;

import org.openrdf.model.*;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;

/**
 * Created by IntelliJ IDEA.
 * User: akollegger
 * Date: 10/5/11
 * Time: 12:02 AM
 */
public class StatementReporter implements RDFHandler
{
    public void startRDF() throws RDFHandlerException
    {
        ; // don't care
    }

    public void endRDF() throws RDFHandlerException
    {
        ; // don't care
    }

    public void handleNamespace( String prefix, String uri ) throws RDFHandlerException
    {
        System.out.println( "ns: " + prefix + "\t" + uri );
    }

    public void handleStatement( Statement st ) throws RDFHandlerException
    {
        String predicateName = st.getPredicate().getLocalName();
        System.out.print( "" + toString( st.getSubject() ) + " " + toString( st.getPredicate() ) + " " );
        if ( predicateName.equals( "homepage" ) )
        {
            System.out.println( st.getObject().stringValue() );
        }
        else
            System.out.println( toString( st.getObject() ) );
    }

    private String toString( Value rdfVal )
    {
        if ( rdfVal instanceof URI )
        {
            return ((URI) rdfVal).getLocalName();
        }
        else if ( rdfVal instanceof Literal )
        {
            Literal literal = (Literal) rdfVal;
            return literal.getLabel() + " {" + literal.getLanguage() + "," + literal.getDatatype() + "}";
        } else if (rdfVal instanceof BNode)
        {
            BNode bnode = (BNode) rdfVal;
            return "...";
        }
        throw new RuntimeException( rdfVal.getClass().toString() );
    }

    public void handleComment( String comment ) throws RDFHandlerException
    {
        System.out.println( "// " + comment );
    }
}
