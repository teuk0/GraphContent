/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.textminer.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Teuk
 */
public class NgramComponent implements TextMinerComponent{

    /**
     * Max value N of Ngram
     */
    private static int N=1;
    
    
    @Override
    public void execute(TextMinerContent tmc) {
        
        LinkedList<TokenAnnotation>qt = new LinkedList<TokenAnnotation>();
        Iterator<SentenceAnnotation>sentences = tmc.getAnnotations(SentenceAnnotation.class).iterator();
        SentenceAnnotation sentence = sentences.next();
        for(TokenAnnotation t : tmc.getAnnotations(TokenAnnotation.class )){
            
            if(t.getStart()>sentence.getEnd()){
                while(qt.isEmpty()){
                    qt.remove();
                    createAnnotation(qt, tmc);
                }
              
                if(sentences.hasNext()){
                    while(sentences.hasNext()&&t.getStart()>sentence.getEnd()){
                        //System.out.println("next sent "+t.getStart()+" "+sentence.getEnd());
                        sentence = sentences.next();
                    }
                }else{
                    break;
                }
            }
            
            if(!t.isStopWord()){
                qt.offer(t);
                if(qt.size()>N){
                    qt.remove();
                }
                if(qt.size()==N){
                    createAnnotation(qt, tmc);
                }
            }
        }
    }
    
    public void createAnnotation( LinkedList <TokenAnnotation> qt ,TextMinerContent tmc){
                    List<TokenAnnotation>l = new ArrayList<TokenAnnotation>();
                    l.add(qt.get(0));
                    for(int i=1;i<qt.size();i++){
                        l.add(qt.get(i));
                        NGramAnnotation ng = new NGramAnnotation(tmc, l);
                        tmc.addAnnotation(ng);
                    }
    }
    
    
    
}
