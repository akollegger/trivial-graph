// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.neo4j.app.trivialt.service;

import java.lang.String;

privileged aspect Toggle_Roo_ToString {
    
    public String Toggle.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("State: ").append(getState());
        return sb.toString();
    }
    
}
