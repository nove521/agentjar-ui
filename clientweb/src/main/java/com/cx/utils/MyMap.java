package com.cx.utils;

import java.util.Collections;
import java.util.HashMap;

public class MyMap<K,V> extends HashMap<K,V>{

    public MyMap() {
        super();
    }

    public MyMap<K,V> puti(K key, V val){
        super.put(key,val);
        return this;
    }

}
