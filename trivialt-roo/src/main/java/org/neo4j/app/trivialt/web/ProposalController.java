package org.neo4j.app.trivialt.web;

import org.neo4j.app.trivialt.model.Proposal;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RooWebScaffold(path = "api/proposals", formBackingObject = Proposal.class)
@RequestMapping("/api/proposals")
@Controller
public class ProposalController { 
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        Proposal p = Proposal.fromJsonToProposal(json).save();
        HttpHeaders headers= new HttpHeaders();
        headers.add("Content-Type", "application/text");
        return new ResponseEntity<String>(p.toJson(),headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers= new HttpHeaders();
        headers.add("Content-Type", "application/text");
        Proposal p = Proposal.fromJsonToProposal(json).save();
        if (p == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(p.toJson(), headers, HttpStatus.OK);
    }
    
}
