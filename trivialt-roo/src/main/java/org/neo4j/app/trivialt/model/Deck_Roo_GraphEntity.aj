// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.neo4j.app.trivialt.model;

import java.lang.Long;
import java.util.Collection;
import javax.annotation.Resource;
import org.neo4j.app.trivialt.model.Deck;
import org.neo4j.graphdb.Node;
import org.neo4j.helpers.collection.IteratorUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.repository.DirectGraphRepositoryFactory;
import org.springframework.data.neo4j.repository.GraphRepository;

privileged aspect Deck_Roo_GraphEntity {
    
    @GraphId
    private final Long Deck.id;
    
    public Deck.new(Node n) {
        setPersistentState(n);
    }

    public Long Deck.getId() {
        return getNodeId();

    }
    
    public Deck Deck.save() {
        return (Deck)persist();

    }
    
    public void Deck.delete() {
        remove();

    }
    
    public static Collection<Deck> Deck.findPaged(int start, int pageSize) {
        return IteratorUtil.asCollection(DeckRepositoryHolder.repository().findAll(new PageRequest(start/pageSize,pageSize)));

    }
    
    public static Deck Deck.findDeck(Long id) {
        return DeckRepositoryHolder.repository().findOne(id);

    }
    
    public static long Deck.count() {
        return DeckRepositoryHolder.repository().count();

    }
    
    public static Collection<Deck> Deck.findAll() {
        return IteratorUtil.asCollection(DeckRepositoryHolder.repository().findAll());

    }
    
    static class org.neo4j.app.trivialt.model.Deck.DeckRepositoryHolder{

                private DirectGraphRepositoryFactory repositoryFactory;

                private static GraphRepository<Deck> repository;
        @Resource
        public void setRepositoryFactory(DirectGraphRepositoryFactory repositoryFactory) {
         this.repositoryFactory = repositoryFactory; 

        }
        
        public static GraphRepository<Deck> repository() {
        if (DeckRepositoryHolder.repository==null) { DeckRepositoryHolder.repository=new DeckRepositoryHolder().repositoryFactory.createGraphRepository(Deck.class);}
return repository;
}} 
declare @type : Deck.DeckRepositoryHolder : @org.springframework.beans.factory.annotation.Configurable;
{{

        }
        
    }
    
}
