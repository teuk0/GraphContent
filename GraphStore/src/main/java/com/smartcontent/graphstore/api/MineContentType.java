/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.graphstore.api;

import java.util.List;
import java.util.Set;

/**
 *
 * @author Teuk
 */
public interface MineContentType {
    
    public static String indexKey="name";
    
    public void createContent(String name,String lang,String content);
    
    public Set<MineContent> getContents();
    
    public List<MineKeyWord> getKeyWords();
    
}
