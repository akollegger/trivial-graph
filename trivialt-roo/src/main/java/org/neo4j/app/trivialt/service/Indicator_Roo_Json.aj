// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.neo4j.app.trivialt.service;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.neo4j.app.trivialt.service.Indicator;

privileged aspect Indicator_Roo_Json {
    
    public String Indicator.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static Indicator Indicator.fromJsonToIndicator(String json) {
        return new JSONDeserializer<Indicator>().use(null, Indicator.class).deserialize(json);
    }
    
    public static String Indicator.toJsonArray(Collection<Indicator> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<Indicator> Indicator.fromJsonArrayToIndicators(String json) {
        return new JSONDeserializer<List<Indicator>>().use(null, ArrayList.class).use("values", Indicator.class).deserialize(json);
    }
    
}
