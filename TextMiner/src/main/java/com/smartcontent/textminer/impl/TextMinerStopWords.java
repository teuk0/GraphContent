/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.textminer.impl;

import com.smartcontent.textminer.Stemmers.EnglishStemmer;
import com.smartcontent.textminer.Stemmers.FrenchStemmer;
import com.smartcontent.textminer.Stemmers.SnowballStemmer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author Teuk
 */
public class TextMinerStopWords {

    
    
        private static Map<String, HashSet<String>> langdico;
    
   
        
        public static void init(){
            langdico = new HashMap<String, HashSet<String>>();
            loaddico("fr", "C:/Users/Teuk/Documents/textMiner/textMiner/src/stopwords/stopwords_fr");
            loaddico("en", "C:/Users/Teuk/Documents/textMiner/textMiner/src/stopwords/stopwords_en");
        }
    
         public static void loaddico(String lang, String path)
        {
            try
            {
            String dico = readFile(path);
            HashSet<String> s = new HashSet<String>();

            String[] sentences = dico.split("\n");
            for (String sen : sentences)
            {
                String entry = sen.toLowerCase().trim();
                SnowballStemmer stemmer;
                if(lang=="fr"){
                    stemmer = new FrenchStemmer();
                }else{
                   stemmer = new EnglishStemmer(); 
                }
                stemmer.setCurrent(entry);
                stemmer.stem();
                
                String stemmedentry = stemmer.getCurrent();
    
                //load stemmed and non-stemmed version of the word.
                if (entry.length() > 0)
                {
                    s.add(entry);
                    s.add(stemmedentry);
                }
        
            }
            langdico.put(lang, s);
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException(e);
        }

    }
         
         public static boolean contains(String lang,String word){
             if(langdico.containsKey(lang)){
               
                return langdico.get(lang).contains(word);
             }else{
                 return  false;
             }
         }
    
    
        private static String readFile(String path) throws IOException
        {
        FileInputStream stream = new FileInputStream(new File(path));
        try
        {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            String s = Charset.forName("UTF-8").decode(bb).toString();
            stream.close();
            return s;
        }
        finally
        {
            stream.close();
        }
        }   
    
}
