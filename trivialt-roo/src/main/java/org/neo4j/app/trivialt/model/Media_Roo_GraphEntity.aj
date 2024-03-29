// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.neo4j.app.trivialt.model;

import java.lang.Long;
import java.util.Collection;
import javax.annotation.Resource;
import org.neo4j.app.trivialt.model.Media;
import org.neo4j.graphdb.Node;
import org.neo4j.helpers.collection.IteratorUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.repository.DirectGraphRepositoryFactory;
import org.springframework.data.neo4j.repository.GraphRepository;

privileged aspect Media_Roo_GraphEntity {
    
    @GraphId
    private final Long Media.id;
    
    public Media.new(Node n) {
        setPersistentState(n);
    }

    public Long Media.getId() {
        return getNodeId();

    }
    
    public Media Media.save() {
        return (Media)persist();

    }
    
    public void Media.delete() {
        remove();

    }
    
    public static Collection<Media> Media.findPaged(int start, int pageSize) {
        return IteratorUtil.asCollection(MediaRepositoryHolder.repository().findAll(new PageRequest(start/pageSize,pageSize)));

    }
    
    public static Media Media.findMedia(Long id) {
        return MediaRepositoryHolder.repository().findOne(id);

    }
    
    public static long Media.count() {
        return MediaRepositoryHolder.repository().count();

    }
    
    public static Collection<Media> Media.findAll() {
        return IteratorUtil.asCollection(MediaRepositoryHolder.repository().findAll());

    }
    
    static class org.neo4j.app.trivialt.model.Media.MediaRepositoryHolder{

                private DirectGraphRepositoryFactory repositoryFactory;

                private static GraphRepository<Media> repository;
        @Resource
        public void setRepositoryFactory(DirectGraphRepositoryFactory repositoryFactory) {
         this.repositoryFactory = repositoryFactory; 

        }
        
        public static GraphRepository<Media> repository() {
        if (MediaRepositoryHolder.repository==null) { MediaRepositoryHolder.repository=new MediaRepositoryHolder().repositoryFactory.createGraphRepository(Media.class);}
return repository;
}} 
declare @type : Media.MediaRepositoryHolder : @org.springframework.beans.factory.annotation.Configurable;
{{

        }
        
    }
    
}
