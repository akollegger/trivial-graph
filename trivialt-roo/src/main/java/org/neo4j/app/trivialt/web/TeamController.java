package org.neo4j.app.trivialt.web;

import org.neo4j.app.trivialt.model.Team;
import org.neo4j.app.trivialt.repository.TeamRepository;
import org.neo4j.app.trivialt.present.AvailabilityStatus;
import org.neo4j.helpers.collection.ClosableIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RooWebScaffold(path = "api/teams", formBackingObject = Team.class)
@RequestMapping("/api/teams")
@Controller
public class TeamController {

	@Autowired private TeamRepository teams;

    @RequestMapping(value="/availability", method=RequestMethod.GET)
    public @ResponseBody AvailabilityStatus getAvailability(@RequestParam String name) {
    	ClosableIterable<Team> findAllByPropertyValue = teams.findAllByPropertyValue("name", name);
		if (findAllByPropertyValue.iterator().hasNext())
    		return AvailabilityStatus.notAvailable(name);
    	else 
    		return AvailabilityStatus.available(name);

    }
}
