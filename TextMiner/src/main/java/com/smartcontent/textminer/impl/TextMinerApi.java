/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.textminer.impl;

/**
 *
 * @author Teuk
 */

public interface TextMinerApi {
    public WordsBag compute(String txt,String lang);
}
