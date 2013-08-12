/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.graphstore.impl;

import com.smartcontent.graphstore.api.MineContent;
import com.smartcontent.graphstore.api.MineContentType;
import com.smartcontent.graphstore.api.MineKeyWord;
import java.util.ArrayList;
import java.util.List;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

/**
 *
 * @author Teuk
 */
public class MineKeyWordImpl implements MineKeyWord {
    
    private final Node node;
    
    public MineKeyWordImpl(Node n){
        node = n;
    }

    @Override
    public String getStem(){
        return (String)node.getProperty(indexKey);
    }
    
    @Override
    public String toString(){
        return getStem();
    }

    @Override
    public List<MineContent> getRelatedContent(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<MineContentType> getRelatedContentType(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<MineKeyWord> getRelatedKeyWords(){
        ExecutionEngine engine = new ExecutionEngine( node.getGraphDatabase() );
        //ExecutionResult result = engine.execute("start type=node("+node.getId()+") match (type)-[:CONTAINS]->(docs),(docs)-[a:ABOUT]->(kw) return kw."+MineKeyWord.indexKey+",sum(a."+MineKeyWord.OCCURENCES+") order by sum(a."+MineKeyWord.OCCURENCES+") " );
	ExecutionResult result = engine.execute("start kw=node("+node.getId()+") match (kw)-[k:KWREL]-(kws) where k."+MineKeyWord.WEIGHT+">1   return kws,k."+MineKeyWord.WEIGHT+" order by k."+MineKeyWord.WEIGHT+" DESC limit 10 " );
	List<MineKeyWord> l = new ArrayList<MineKeyWord>();
        while(result.hasNext()){
            try{
            l.add(new MineKeyWordImpl((Node)result.next().get("kws").get()));
            }catch(Exception e){}
            //System.out.println("res "+result.next());
        }
  
        return l;
    }

    public double getIdf() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int commonContents(MineKeyWord mkw) {
        ExecutionEngine engine = new ExecutionEngine( node.getGraphDatabase() );
        //ExecutionResult result = engine.execute("start type=node("+node.getId()+") match (type)-[:CONTAINS]->(docs),(docs)-[a:ABOUT]->(kw) return kw."+MineKeyWord.indexKey+",sum(a."+MineKeyWord.OCCURENCES+") order by sum(a."+MineKeyWord.OCCURENCES+") " );
	ExecutionResult result = engine.execute("start kw=node("+node.getId()+") , kw2=node("+mkw.getNodeId()+") match (kw)-[:ABOUT]-(docs)-[:ABOUT]-(kw2) return count(*) " );

        long i=0;
        while(result.hasNext()){
            i = (Long)result.next().get("count(*)").get();
            System.out.println(i);
         
        }
        return (int)i;
    }

    public long getNodeId() {
        return node.getId();
    }
    
    
    
    public void linkWithKeyWord(final MineKeyWord mkw,double weight){
        Transaction tx = node.getGraphDatabase().beginTx();
        Relationship rs = node.createRelationshipTo(mkw.getNode(), Relations.KWREL);
        rs.setProperty(MineKeyWord.WEIGHT, weight);
        System.out.println("new link from "+this.getStem()+" to "+mkw.getStem());
        tx.success();
        tx.finish(); 
    }
    
    public Node getNode(){
        return node;
    }

    public int getNumberRelContent() {
        int cc=0;
        for(Relationship rs:node.getRelationships(Relations.ABOUT)){
            cc++;
        }
        System.out.println(cc);
        return cc;
    }

    public void incrLinkWith(final MineKeyWord mkw) {
        Relationship found=null;
        for(Relationship rs:node.getRelationships(Relations.KWREL)){
            if(rs.getOtherNode(node).equals(mkw.getNode())){
                found=rs;
                break;
            }
        }
     
        if(found!=null){
            found.setProperty(WEIGHT, (Integer)(found.getProperty(WEIGHT))+1);
        }else{
            found=node.createRelationshipTo(mkw.getNode(), Relations.KWREL);
            found.setProperty(WEIGHT, new Integer(1));
        }
  
    }

 
    
    
}
