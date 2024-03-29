// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.neo4j.app.trivialt.model;

import flexjson.JSONDeserializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.neo4j.app.trivialt.model.Match;

privileged aspect Match_Roo_Json {
    
    public static Match Match.fromJsonToMatch(String json) {
        return new JSONDeserializer<Match>().use(null, Match.class).deserialize(json);
    }
    
    public static Collection<Match> Match.fromJsonArrayToMatches(String json) {
        return new JSONDeserializer<List<Match>>().use(null, ArrayList.class).use("values", Match.class).deserialize(json);
    }
    
}
