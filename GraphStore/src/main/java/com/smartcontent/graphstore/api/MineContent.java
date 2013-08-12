/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.graphstore.api;

import java.util.List;

/**
 *
 * @author Teuk
 */
public interface MineContent {
    
    public static String indexKey="name";
    
    public static String LANG="lang";
    
    public void subContentOf(MineContent m);
    
    public String getContent();
    
    public List<MineKeyWord> getKeyWords();
    
    public List<MineKeyWord> getInferedKeyWords();
    
    public List<MineContent> getClosestContent();
    
    public List<MineContent> getClosestInferedContent();
    
    public String getLang();
    
    public long getTime();
    
    public String getName();
    
    public void mineContent();
    
}
