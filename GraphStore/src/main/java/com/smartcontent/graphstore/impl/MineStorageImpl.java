/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.graphstore.impl;

import com.smartcontent.graphstore.api.MineContent;
import com.smartcontent.graphstore.api.MineContentType;
import com.smartcontent.graphstore.api.MineKeyWord;
import com.smartcontent.graphstore.api.MineStorage;
import com.smartcontent.textminer.impl.TextMinerImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;

/**
 *
 * @author Teuk
 */
public class MineStorageImpl implements MineStorage {

    
    private String dbpath;

    public  GraphDatabaseService graphDb;
    
    public MineStorageImpl(String path){
        dbpath = path;
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbpath);
        registerShutdownHook(graphDb);
        TextMinerImpl.init();
    }
    
    @Override
    public MineKeyWord getKeyWord(String stem) throws UnsatisfiedLinkError{
        Node n = graphDb.index().forNodes(MineKeyWord.class.getSimpleName()).get(MineKeyWord.indexKey, stem).getSingle();
        if(n==null){
            throw new UnsatisfiedLinkError(stem);
        }
        return new MineKeyWordImpl(n);
    }

    
    
    @Override
    public MineContentType getType(String name) {
        Node n = graphDb.index().forNodes(MineContentType.class.getSimpleName()).get(MineContentType.indexKey, name).getSingle();
        if(n==null){
            throw new UnsatisfiedLinkError(name);
        }
        return new MineContentTypeImpl(n);
    }
    
    @Override
    public MineContent getContent(String name){
        Node n = graphDb.index().forNodes(MineContent.class.getSimpleName()).get(MineContent.indexKey, name).getSingle();
        if(n==null){
            throw new UnsatisfiedLinkError(name);
        }
        return new MineContentImpl(n);
    }

    @Override
    public void createContentType(String name) {
        Transaction tx = graphDb.beginTx();
        Node n  = graphDb.createNode();
        n.setProperty(MineContentType.indexKey, name);
        MineContentType m = new MineContentTypeImpl(n);
        n.getGraphDatabase().index().forNodes(MineContentType.class.getSimpleName()).add(n, MineContentType.indexKey, n.getProperty(MineContentType.indexKey));
        tx.success();
        tx.finish();    
    }
    
    public static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running example before it's completed)
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        } );
    }

    public static void main(String[]argv){
        MineStorage ms = new MineStorageImpl("minestorage1");
        MineContentType mct = ms.getType("brutcontent");
        MineContent mc = ms.getContent("doc5");
        mct.getKeyWords();
        System.out.println(mc.getKeyWords());
    }

    public void shutDown() {
        graphDb.shutdown();
    }

    public Set<MineKeyWord> getKeyWords() {
        IndexHits<Node> hits = graphDb.index().forNodes(MineContent.class.getSimpleName()).query("*:*");
        Set<MineKeyWord>kws = new HashSet<MineKeyWord>();
        try{
            while(hits.hasNext()){
                System.out.println("ok");
                Node no = hits.next();
                kws.add(new MineKeyWordImpl(no));
            }
        }finally{
            hits.close();
        }
        return kws;
    }

    public void linkKeyWords() {
        List<MineKeyWord> l1 = new ArrayList<MineKeyWord>(this.getKeyWords());
        List<MineKeyWord> l2 = new ArrayList<MineKeyWord>(l1);
        
        for(int i =0;i<l1.size()-1;i++){
            System.out.println(i);
            for(int j=i+1;j<l2.size();j++){
                double currentRelContent = l1.get(i).getNumberRelContent();
                double relBetween = l1.get(i).commonContents(l2.get(j));
                if(relBetween>0){
                    l1.get(i).linkWithKeyWord(l2.get(j), relBetween/currentRelContent);
                }
            }
        }
        
    }
    
    
}
