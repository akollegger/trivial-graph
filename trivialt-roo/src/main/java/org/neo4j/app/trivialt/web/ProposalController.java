package org.neo4j.app.trivialt.web;

import org.neo4j.app.trivialt.model.Proposal;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "proposals", formBackingObject = Proposal.class)
@RequestMapping("/proposals")
@Controller
public class ProposalController {
}
