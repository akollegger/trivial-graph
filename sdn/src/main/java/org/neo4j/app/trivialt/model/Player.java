package org.neo4j.app.trivialt.model;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

@NodeEntity
public class Player {
    @Indexed
    private String handle;

    @Indexed(fulltext = true, indexName = "people")
    private String name;

    @RelatedTo(elementClass = Player.class, type = "FRIENDS")
    private Set<Player> friends;
    
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
}
