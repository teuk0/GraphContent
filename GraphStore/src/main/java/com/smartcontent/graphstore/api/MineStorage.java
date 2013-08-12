/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.graphstore.api;

import java.util.Set;

/**
 *
 * @author Teuk
 */
public interface MineStorage {
    
    public MineKeyWord getKeyWord(String stem) throws UnsatisfiedLinkError;
    
    public Set<MineKeyWord> getKeyWords();
    
    public MineContentType getType(String name) throws UnsatisfiedLinkError;
    
    public void createContentType(String name);
    
    public MineContent getContent(String name) throws UnsatisfiedLinkError;
    
    public void linkKeyWords();
 
    public void shutDown();
}
