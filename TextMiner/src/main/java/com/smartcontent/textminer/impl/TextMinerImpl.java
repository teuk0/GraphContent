/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.textminer.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import org.springframework.stereotype.Service;


/**
 *
 * @author Teuk
 */
@Service
public class TextMinerImpl /*implements TextMinerApi*/{

    private static boolean isInit=false;
    private static TokenizerComponent tokenizer;
    private static NgramComponent ng;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //String s = readFile("C:/Users/Teuk/Documents/textMiner/textMiner/src/testFolder/txt2.txt");
        //String s ="Raymond Couderc, premier magistrat de la ville et cible plus ou moins cachée de ce billet, se défend énergiquement : « C’est un constat réel. Connu. L’hyper centre-ville fait face à des populations aux faibles revenus. Mais je voudrais bien savoir quelles actions – que nous n’aurions pas encore engagées – devraient être menées ? La collectivité a mis autant d’argent pour le centre-ville qu’à la Devèze. Le résultat est moins visible car la zone est plus vaste. Autre difficulté pour la municipalité : nous faisons face à des propriétaires privés et certains, préfèrent laisser leurs logements vides. Nous menons alors des actions en justice qui prennent du temps. »";
        TextMinerImpl.init();
        //WordsBag w = TextMiner.compute("des usagers du RER parisien. Les journalistes socialistes Arno Bertina et Oliver Rohe se sont donc rendus le 2 septembre 2012 dans cette même station à 18 heures et, calculette en main, ont procédé à un petit fichage en règle de toutes les personnes présentes sur les quais à cette heure-là. Les chiffres constatés par eux-mêmes sont les suivants: ", "fr");
        //System.out.println(w.getKeyWordList());
        
        //ms.addContent(s, "txt1", "fr");
        /*long t = System.currentTimeMillis();
        ms.queryTest();
        System.out.println(System.currentTimeMillis()-t);
        t = System.currentTimeMillis();
        ms.queryTest();
        System.out.println(System.currentTimeMillis()-t);*/
        
    }
    
    public static WordsBag compute(String txt,String lang){
        init();
        TextMinerContent tmc = new TextMinerContent(txt,lang);
        tokenizer.execute(tmc);
        ng.execute(tmc);
        WordsBag w = new WordsBag(tmc);
        return w;
    }
    
    public static void init(){
        if(!isInit){
            TextMinerStopWords.init();
            tokenizer = new TokenizerComponent();
            ng = new NgramComponent();  
            isInit = true;
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
