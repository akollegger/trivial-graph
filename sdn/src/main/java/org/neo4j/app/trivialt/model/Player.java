package org.neo4j.app.trivialt.model;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Player {
    @Indexed
    private String handle;

    @Indexed(fulltext = true, indexName = "people")
    private String name;

    public Player() {}

    public Player(String handle, String name) {
        this.handle = handle;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getHandle() {
        return handle;
    }
}
