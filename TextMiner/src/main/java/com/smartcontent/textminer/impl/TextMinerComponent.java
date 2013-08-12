/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.textminer.impl;

/**
 *
 * @author Teuk
 */
public interface TextMinerComponent {
    
    /**
     * Execute this component on this content.
     * @param tmc 
     */
    public void execute(TextMinerContent tmc);
    
}
