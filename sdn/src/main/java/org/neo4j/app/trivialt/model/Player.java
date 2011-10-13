package org.neo4j.app.trivialt.model;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import static org.neo4j.graphdb.Direction.BOTH;
import static org.neo4j.graphdb.Direction.INCOMING;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@NodeEntity
public class Player {
    @Indexed
    private String handle;

    @Indexed(fulltext = true, indexName = "people")
    private String name;

    @RelatedTo(elementClass = Player.class, type = "FRIEND", direction = BOTH)
    private Set<Player> friends;

    @RelatedToVia(elementClass = Role.class, type = "MEMBER", direction = INCOMING)
    private Iterable<Role> memberships;
    
    public Player() {}

    public Player(String handle, String name) {
        this.handle = handle;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getHandle() {
        return handle;
    }

    public Set<Player> getFriends() {
        return friends;
    }

    public void addFriend(Player player) {
        this.friends.add(player);
    }

	public Iterable<Team> getMemberships() {
		Collection<Team> teams = new ArrayList<Team>();
		for (Role r : memberships) {
			teams.add(r.team);
		}
		return teams;
	}
}
