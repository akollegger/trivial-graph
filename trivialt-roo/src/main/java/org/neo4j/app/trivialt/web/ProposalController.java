package org.neo4j.app.trivialt.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.neo4j.app.trivialt.model.Proposal;
import org.neo4j.app.trivialt.service.TrivialtMatchPlay;
import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.GraphDatabaseContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RooWebScaffold(path = "api/proposals", formBackingObject = Proposal.class)
@RequestMapping("/api/proposals")
@Controller
public class ProposalController { 

	@Autowired private TrivialtMatchPlay trivialt;
	
    @Autowired GraphDatabaseContext gdc;
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        Proposal p = Proposal.fromJsonToProposal(json).save();
        
        trivialt.grade(p);
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
	
    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Proposal proposal, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest,
    		@RequestParam(value = "id", required = true) Long nodeId) {

        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("proposal", proposal);
            return "api/proposals/update";
        }
        Node node = gdc.getNodeById(nodeId);
        proposal.setPersistentState(node);
        uiModel.asMap().clear();
        proposal.save();
        return "redirect:/api/proposals/" + encodeUrlPathSegment(proposal.getId().toString(), httpServletRequest);
    }
}
