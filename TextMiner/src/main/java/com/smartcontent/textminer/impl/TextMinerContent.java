/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.textminer.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 *
 * @author Teuk
 */
public class TextMinerContent {
    
    char[]content;
    List<TextMinerAnnotation>annotations;
    String lang;
    
    public TextMinerContent(String content,String lang){
        
        this.content = TextMinerUtils.normalize(content).toCharArray();
        annotations = new ArrayList<TextMinerAnnotation>();
        this.lang = lang;
    }
    
    public char[] getContent(){
        return content;
    }
    
    public String getLang(){
        return lang;
    }
    
    public void addAnnotation(TextMinerAnnotation annot){
        annotations.add(annot);
    }
    
    public List<TextMinerAnnotation> getAnnotations(){
        return annotations;
    }
    
    public <A  extends TextMinerAnnotation> List<A> getAnnotations(Class<A> c){
        ArrayList<A>l = new ArrayList<A>();
        for(TextMinerAnnotation t:getAnnotations()){
            if(t.getClass().equals(c)){
                l.add((A)t);
            }
        }
        Collections.sort(l);
        return l;
    }
    
}
