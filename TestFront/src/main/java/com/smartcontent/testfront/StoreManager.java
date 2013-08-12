/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcontent.testfront;

import com.smartcontent.graphstore.api.MineStorage;
import com.smartcontent.graphstore.impl.MineStorageImpl;

/**
 *
 * @author Teuk
 */
public class StoreManager {
    
    private static MineStorage ms = null;
    public static String typeName = "conte2";
    
    public static MineStorage getStore(){
        if(ms == null){
            ms = new MineStorageImpl("store8");
        }
        return ms;
    }
    
}
