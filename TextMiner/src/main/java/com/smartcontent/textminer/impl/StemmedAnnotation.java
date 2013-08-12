/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.textminer.impl;

/**
 *
 * @author Teuk
 */
public abstract class StemmedAnnotation extends TextMinerAnnotation {
    
    public StemmedAnnotation(TextMinerContent tmc,int start,int end){
        super(tmc, start, end);
    }
    
    public abstract String getStem();
    
    public abstract boolean isStopWord();
    
}
