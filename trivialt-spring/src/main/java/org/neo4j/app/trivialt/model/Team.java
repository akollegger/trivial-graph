package org.neo4j.app.trivialt.model;

import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

@NodeEntity
public class Team {

    @Indexed
    private String name;

    private String secret;
    
    @RelatedToVia(elementClass = Role.class, type = "MEMBER", direction = OUTGOING)
    private Iterable<Role> members;

    private Set<Deck> decks;
    
    public Team() {
    }

    public Team(String name, String secret) {
        this.name = name;
        this.setSecret(secret);
    }

    public String getName() {
        return name;
    }

	public void setFounder(Player founder) {
        Role role = relateTo(founder, Role.class, "MEMBER");
        role.setTitle(Role.FOUNDER);
	}
	
	public Player getFounder()
	{
		Player founder = null;
		for (Role r : members)
		{
			if (Role.FOUNDER.equals(r.getTitle()))
			{
				founder = r.getPlayer();
				break;
			}
		}
		return founder;
	}

	public void add(Player player) {
		relateTo(player, Role.class, "MEMBER");
	}
	
	public Iterable<Player> getMembers() {
		Collection<Player> players = new ArrayList<Player>();
		for (Role r : members)
		{
			players.add(r.getPlayer());
		}
		return players;
	}

	public boolean confirmSecret(String usingSecret) {
		return (usingSecret != null) && (usingSecret.equals(getSecret()));
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Set<Deck> getDecks() {
		return decks;
	}

	public void add(Deck deck) {
		this.decks.add(deck);
	}

}
