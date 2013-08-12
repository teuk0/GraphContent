/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.graphstore.impl;

import com.smartcontent.graphstore.api.MineContent;
import com.smartcontent.graphstore.api.MineContentType;
import com.smartcontent.graphstore.api.MineKeyWord;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

/**
 *
 * @author Teuk
 */
public class MineContentTypeImpl implements MineContentType {

    
  
    
    private final Node node;
    
    public MineContentTypeImpl(Node n){
        node = n;
    }
    
    @Override
    public void createContent(String name, String lang, String content) {
        //System.out.println("content: --"+content+"--");
        Transaction tx = node.getGraphDatabase().beginTx();
        Node n  = node.getGraphDatabase().createNode();
        n.setProperty(MineContent.indexKey, name);
        n.setProperty("content", content);
        n.setProperty("lang", lang);
        n.setProperty("time", System.currentTimeMillis());
        node.createRelationshipTo(n, Relations.CONTAINS);
        n.getGraphDatabase().index().forNodes(MineContent.class.getSimpleName()).add(n, MineContent.indexKey, n.getProperty(MineContent.indexKey));
        MineContent m = new MineContentImpl(n);
        m.mineContent();
        tx.success();
        tx.finish();   
    }

    @Override
    public Set<MineContent> getContents() {
        HashSet hs = new HashSet<MineContent>();
        for(Relationship rs:node.getRelationships(Relations.CONTAINS)){
            hs.add(new MineContentImpl(rs.getEndNode()));
        }
        return hs;
    }

    @Override
    public List<MineKeyWord> getKeyWords() {
            long time = System.currentTimeMillis();
        	ExecutionEngine engine = new ExecutionEngine( node.getGraphDatabase() );
                //ExecutionResult result = engine.execute("start type=node("+node.getId()+") match (type)-[:CONTAINS]->(docs),(docs)-[a:ABOUT]->(kw) return kw."+MineKeyWord.indexKey+",sum(a."+MineKeyWord.OCCURENCES+") order by sum(a."+MineKeyWord.OCCURENCES+") " );
		ExecutionResult result = engine.execute("start type=node("+node.getId()+") match (type)-[:CONTAINS]->(docs),(docs)-[a:ABOUT]->(kw) return kw,sum(a."+MineKeyWord.OCCURENCES+") order by sum(a."+MineKeyWord.OCCURENCES+") DESC limit 10 " );
		System.out.println(System.currentTimeMillis()-time);
                result = engine.execute("start type=node("+node.getId()+") match (type)-[:CONTAINS]->(docs),(docs)-[a:ABOUT]->(kw) return kw,sum(a."+MineKeyWord.OCCURENCES+") order by sum(a."+MineKeyWord.OCCURENCES+") DESC limit 10 " );
		System.out.println(System.currentTimeMillis()-time);
                System.out.println(result);
		List<MineKeyWord>ml = new ArrayList<MineKeyWord>();
                while(result.hasNext()){
                    try{
                    ml.add(new MineKeyWordImpl((Node)result.next().get("kw").get()));
                    }catch(Exception e){
                        
                    }
                }
                //System.out.println(result.next().get("kw.stem").get().getClass());
                System.out.println(System.currentTimeMillis()-time);
                return ml;
    }

 
    
}
