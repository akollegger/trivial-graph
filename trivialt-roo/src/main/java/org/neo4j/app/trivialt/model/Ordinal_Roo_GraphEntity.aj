// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.neo4j.app.trivialt.model;

import org.neo4j.graphdb.Relationship;

privileged aspect Ordinal_Roo_GraphEntity {
    
    public Ordinal.new(Relationship r) {
        setPersistentState(r);
    }

}
