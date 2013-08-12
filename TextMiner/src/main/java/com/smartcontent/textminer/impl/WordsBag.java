/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.textminer.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Teuk
 */
public class WordsBag {
    

    private static double containedFactor=2;
    private static int bagSize=200;
    
    private List<TextMinerKeyWord> finalList;
    
    public WordsBag(TextMinerContent tmc){
       
       Map<String, TextMinerKeyWord> bag;
       bag = new HashMap<String,TextMinerKeyWord>();
        
       for(TokenAnnotation t : tmc.getAnnotations(TokenAnnotation.class)){
            if(!t.isStopWord()){
                
                if(!bag.containsKey(t.getStem())){
                    TextMinerKeyWord k = new TextMinerKeyWord(t.getStem());
                    k.addForm(t.getView());
                    bag.put(t.getStem(),k);
                }else{
                    bag.get(t.getStem()).addForm(t.getView());
                }
            }
        }
        
        for(NGramAnnotation t : tmc.getAnnotations(NGramAnnotation.class)){
            if(!t.isStopWord()){
                if(!bag.containsKey(t.getStem())){
                    TextMinerKeyWord k = new TextMinerKeyWord(t.getStem());
                    k.addForm(t.getView());
                    bag.put(t.getStem(),k);
                }else{
                    bag.get(t.getStem()).addForm(t.getView());
                }
            }
        }
        
        List<TextMinerKeyWord>l=new ArrayList<TextMinerKeyWord>(bag.values());
        Collections.sort(l);
   
        for(int i=0;i<bagSize;i++){
            int j =i+1;
            
            while( j<l.size()&&l.get(i).getOcc()<l.get(j).getOcc()*containedFactor){
                if(l.get(j).getStem().contains(l.get(i).getStem())){
                    l.get(i).isContained(true);
                    //System.out.println(l.get(i).getBestForm());
                }
                j++;
            }
        }
        
        Collections.sort(l);
        finalList = l.subList(0, Math.min(bagSize,l.size()));
        
        
      
        
    }
    
    
    
    public List<TextMinerKeyWord> getKeyWordList(){
        return finalList;
    }
    
    public String toString(){
        String s ="";
        for(TextMinerKeyWord k : getKeyWordList()){
    
            s+=k+"\n";
        }
        return s;
    }
    
}
