// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.neo4j.app.trivialt.service;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.neo4j.app.trivialt.service.Toggle;

privileged aspect Toggle_Roo_Json {
    
    public String Toggle.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static Toggle Toggle.fromJsonToToggle(String json) {
        return new JSONDeserializer<Toggle>().use(null, Toggle.class).deserialize(json);
    }
    
    public static String Toggle.toJsonArray(Collection<Toggle> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<Toggle> Toggle.fromJsonArrayToToggles(String json) {
        return new JSONDeserializer<List<Toggle>>().use(null, ArrayList.class).use("values", Toggle.class).deserialize(json);
    }
    
}
