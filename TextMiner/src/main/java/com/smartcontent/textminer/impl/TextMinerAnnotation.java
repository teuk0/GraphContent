/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.textminer.impl;

/**
 *
 * @author Teuk
 */
public abstract class TextMinerAnnotation implements Comparable<TextMinerAnnotation> {
    private int start;
    private int end;
    private TextMinerContent tmc;
    
    
    public TextMinerAnnotation(TextMinerContent tmc,int start,int end){
        this.tmc = tmc;
        this.start = start;
        this.end= end;
    } 
    
    public String getView(){
        return new String(tmc.getContent(), start, end-start);
    }
    
    @Override
    public int compareTo(TextMinerAnnotation t){
        return start - t.start;
    }
    
    public int getStart(){
        return start;
    }
    public int getEnd(){
        return end;
    }
    
}
