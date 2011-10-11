package org.neo4j.app.trivialt.graph.model;

import org.neo4j.app.trivialt.graph.util.NodeMap;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Collection of trivia categories.
 */
public class Categories
{
    private GraphDatabaseService graphdb;
    private NodeMap catMap;

    public Categories( GraphDatabaseService graphdb )
    {
        this.graphdb = graphdb;
        catMap = NodeMap.named("categories", graphdb);
    }

    public Collection<Category> getAll()
    {
        Collection<Category> allCats = new ArrayList<Category>();
        for (Node catNode : catMap.values())
        {
            allCats.add( new Category(catNode) );
        }
        return allCats;
    }

    public Category categorize( FreeformTrivia trivia, Question question )
    {
        String categoryName = trivia.getCategory();
        Node categoryNode = catMap.get( categoryName );
        Category category = null;
        if (categoryNode == null) {
            category = new Category( catMap.put( categoryName, graphdb.createNode() ));
            category.setName( categoryName );
        } else {
            category = new Category(categoryNode);
        }
        category.include(question);
        return category;
    }

    public Category find( String category )
    {
        Category foundCategory = null;
        Node categoryNode = catMap.get(category);
        if (categoryNode != null) {
            foundCategory = new Category(categoryNode);
        }
        return foundCategory;
    }


}
