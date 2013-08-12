/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.textminer.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Teuk
 */
public class TextMinerKeyWord implements Comparable<TextMinerKeyWord> {
    
    private int occ;
    private String stem;
    private Map<String,Integer>forms;
    private boolean isContained;

    public TextMinerKeyWord(String stem){
        forms = new HashMap<String,Integer>();
        this.stem = stem;
        isContained = false;
    }
    
    public void isContained(boolean b){
        isContained = b;
        if(b){
            occ = 0;
        }
    }
    
    public boolean isContained(){
        return isContained;
    }
    
    public void addForm(String form){
        occ++;
        if(forms.containsKey(form)){
            forms.put(form, forms.get(form)+1);
        }else{
            forms.put(form, 1);
        }
    }
    
    public String getStem(){
        return stem;
    }

    public int getOcc(){
        return occ;
    }
    
    @Override
    public int hashCode() {
        int hash = this.stem.hashCode();
        return hash;
    }
    
    @Override
    public boolean equals(Object k){
        if(k instanceof TextMinerKeyWord){
            return ((TextMinerKeyWord)k).stem.equals(stem);
        }else{
            return false;
        }
    }
    
    public String getBestForm(){
        Entry<String,Integer>best=null;
        for(Entry<String,Integer>e:forms.entrySet()){
            if(best==null){
                best=e;
            }
            if(e.getValue()>best.getValue()){
                best=e;
            }
        }
        return best.getKey();
    }
    
    public int compareTo(TextMinerKeyWord t) {
        return t.occ - occ;
    }
    
    public String toString(){
        return getBestForm()+"="+occ;
    }
    
    
}
