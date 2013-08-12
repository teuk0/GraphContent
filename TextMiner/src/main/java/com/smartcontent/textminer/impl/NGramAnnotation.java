/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.textminer.impl;

import java.util.List;

/**
 *
 * @author Teuk
 */
public class NGramAnnotation extends StemmedAnnotation {
    
    String stem;
    
    public NGramAnnotation(TextMinerContent tmc,List<TokenAnnotation> lt){
        
        super(tmc, lt.get(0).getStart(), lt.get(lt.size()-1).getEnd());
        stem= "";
        for(TokenAnnotation t : lt){
            stem += t.getStem()+" ";
        }
        stem= stem.trim();
    }    
    
    public String getStem(){
        return stem;
    }

    @Override
    public boolean isStopWord() {
        return false;
    }
    
}
