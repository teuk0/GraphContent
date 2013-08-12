/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.textminer.impl;

import com.smartcontent.textminer.Stemmers.FrenchStemmer;



/**
 *
 * @author Teuk
 */
public class TokenAnnotation extends StemmedAnnotation{
    
    String stem;
    boolean isStopWord;
    
    public TokenAnnotation(TextMinerContent tmc,int start,int end){
        super(tmc, start, end);
   
        FrenchStemmer stemmer = new FrenchStemmer();
        stemmer.setCurrent(this.getView().toLowerCase().trim());
        stemmer.stem();
        stem = stemmer.getCurrent();
        
        isStopWord = TextMinerStopWords.contains(tmc.getLang(), new String(tmc.getContent(),start, end-start).trim().toLowerCase()) ||
               TextMinerStopWords.contains(tmc.getLang(), stem) || isInteger(stem) ;
    }
    
    public String getStem(){
        return stem;
    }
    
    public boolean isStopWord(){
        return isStopWord;
    }
    
    public String toString(){return stem;}
    
    
    public boolean isInteger(String str) {
    try {
        Integer.parseInt(str);
        return true;
    } catch (NumberFormatException nfe) {}
    return false;
}

    
}
