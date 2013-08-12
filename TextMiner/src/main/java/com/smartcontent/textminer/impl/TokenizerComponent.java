/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.textminer.impl;

/**
 *
 * @author Teuk
 */
public class TokenizerComponent implements TextMinerComponent {

    @Override
    public void execute(TextMinerContent tmc) {
        char[]c = tmc.getContent();
        int currentstart = 0;
        int sentencestart =0;
    
        for(int i = 0 ; i<c.length ; i++){
            
            if(c[i] == ' ' || c[i] == '.' || c[i] == '!' || c[i] == ',' || c[i] == ';'|| c[i] == '-'|| c[i] == '\'' || c[i]==':'||c[i]=='_'||
                    c[i] == '"' || c[i] == '\n' || c[i] == '(' || c[i] == ')' || c[i] == '?' || c[i] == '*' || c[i]=='='|| c[i]=='–'|| c[i]=='`'|| c[i]=='’'||
                    c[i]=='»' || c[i] == '«'){
                if(!new String(tmc.getContent(),currentstart, i-currentstart).trim().equals("")){
           
                    TextMinerAnnotation t = new TokenAnnotation(tmc, currentstart, i);
                    tmc.addAnnotation(t);
               }
                currentstart = i+1;
            }
            if(c[i] == '.' || c[i] == '!' ||  c[i] == ';' || c[i] == '?'){
                TextMinerAnnotation t = new SentenceAnnotation(tmc, sentencestart, i);
                tmc.addAnnotation(t);
                sentencestart = i+1;
            }
            
            
        }
        
        if(currentstart<c.length-1){
                TextMinerAnnotation t = new TokenAnnotation(tmc, currentstart, c.length-1);
                tmc.addAnnotation(t);
        }
        
        if(sentencestart<c.length-1){
            TextMinerAnnotation t = new SentenceAnnotation(tmc, sentencestart, c.length-1);
            tmc.addAnnotation(t);
        }
    }
    
    
    
}
