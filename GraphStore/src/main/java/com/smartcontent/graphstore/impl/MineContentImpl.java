/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.graphstore.impl;

import com.smartcontent.graphstore.api.MineContent;
import com.smartcontent.graphstore.api.MineKeyWord;
import com.smartcontent.textminer.impl.TextMinerKeyWord;
import com.smartcontent.textminer.impl.TextMinerImpl;
import com.smartcontent.textminer.impl.WordsBag;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;


/**
 *
 * @author Teuk
 */
public class MineContentImpl implements MineContent{

    private final Node node;
   
    public MineContentImpl(Node n){
        node = n;
    }
    
    @Override
    public void mineContent(){
        WordsBag w = TextMinerImpl.compute(this.getContent(),this.getLang());
        List<TextMinerKeyWord> kws = w.getKeyWordList();
        for(int i=0;i<kws.size();i++){
            this.addKeyWordLink(this.getKeyWord(kws.get(i).getStem()), kws.get(i).getOcc());
            for(int j=i+1;j<kws.size();j++){
                new MineKeyWordImpl(this.getKeyWord(kws.get(i).getStem())).incrLinkWith(
                        new MineKeyWordImpl(this.getKeyWord(kws.get(j).getStem())));
            }
        }
 
    }
    
    public Node getKeyWord(String stem){
        //System.out.println("getting: "+stem);
        Node k = node.getGraphDatabase().index().forNodes(MineKeyWord.class.getSimpleName()).get(MineKeyWord.indexKey, stem).getSingle();
        if(k == null){
            //System.out.println("not exist create: "+stem);
            Node kw = node.getGraphDatabase().createNode();
            kw.setProperty(MineKeyWord.indexKey, stem);
            node.getGraphDatabase().index().forNodes(MineKeyWord.class.getSimpleName()).add(kw, MineKeyWord.indexKey, stem);
            return kw;
        }else{
            return k;
        }
    }
        
    public String getContent(){
        return (String)node.getProperty("content");
    }
    
    @Override
    public void subContentOf(MineContent m) {
        
        
        
    }

    @Override
    public String getLang(){
        return (String) node.getProperty("lang");
    }

    public void addKeyWordLink(Node kw,int occ) {
        Relationship rs = node.createRelationshipTo(kw, Relations.ABOUT);
        rs.setProperty(MineKeyWord.OCCURENCES, occ);
    }

    @Override
    public List<MineKeyWord> getKeyWords() {
       ExecutionEngine engine = new ExecutionEngine( node.getGraphDatabase() );
       ExecutionResult result = engine.execute("start content=node("+node.getId()+") match (content)-[a:ABOUT]->(kw) return kw,a."+MineKeyWord.OCCURENCES+" order by a."+MineKeyWord.OCCURENCES+" DESC limit 10 " );
	
        ArrayList<MineKeyWord>l = new ArrayList<MineKeyWord>();
   
                        while(result.hasNext()){
                    try{
                    l.add(new MineKeyWordImpl((Node)result.next().get("kw").get()));
                    }catch(Exception e){
                        
                    }
                }
 
        return l;
    }

    @Override
    public List<MineContent> getClosestContent(){
        ExecutionEngine engine = new ExecutionEngine( node.getGraphDatabase() );
        ExecutionResult result = engine.execute("start content=node("+node.getId()+") match (content)-[a:ABOUT]->(kw)<-[b:ABOUT]-(docs) return docs,count(kw) order by count(kw) DESC limit 10 " );
        List<MineContent>ml = new ArrayList<MineContent>();
        while(result.hasNext()){
            try{
                ml.add(new MineContentImpl((Node)result.next().get("docs").get()));
            }catch(Exception e){

            }
        }
        return ml;
    }

    public long getTime() {
        return (Long)node.getProperty("time");
    }

    public String getName() {
        return (String)node.getProperty(MineContent.indexKey);
    }

    public List<MineKeyWord> getInferedKeyWords() {
        
        List<MineKeyWord>lk=this.getKeyWords();
        ArrayList<MineKeyWord>l = new ArrayList<MineKeyWord>();
        for(MineKeyWord mkw:lk){
            l.addAll(mkw.getRelatedKeyWords());
        }
        
     
        return l;
    }

    public List<MineContent> getClosestInferedContent() {
                ExecutionEngine engine = new ExecutionEngine( node.getGraphDatabase() );
        ExecutionResult result = engine.execute("start content=node("+node.getId()+") match (content)-[a:ABOUT]->(kw) with (kw) order by a."+MineKeyWord.OCCURENCES+" desc limit 10 "+
                                                    "match (kw)-[kl:KWREL]-(kw2) with kw2 order by kl."+MineKeyWord.WEIGHT+" desc limit 10 "+
                                                    " match (kw2)<-[b:ABOUT]-(docs) return docs,count(kw)+count(kw2) order by count(kw)+count(kw2) DESC limit 10 " );
        List<MineContent>ml = new ArrayList<MineContent>();
        while(result.hasNext()){
            try{
                ml.add(new MineContentImpl((Node)result.next().get("docs").get()));
            }catch(Exception e){

            }
        }
        return ml;
    }
    
}
