/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.graphstore.api;

import java.util.List;
import org.neo4j.graphdb.Node;

/**
 *
 * @author Teuk
 */
public interface MineKeyWord {
    
    public static String indexKey="stem";
    
    public static String OCCURENCES = "occ";
    
    public static String WEIGHT = "weight";
    
    public String getStem();
    
    public void linkWithKeyWord(MineKeyWord mkw,double weight);
    
    public double getIdf();
    
    public int getNumberRelContent();
    
    public List<MineContent> getRelatedContent();
    
    public Node getNode();
    
    public List<MineContentType> getRelatedContentType();
    
    public List<MineKeyWord> getRelatedKeyWords();
    
    public int commonContents(MineKeyWord mkw);
    
    public long getNodeId();
    
    public void incrLinkWith(MineKeyWord mkw);
    
    
}
