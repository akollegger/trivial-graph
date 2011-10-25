// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.neo4j.app.trivialt.model;

import flexjson.JSONDeserializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.neo4j.app.trivialt.model.Card;

privileged aspect Card_Roo_Json {
    
    public static Card Card.fromJsonToCard(String json) {
        return new JSONDeserializer<Card>().use(null, Card.class).deserialize(json);
    }
    
    public static Collection<Card> Card.fromJsonArrayToCards(String json) {
        return new JSONDeserializer<List<Card>>().use(null, ArrayList.class).use("values", Card.class).deserialize(json);
    }
    
}